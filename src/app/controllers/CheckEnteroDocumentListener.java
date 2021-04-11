package app.controllers;

import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

public class CheckEnteroDocumentListener implements DocumentListener {
    public CheckEnteroDocumentListener() {
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        checkNumero(e);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        checkNumero(e);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        checkNumero(e);
    }

    public void checkNumero(DocumentEvent e) {
        try {
            // Obtener el input del "documento" (el textfield)
            String textoActual = e.getDocument().getText(0, e.getDocument().getLength());

            // Validar número
            int valor = validarNumero(textoActual);
            // Si el valor no es válido
            if (valor < 0) {

                // Runnable para borrar textfield
                Runnable eraseTextField = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            e.getDocument().remove(0, e.getDocument().getLength());
                        } catch (Exception exception) {
                        }
                    }
                }; 


                // Ejecutar runnable
                SwingUtilities.invokeLater(eraseTextField);

                return;
            }
        } catch (BadLocationException exception) {
        }
    }

    // Validar número valida que un número sea mayor o igual a 0
    public int validarNumero(String numeroString) {
        int valor = 0;
        try {
            valor = Integer.parseInt(numeroString);
            if (valor <= 0) {
                throw new Exception("Número menor o igual a cero");
            }
        } catch (Exception e) {
            return -1;
        }
        return valor;
    }

}
