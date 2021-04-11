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
    private JMenuItem exportarXMLMenuItem = new JMenuItem("XML");
    private JFileChooser jFileChooser;

    public MenuBar(XMLController xmlController, JFileChooser jFileChooser) {
        exportarMenu.add(exportarXMLMenuItem);

        this.jFileChooser = jFileChooser;

        MenuBar menuBar = this;

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
    }
}
