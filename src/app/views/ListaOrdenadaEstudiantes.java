package app.views;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

import app.controllers.PromediosController;
import app.models.Estudiante;
import app.models.ListaEstudiantesListModel;
import app.models.NotasTableModel;

public class ListaOrdenadaEstudiantes extends JDialog {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    // Componentes de swing
    private JList<Estudiante> listaEstudiantes;
    private JScrollPane scrollPane;

    // Propiedades
    private ListaEstudiantesListModel listaEstudiantesListModel;
    private NotasTableModel notasTableModel;

    public ListaOrdenadaEstudiantes(JFrame parent, NotasTableModel notasTableModel) {
        super(parent, "Lista de estudiantes por promedio");

        // Modelo de tabla principal
        this.notasTableModel = notasTableModel;

        // Modelo de lista
        this.listaEstudiantesListModel = new ListaEstudiantesListModel();

        // Componente de lista
        listaEstudiantes = new JList<>();

        // Panel de scroll para el componente de lista
        scrollPane = new JScrollPane(listaEstudiantes);

        // Agregar panel
        add(scrollPane);

        // Establecer tamaño de la ventana como el de sus elementos
        pack();

        // Deshabilitar y habilitar la ventana principal de la aplicación mientras esta ventana esté abierta
        setModalityType(DEFAULT_MODALITY_TYPE);
    }

    public void actualizarDatos() {
        // Calcular promedios
        double[] promedios = PromediosController.getPromediosPorEstudiante(notasTableModel.getCalificaciones());

        // Obtener nombres y apellidos
        String[] nombres = notasTableModel.getNombresEstudiante();
        String[] apellidos = notasTableModel.getApellidosEstudiante();

        // Crear lista de estudiantes
        List<Estudiante> listaDeEstudiantes = new ArrayList<Estudiante>();

        // Agregar estudiantes
        for (int i = 0; i < promedios.length; i++) {
            Estudiante estudiante = new Estudiante(nombres[i], apellidos[i], promedios[i]);

            listaDeEstudiantes.add(estudiante);
        }

        // Ordenar lista de estudiantes y revertir el orden de mayor a menor
        Collections.sort(listaDeEstudiantes);
        Collections.reverse(listaDeEstudiantes);

        // Establecer lista en modelo
        listaEstudiantesListModel.setEstudiantes(listaDeEstudiantes);

        // Establecer modelo en JList
        listaEstudiantes.setModel(listaEstudiantesListModel);
    }
}
