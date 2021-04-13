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
import app.models.NotasTableModel;

public class MenuBar extends JMenuBar {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    // Elementos de swing
    private JMenu exportarMenu = new JMenu("Exportar datos");
    private JMenu accionesMenu = new JMenu("Acciones");

    private JMenuItem exportarXMLMenuItem = new JMenuItem("XML");
    private JMenuItem promediosEstudiantesMenuItem = new JMenuItem("Promedios por Estudiante");
    private JMenuItem listaEstudiantesMenuItem = new JMenuItem("Lista de estudiantes por promedio");

    private JFileChooser jFileChooser;
    private TablaPromedioEstudiantes tablaEstudiantes;
    private ListaOrdenadaEstudiantes listaOrdenadaEstudiantes;
    private NotasTableModel notasTableModel;

    public MenuBar(XMLController xmlController, JFileChooser jFileChooser, 
        NotasTableModel notasTableModel, TablaPromedioEstudiantes tablaEstudiantes,
        ListaOrdenadaEstudiantes listaOrdenadaEstudiantes) {
            
        this.jFileChooser = jFileChooser;
        this.tablaEstudiantes = tablaEstudiantes;
        this.listaOrdenadaEstudiantes = listaOrdenadaEstudiantes;
        this.notasTableModel = notasTableModel;

         // Agregar MenuItems al menú
         exportarMenu.add(exportarXMLMenuItem);
         accionesMenu.add(promediosEstudiantesMenuItem);
         accionesMenu.add(listaEstudiantesMenuItem);

        MenuBar menuBar = this;

        // Lista ordenada de estudiantes
        listaEstudiantesMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // No permitir si no sea han rellenado todos los datos
                if (notasTableModel.hasEmptyNombreApellido()) {
                    JOptionPane.showMessageDialog(menuBar, "No se puede realizar esta acción con datos incompletos", 
                        "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                listaOrdenadaEstudiantes.actualizarDatos();
                listaOrdenadaEstudiantes.setVisible(true);
            }
        });

        // Tabla de promedio por estudiante
        promediosEstudiantesMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // No permitir si no sea han rellenado todos los datos
                if (notasTableModel.hasEmptyNombreApellido()) {
                    JOptionPane.showMessageDialog(menuBar, "No se puede realizar esta acción con datos incompletos", 
                        "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                tablaEstudiantes.actualizarDatos();
                tablaEstudiantes.setVisible(true);
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
