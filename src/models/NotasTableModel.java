package models;

import javax.swing.table.AbstractTableModel;

import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class NotasTableModel extends AbstractTableModel {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String[] nombreEstudiante;
    private String[] nombreMateria;
    private double[][] calificaciones;


    // Revisar si la ubicación en la tabla es válida
    private boolean esLocacionValida(int rowIndex, int columnIndex) {
        return rowIndex < nombreEstudiante.length && columnIndex < nombreMateria.length + 1;
    }

    public NotasTableModel(int numEstudiantes, int numCalificaciones) {
        nombreEstudiante = new String[numEstudiantes];
        nombreMateria = new String[numCalificaciones];

        calificaciones = new double[numEstudiantes][numCalificaciones];

        for (int i = 0; i < numCalificaciones; i++) {
            nombreMateria[i] = super.getColumnName(i);
        }
    }

    @Override
    public int getRowCount() {
        return nombreEstudiante.length;
    }

    @Override
    public int getColumnCount() {
        return calificaciones[0].length + 1;
    }

    @Override
    public String getColumnName(int columnIndex) {
        if (columnIndex == 0) {
            return "Alumno";
        }

        return nombreMateria[columnIndex - 1];
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex) {
            case 0:
                return String.class;
        }

        return Double.class;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (esLocacionValida(rowIndex, columnIndex)) {
            if (columnIndex == 0) {
                return nombreEstudiante[rowIndex];
            } else {
                return calificaciones[rowIndex][columnIndex - 1];
            }
        }

        return null;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        try {
            switch(columnIndex) {
                case 0:
                    nombreEstudiante[rowIndex] = (String)value;
                    break;
                default:
                    if (esCalificacionValida((Double)value)) {
                        calificaciones[rowIndex][columnIndex - 1] = (Double)value;
                    }
            }
        } catch(Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    public static boolean esCalificacionValida(double calificacion) {
        return calificacion >= 0 && calificacion <= 20;
    }

    // Serializar NotasTableModel en forma de un fragmento de documento XML
    public DocumentFragment getXMLFragment(Document parentDocument) {
        // Fragmento principal
        DocumentFragment mainFragment = parentDocument.createDocumentFragment();

        // Dimensiones
        Element dimensionesElement = parentDocument.createElement("dimensiones");
        dimensionesElement.setAttribute("materias", String.valueOf(nombreMateria.length));
        dimensionesElement.setAttribute("estudiantes", String.valueOf(nombreEstudiante.length));

        mainFragment.appendChild(dimensionesElement);

        for (int i = 0; i < nombreEstudiante.length; i++) {
            // Estudiante
            Element estudianteElement = parentDocument.createElement("estudiante");

            // Nombre de estudiante
            Element nombrElement = parentDocument.createElement("nombre");
            nombrElement.setTextContent(nombreEstudiante[i]);


            // Calificaciones de estudiante
            DocumentFragment fragment = parentDocument.createDocumentFragment();

            for (int j = 0; j < nombreMateria.length; j++) {
                Element calificacionElement = parentDocument.createElement("calificacion");

                calificacionElement.setTextContent(String.valueOf(calificaciones[i][j]));

                fragment.appendChild(calificacionElement);
            }

            // Agregar nombre y calificaciones
            estudianteElement.appendChild(nombrElement);
            estudianteElement.appendChild(fragment);

            // Agregar estudiante al fragmento princiapl
            mainFragment.appendChild(estudianteElement);          
        }

        return mainFragment;
    }

    // Deserializar un documento XML
    public static NotasTableModel getFromXML(Document parentDocument) throws Exception {
        NotasTableModel notasTableModel = null;

        // Chequear la existencia de un solo <estudiantes></estudiantes>
        NodeList estudiantes = parentDocument.getElementsByTagName("estudiantes");

        if (estudiantes.getLength() != 1) {
            throw new Exception("Esquema inválido (solo un elemento de estudiantes permitido)");
        }

        // Chequear la existencia de un solo <dimensiones .../>
        NodeList dimensiones = parentDocument.getElementsByTagName("dimensiones");

        if (dimensiones.getLength() != 1) {
            throw new Exception("Esquema inválido (solo un elemento de dimensiones permitido)");
        }
        else if (dimensiones.item(0).getParentNode() != estudiantes.item(0)) {
            throw new Exception("Esquema inválido (dimensiones no es padre del nodo estudiantes)");
        }

        // Obtener dimensiones de la tabla
        Node dimensionesElement = dimensiones.item(0);
        NamedNodeMap attributes = dimensionesElement.getAttributes();

        try {
            int numEstudiantes = Integer.parseInt(attributes.getNamedItem("estudiantes").getTextContent());
            int numMaterias = Integer.parseInt(attributes.getNamedItem("materias").getTextContent());

            // Crear notasTableModel a partir de dichas dimensiones
            notasTableModel = new NotasTableModel(numEstudiantes, numMaterias);
        } catch (Exception e) {
            throw new Exception("Esquema inválido (Las dimensiones no son enteros positivos)");
        }

        // Verificar la validez del número de estudiantes en dimensiones
        NodeList estudiantesNodes = parentDocument.getElementsByTagName("estudiante");

        if (estudiantesNodes.getLength() != notasTableModel.getRowCount()) {
            throw new Exception("Esquema inválido (El número de estudiantes en dimensiones no concuerdan con el número de estudiantes en el archivo)");
        }

        // Verificar la validez del número de calificaciones por estudiante en dimensiones (n*m)
        NodeList calificacionesNodes = parentDocument.getElementsByTagName("calificacion");

        if (calificacionesNodes.getLength() != notasTableModel.getRowCount() *notasTableModel.nombreMateria.length) {
                throw new Exception("Esquema inválido (El número de materias en dimensiones no concuerdan con el número de calificaciones por estudiante)");
        }

        int col = 0;

        // Rellenar valores de la tabla
        for (int i = 0; i < estudiantesNodes.getLength(); i++) {
            if (estudiantesNodes.item(i).getParentNode() != estudiantes.item(0)) {
                throw new Exception("Esquema inválido (estudiante no es padre del nodo estudiantes)");
            }

            // Obtener nodo de estudiante
            Node estudiante = estudiantesNodes.item(i);

            // Obtener nodos hijo del estudiante
            NodeList children = estudiante.getChildNodes();

            col = 0;

            for (int j = 0; j < children.getLength(); j++) {
                if (children.item(j).getNodeType() != Node.ELEMENT_NODE) {
                    continue;
                }

                switch (col) {
                    // Nombre de estudiante
                    case 0:
                        notasTableModel.setValueAt(children.item(j).getTextContent(), i, col);
                        break;
                
                    // Calificación de estudiante
                    default:
                        try {
                            double calificacion = Double.parseDouble(children.item(j).getTextContent());

                            // Chequear validez de calificación antes de agregar
                            if (!esCalificacionValida(calificacion)) {
                                throw new Exception("");
                            }

                            notasTableModel.setValueAt(calificacion, i, col);
                        } catch (Exception e) {
                            throw new Exception(
                                String.format("Esquema inválido (Error en calificacion %d del estudiante %d)", col, i + 1));
                        }
                        break;
                }

                col++;
            }

        }

        return notasTableModel;   
    }
}
