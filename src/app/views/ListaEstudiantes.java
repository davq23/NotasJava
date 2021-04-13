package app.views;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

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

 

    public ListaEstudiantes(NotasTableModel notasTableModel) {
        this.notasTableModel = notasTableModel;

        listaEstudiantesTableModel = new ListaEstudiantesTableModel();
        
        estudiantesLisTable = new JTable();
        
        scrollPane = new JScrollPane(estudiantesLisTable);
        
        add(scrollPane);
    }


    public void actualizarPromedios() {
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
