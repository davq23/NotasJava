package app.views;


import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import app.controllers.PromediosController;
import app.models.ListaEstudiantesTableModel;
import app.models.NotasTableModel;

public class TablaPromedioEstudiantes extends JDialog {

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


    public TablaPromedioEstudiantes(JFrame parent, NotasTableModel notasTableModel) {
        super(parent, "Promedios por estudiante");
        
        this.notasTableModel = notasTableModel;

        // Crear modelo de tabla
        listaEstudiantesTableModel = new ListaEstudiantesTableModel();

        // Crear tabla y hacerla no autoajustable
        estudiantesLisTable = new JTable();
        estudiantesLisTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // Colocar la tabla en un scroll panel
        scrollPane = new JScrollPane(estudiantesLisTable);

        // Agregar el scrollpanel
        add(scrollPane);

        // Establecer tamaño de la ventana como el de sus elementos
        pack();

        // Deshabilitar y habilitar la ventana principal de la aplicación mientras esta ventana esté abierta
        setModalityType(DEFAULT_MODALITY_TYPE);

    }


    public void actualizarDatos() {
        // Calcular promedios
        double[] promedios = PromediosController.getPromediosPorEstudiante(notasTableModel.getCalificaciones());

        // Establecer valores en el modelo de tabla
        listaEstudiantesTableModel.setPromedios(promedios);
        listaEstudiantesTableModel.setNombres(notasTableModel.getNombresEstudiante());
        listaEstudiantesTableModel.setApellidos(notasTableModel.getApellidosEstudiante());
        
        // Reestablecer modelo de JTable
        estudiantesLisTable.setModel(listaEstudiantesTableModel);
    }
    
}
