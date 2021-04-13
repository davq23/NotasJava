package app.controllers;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class DisableParentWindowAdapter extends WindowAdapter {
    // JFrame principal de la aplicación
    private JFrame appJFrame;

    public DisableParentWindowAdapter(JFrame appJFrame) {
        this.appJFrame = appJFrame;
    }

    // Deshabilitar la ventana principal mientras la ventana secundaria esté abierta
    @Override
    public void windowOpened(WindowEvent e) {
        appJFrame.setEnabled(false);
        super.windowOpened(e);
    }

    // Habilitar la ventana principal apenas la ventan secundaria se cierre
    @Override
    public void windowClosed(WindowEvent e) {
        System.out.println("aaaaaaa");
        appJFrame.setEnabled(true);
        super.windowOpened(e);
    }
}
