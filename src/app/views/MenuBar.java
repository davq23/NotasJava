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

    private JMenuItem exportarXMLMenuItem = new JMenuItem("Exportar a XML");
    private JMenuItem promediosEstudiantesMenuItem = new JMenuItem("Promedios por estudiante");
    private JMenuItem promediosMateriasMenuItem = new JMenuItem("Promedios por materia");
    private JMenuItem promedioSeccionMenuItem = new JMenuItem("Promedio de secci\u00f3n");
    private JMenuItem listaEstudiantesMenuItem = new JMenuItem("Lista de estudiantes por promedio");

    private JFileChooser jFileChooser;
    private ListaOrdenadaEstudiantes listaOrdenadaEstudiantes;
    private NotasTableModel notasTableModel;
    private PromedioSeccion promedioSeccion;
    private TablaPromedioEstudiantes tablaPromedioEstudiantes;
    private TablaPromedioMaterias tablaPromedioMaterias;

    public MenuBar(XMLController xmlController, JFileChooser jFileChooser, 
        NotasTableModel notasTableModel, TablaPromedioEstudiantes tablaPromedioEstudiantes, 
        TablaPromedioMaterias tablaPromedioMaterias,
        PromedioSeccion promedioSeccion,
        ListaOrdenadaEstudiantes listaOrdenadaEstudiantes) {
            
        this.jFileChooser = jFileChooser;
        this.tablaPromedioEstudiantes = tablaPromedioEstudiantes;
        this.listaOrdenadaEstudiantes = listaOrdenadaEstudiantes;
        this.notasTableModel = notasTableModel;
        this.tablaPromedioMaterias = tablaPromedioMaterias;
        this.promedioSeccion = promedioSeccion;

         // Agregar MenuItems al menú
         exportarMenu.add(exportarXMLMenuItem);
         accionesMenu.add(promediosEstudiantesMenuItem);
         accionesMenu.add(promediosMateriasMenuItem);
         accionesMenu.add(promedioSeccionMenuItem);
         accionesMenu.add(listaEstudiantesMenuItem);

        MenuBar menuBar = this;

         // Tabla de promedio por estudiante
         promediosEstudiantesMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // No permitir si no sea han rellenado todos los datos
                if (menuBar.notasTableModel.hasEmptyNombreApellido()) {
                    JOptionPane.showMessageDialog(menuBar, "No se puede realizar esta acci\u00f3n con datos incompletos", 
                        "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                menuBar.tablaPromedioEstudiantes.actualizarDatos();
                menuBar.tablaPromedioEstudiantes.setVisible(true);
            }
        });

         // Tabla de promedio por materia
         promediosMateriasMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // No permitir si no sea han rellenado todos los datos
                if (menuBar.notasTableModel.hasEmptyNombreApellido()) {
                    JOptionPane.showMessageDialog(menuBar, "No se puede realizar esta acci\u00f3n con datos incompletos", 
                        "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                menuBar.tablaPromedioMaterias.actualizarDatos();
                menuBar.tablaPromedioMaterias.setVisible(true);
            }
        });

        // Promedio de sección
        promedioSeccionMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // No permitir si no sea han rellenado todos los datos
                if (menuBar.notasTableModel.hasEmptyNombreApellido()) {
                    JOptionPane.showMessageDialog(menuBar, "No se puede realizar esta acci\u00f3n con datos incompletos", 
                        "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                menuBar.promedioSeccion.actualizarDatos();
                menuBar.promedioSeccion.setVisible(true);
            }
        });


        // Lista ordenada de estudiantes
        listaEstudiantesMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // No permitir si no sea han rellenado todos los datos
                if (menuBar.notasTableModel.hasEmptyNombreApellido()) {
                    JOptionPane.showMessageDialog(menuBar, "No se puede realizar esta acci\u00f3n con datos incompletos", 
                        "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                menuBar.listaOrdenadaEstudiantes.actualizarDatos();
                menuBar.listaOrdenadaEstudiantes.setVisible(true);
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
