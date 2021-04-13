package app.views;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import app.controllers.PromediosController;
import app.models.PromediosMateriaTableModel;
import app.models.NotasTableModel;

public class TablaPromedioMaterias extends JDialog {
    
    private static final long serialVersionUID = 1L;

    // Componentes de swing
    private JTable promediosMatListTable;
    private JScrollPane scrollPane;
    private PromediosMateriaTableModel PromediosMateriaTableModel;

    // Propiedades
    private NotasTableModel notasTableModel;
    
    public TablaPromedioMaterias(JFrame parent, NotasTableModel notasTableModel) {
        super(parent, "Promedio por materia");
        
        this.notasTableModel = notasTableModel;

        // Crear modelo de tabla
        PromediosMateriaTableModel = new PromediosMateriaTableModel();

        // Crear tabla y hacerla no autoajustable
        promediosMatListTable = new JTable();
        promediosMatListTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // Colocar la tabla en un scroll panel
        scrollPane = new JScrollPane(promediosMatListTable);

        // Agregar el scrollpanel
        add(scrollPane);

        // Establecer tamaño de la ventana
        setSize(400, 600);

        // Deshabilitar y habilitar la ventana principal de la aplicación mientras esta ventana esté abierta
        this.setModalityType(DEFAULT_MODALITY_TYPE);

    }


    public void actualizarDatos() {
        // Calcular promedios
        double[] promedios = PromediosController.getPromediosPorMateria(notasTableModel.getCalificaciones());

        // Establecer valores en el modelo de tabla
        PromediosMateriaTableModel.setMaterias(notasTableModel.getNombresMateria());
        PromediosMateriaTableModel.setPromedios(promedios);
        
        // Reestablecer modelo de JTable
        promediosMatListTable.setModel(PromediosMateriaTableModel);
    }
}
