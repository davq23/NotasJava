package views;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import controllers.XMLController;

import javax.swing.JMenu;

public class MenuBar extends JMenuBar {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    // Elementos de swing
    private JMenu exportarMenu = new JMenu("Exportar datos");
    private JMenuItem exportarXMLMenuItem = new JMenuItem("XML");

    public MenuBar(XMLController xmlController) {
        exportarMenu.add(exportarXMLMenuItem);

        MenuBar menuBar = this;

        // Acción de exportar a XML
        exportarXMLMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e)
            {
                try {
                    // Diálogo de nombre de archivo
                    String nombreArchivo = JOptionPane.showInputDialog("Nombre de Archivo XML:");

                    // Si no se ingresa un nombre, se elige "output.xml" por defecto 
                    if (nombreArchivo.equals("")) {
                        xmlController.setNombreArchivo("output.xml");

                    } else {
                        xmlController.setNombreArchivo(nombreArchivo);
                    }

                    // Especificar operación del controlador de XML
                    xmlController.setOperacion(XMLController.EXPORTAR_XML);

                    // Iniciar controlador ede XML de forma asincrónica
                    SwingUtilities.invokeLater(xmlController);
                    
                } catch(Exception ex) {
                    JOptionPane.showMessageDialog(menuBar.getParent(), 
                        "Error al exportar XML: " + ex.getLocalizedMessage(), 
                        "Error de XML", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(exportarMenu);
    }
}
