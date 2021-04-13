package app.views;

import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import javax.swing.JFileChooser;
import javax.swing.JMenu;

import app.controllers.XMLController;

public class MenuBar extends JMenuBar {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    // Elementos de swing
    private JMenu exportarMenu = new JMenu("Exportar datos");
    private JMenu accionesMenu = new JMenu("Acciones");

    private JMenuItem exportarXMLMenuItem = new JMenuItem("XML");
    private JMenuItem listaEstudiantesMenuItem = new JMenuItem("Lista de Estudiantes");

    private JFileChooser jFileChooser;
    private ListaEstudiantes listaEstudiantes;

    public MenuBar(XMLController xmlController, JFileChooser jFileChooser, ListaEstudiantes listaEstudiantes) {
        // Agregar MenuItems al menú
        exportarMenu.add(exportarXMLMenuItem);
        accionesMenu.add(listaEstudiantesMenuItem);

        this.jFileChooser = jFileChooser;
        this.listaEstudiantes = listaEstudiantes;

        MenuBar menuBar = this;

        // Acción de abrir la ventana de lista de estudiantes
        listaEstudiantesMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listaEstudiantes.actualizarDatos();
                listaEstudiantes.setVisible(true);
            }
        });

        // Acción de exportar a XML
        exportarXMLMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Diálogo de nombre de archivo
                    int option = menuBar.jFileChooser.showSaveDialog(xmlController.getParent());

                    switch (option) {
                    case JFileChooser.APPROVE_OPTION:
                        File file = jFileChooser.getSelectedFile();

                        // Si el archivo no termina en xml, crear uno nuevo con la extensión
                        if (!file.getName().toLowerCase().endsWith(".xml")) {
                            file = new File(file.getParentFile(), file.getName() + ".xml");
                        }

                        xmlController.setFileChosen(file);
                        break;

                    case JFileChooser.CANCEL_OPTION:
                        // Eliminar selección
                        xmlController.setFileChosen(null);
                        return;

                    case JFileChooser.ERROR_OPTION:
                        xmlController.setFileChosen(null);
                        JOptionPane.showMessageDialog(jFileChooser, "Error en selecci\u00f3n de archivo", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Especificar operación del controlador de XML
                    xmlController.setOperacion(XMLController.EXPORTAR_XML);

                    // Iniciar controlador ede XML de forma asincrónica
                    SwingUtilities.invokeLater(xmlController);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(menuBar.getParent(),
                            "Error al exportar XML: " + ex.getLocalizedMessage(), "Error de XML",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(exportarMenu);
        add(accionesMenu);
    }
}
