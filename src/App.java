import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import controllers.CheckEnteroDocumentListener;
import controllers.XMLController;
import models.NotasTableModel;
import views.MenuBar;

public class App extends JFrame implements ActionListener {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    // Propiedades
    private final int textFieldSize = 10;

    // Controladores
    private XMLController xmlController;

    // Archivo XML importado
    private File fileChosen;

    // Filtro de archivos
    FileNameExtensionFilter filter = new FileNameExtensionFilter("Documento XML", "xml");

    // Componentes de swing
    private JButton importXMLBtn = new JButton("Importar XML");

    private JFileChooser jFileChooser = new JFileChooser();

    private JLabel numEstudiantesLabel = new JLabel("Número de estudiantes");
    private JLabel numMateriasLabel = new JLabel("Número de materias");

    private JTextField numEstudiantesTextField = new JTextField(textFieldSize);
    private JTextField numMateriasTextField = new JTextField(textFieldSize);
    private JTextField fileNameTextField = new JTextField();

    private JTable notasTable;
    private JScrollPane scrollPane;

    // Listeners
    private CheckEnteroDocumentListener checkEnteroListener = new CheckEnteroDocumentListener();

    // Modelos
    private NotasTableModel notasTableModel;

    // Vistas
    private MenuBar menuBar;

    // Components del diálogo inicial
    private final JComponent[] componentesDialogo = new JComponent[] { numEstudiantesLabel, numEstudiantesTextField,
            numMateriasLabel, numMateriasTextField, fileNameTextField, importXMLBtn };

    public App(String title) {
        super(title);

        // Solo permitir números enteros positivos
        numEstudiantesTextField.getDocument().addDocumentListener(checkEnteroListener);
        numMateriasTextField.getDocument().addDocumentListener(checkEnteroListener);

        // Hacer el textfield de archivo no editable
        fileNameTextField.setEditable(false);

        // Aplicar filtro a JFileChooser
        jFileChooser.setFileFilter(filter);

        // Inicializar tabla
        notasTable = new JTable();

        // Hacer a la tabla de tamaño fijo
        notasTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // Inicializar scrollpane
        scrollPane = new JScrollPane(notasTable);

        // Inicializar XMLController
        xmlController = new XMLController(this);

        // scrollPane.setVisible(true);

        add(scrollPane);

        // Inicializar ActionEvent
        importXMLBtn.addActionListener(this);
    }

    public void setTabla() {
        // Inicializar data de JTable
        notasTable.setModel(notasTableModel);

        // Inicializar XML Controller
        xmlController.setNotasTableModel(notasTableModel);
    }

    public void setMenu() {
        // Inicializar MenuBar
        menuBar = new MenuBar(xmlController);

        // Agregar MenuBar
        setJMenuBar(menuBar);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Elegir archivo XML para importar
        int option = jFileChooser.showOpenDialog(this);

        switch (option) {
        case JFileChooser.APPROVE_OPTION:
            // Establecer archivo a importar
            fileChosen = jFileChooser.getSelectedFile();

            // Establecer el nombre del archivo como texto en fileNameTextField
            fileNameTextField.setText(fileChosen.getName());

            // Borrar los textfield de dimensiones
            numEstudiantesTextField.setText("");
            numMateriasTextField.setText("");
            break;

        case JFileChooser.CANCEL_OPTION:
            // Eliminar selección
            fileChosen = null;
            fileNameTextField.setText("");
            break;

        case JFileChooser.ERROR_OPTION:
            // Eliminar selección y mostrar mensaje de error
            fileChosen = null;
            fileNameTextField.setText("");

            JOptionPane.showMessageDialog(this, "Error en selección de archivo", "Error", JOptionPane.ERROR_MESSAGE);
            break;
        }

        // Si no hay un archivo seleccionado, estarán disponibles las dimensiones
        numEstudiantesTextField.setEditable(fileChosen == null);
        numMateriasTextField.setEditable(fileChosen == null);
    }

    public static void main(String[] args) throws Exception {
        App app = new App("Calificaciones");

        // Mostrar diálogo inicial
        int result = JOptionPane.showConfirmDialog(app, app.componentesDialogo, "Tamaño de matriz",
                JOptionPane.OK_CANCEL_OPTION);

        // Si la opcion no es ok, cerrar programa
        if (result != JOptionPane.OK_OPTION) {
            app.dispose();
            return;
        }

        // Si no se ha seleccionado ningún archivo, revisar dimensiones
        if (app.fileChosen == null) {

            try {
                int numEstudiantes = Integer.parseInt(app.numEstudiantesTextField.getText());
                int numMaterias = Integer.parseInt(app.numMateriasTextField.getText());

                app.notasTableModel = new NotasTableModel(numEstudiantes, numMaterias);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(app, "Valores de inválidos de filas o columnas", "ERROR",
                        JOptionPane.ERROR_MESSAGE);

                app.dispose();
                return;
            }

        // Si se seleccionó un archivo, importar datos de XML
        } else {
            // Especificar archivo a importar al controlador de XML
            app.xmlController.setFileChosen(app.fileChosen);

            // Establecer la operación como importar XML
            app.xmlController.setOperacion(XMLController.IMPORTAR_XML);

            // Ejecutar el controlador XML de forma sincrónica
            SwingUtilities.invokeAndWait(app.xmlController);

            // Si no se logró serializar el documento XML
            if (app.xmlController.getNotasTableModel() == null) {
                app.dispose();
                return;
            }

            // Establecer modelo de tabla
            app.notasTableModel = app.xmlController.getNotasTableModel();
        }

        // Establecer tabla
        app.setTabla();
        // Establecer menú
        app.setMenu();

        // Definir detalles de JFrame
        app.setBounds(100, 100, 800, 600);
        app.setMinimumSize(new Dimension(400, 200));
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);
    }
}
