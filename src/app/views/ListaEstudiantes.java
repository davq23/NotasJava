package app.views;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import app.controllers.DisableParentWindowAdapter;
import app.controllers.PromediosController;
import app.models.ListaEstudiantesTableModel;
import app.models.NotasTableModel;

public class ListaEstudiantes extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    // Componentes de swing
    private JTable estudiantesLisTable;
    private JScrollPane scrollPane;
    private ListaEstudiantesTableModel listaEstudiantesTableModel;

    // Propiedades
    private NotasTableModel notasTableModel;

    public ListaEstudiantes(NotasTableModel notasTableModel, DisableParentWindowAdapter disableParentWindowAdapter) {
        super("Lista Estudiantes");
        
        this.notasTableModel = notasTableModel;

        // Crear modelo de tabla
        listaEstudiantesTableModel = new ListaEstudiantesTableModel();

        // Crear tabla y hacerla no autoajustable
        estudiantesLisTable = new JTable();
        estudiantesLisTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // Colocar la tabla en un scroll panel
        scrollPane = new JScrollPane();
        scrollPane.add(estudiantesLisTable);

        // Agregar el scrollpanel
        add(scrollPane);

        // Establecer tamaño de la ventana
        setSize(200, 400);

        // Deshabilitar y habilitar la ventana principal de la aplicación mientras esta ventana esté abierta
        addWindowFocusListener(disableParentWindowAdapter);
    }


    public void actualizarDatos() {
        // Calcular promedios
        double[] promedios = PromediosController.getPromediosPorEstudiante(notasTableModel.getCalificaciones());

        // Establecer valores en el modelo de tabla
        listaEstudiantesTableModel.setPromedios(promedios);
        listaEstudiantesTableModel.setNombres(notasTableModel.getNombresEstudiante());
        listaEstudiantesTableModel.setApellidos(notasTableModel.getApellidosEstudiante());

        estudiantesLisTable.setModel(listaEstudiantesTableModel);
        estudiantesLisTable.repaint();
    }
    
}
