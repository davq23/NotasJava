

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
        super(parent, "Promedio de secci\u00f3n");
        
        this.notasTableModel = notasTableModel;

        // Crear promedio JLabel
        promedioLabel = new JLabel();

        add(promedioLabel);

        
        // Establecer tamaño de la ventana
        setSize(400, 200);

        // Deshabilitar y habilitar la ventana principal de la aplicación mientras esta ventana esté abierta
        setModalityType(DEFAULT_MODALITY_TYPE);
    }


    public void actualizarDatos() {
        // Calcular promedio de sección
        double promedio = PromediosController.getPromedioDeSeccion(notasTableModel.getCalificaciones());

        promedioLabel.setText(String.format("El promedio de la secci\u00f3n es %f", promedio));
    }
    
}
