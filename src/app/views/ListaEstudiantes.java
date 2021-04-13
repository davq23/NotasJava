package app.views;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.TableRowSorter;

import app.controllers.PromediosController;
import app.models.ListaEstudiantesTableModel;
import app.models.NotasTableModel;

public class ListaEstudiantes extends JDialog {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    // Componentes de swing
    private JTable estudiantesLisTable;
    private JScrollPane scrollPane;
    private ListaEstudiantesTableModel listaEstudiantesTableModel;

    private TableRowSorter<ListaEstudiantesTableModel> sorter;
    List<RowSorter.SortKey> sortKeys;

    // Propiedades
    private NotasTableModel notasTableModel;


    public ListaEstudiantes(JFrame parent, NotasTableModel notasTableModel) {
        super(parent, "Lista de estudiantes");
        
        this.notasTableModel = notasTableModel;

        // Crear modelo de tabla
        listaEstudiantesTableModel = new ListaEstudiantesTableModel();

        // Crear organizador de tabla
        sorter = new TableRowSorter<>();

        // Especificar bajo qué columna se reorganizarán las filas
        sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(ListaEstudiantesTableModel.PROMEDIO_COL, SortOrder.DESCENDING));
        
        // Crear tabla y hacerla no autoajustable
        estudiantesLisTable = new JTable();
        estudiantesLisTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // Colocar la tabla en un scroll panel
        scrollPane = new JScrollPane(estudiantesLisTable);

        // Agregar el scrollpanel
        add(scrollPane);

        // Establecer tamaño de la ventana
        setSize(400, 600);

        // Deshabilitar y habilitar la ventana principal de la aplicación mientras esta ventana esté abierta
        this.setModalityType(DEFAULT_MODALITY_TYPE);

    }


    public void actualizarDatos() {
        // Calcular promedios
        double[] promedios = PromediosController.getPromediosPorEstudiante(notasTableModel.getCalificaciones());

        // Establecer valores en el modelo de tabla
        listaEstudiantesTableModel.setPromedios(promedios);
        listaEstudiantesTableModel.setNombres(notasTableModel.getNombresEstudiante());
        listaEstudiantesTableModel.setApellidos(notasTableModel.getApellidosEstudiante());
        
        estudiantesLisTable.setModel(listaEstudiantesTableModel);

        sorter.setModel(listaEstudiantesTableModel);
        sorter.setSortKeys(sortKeys);
        estudiantesLisTable.setRowSorter(sorter);
        estudiantesLisTable.repaint();

        sorter.setSortable(ListaEstudiantesTableModel.NOMBRE_COL, false);
        sorter.setSortable(ListaEstudiantesTableModel.APELLIDO_COL, false);
        sorter.setSortable(ListaEstudiantesTableModel.PROMEDIO_COL, false);
    }
    
}
