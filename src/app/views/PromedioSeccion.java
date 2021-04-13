

package app.views;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import app.controllers.PromediosController;
import app.models.NotasTableModel;

public class PromedioSeccion extends JDialog {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    // Componentes de swing
    private JLabel promedioLabel;

    // Propiedades
    private NotasTableModel notasTableModel;


    public PromedioSeccion(JFrame parent, NotasTableModel notasTableModel) {
        super(parent, "Promedio Seccion");
        
        this.notasTableModel = notasTableModel;

        // Crear promedio JLabel
        promedioLabel = new JLabel();

        add(promedioLabel);

        // Establecer tamaño de la ventana
        pack();

        // Deshabilitar y habilitar la ventana principal de la aplicación mientras esta ventana esté abierta
        setModalityType(DEFAULT_MODALITY_TYPE);
    }


    public void mostrarPromedio() {
        // Calcular promedio de sección
        double promedio = PromediosController.getPromedioDeSeccion(notasTableModel.getCalificaciones());

        promedioLabel.setText(String.format("El promedio de la sección es %f", promedio));
    }
    
}
