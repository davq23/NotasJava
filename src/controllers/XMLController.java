package controllers;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;

import models.NotasTableModel;

public class XMLController implements Runnable {
    // Propiedades
    private int operacion;

    // Constantes
    public static final int EXPORTAR_XML = 0;
    public static final int IMPORTAR_XML = 1;

    // Archivo a importar
    private File fileChosen = null;

    // Modelo de tabla
    private NotasTableModel notasTableModel;

    // JFrame padre
    private JFrame parent;

    public XMLController(JFrame parent) {
        this.parent = parent;
    }

    public XMLController(JFrame parent, NotasTableModel notasTableModel) {
        this.notasTableModel = notasTableModel;
        this.parent = parent;
    }

    public void setNotasTableModel(NotasTableModel notasTableModel) {
        this.notasTableModel = notasTableModel;
    }

    public void setFileChosen(File fileChosen) {
        this.fileChosen = fileChosen;
    }

    public File getFileChosen() {
        return fileChosen;
    }

    public JFrame getParent() {
        return parent;
    }

    public void setOperacion(int operacion) {
        this.operacion = operacion;
    }

    public NotasTableModel getNotasTableModel() {
        return notasTableModel;
    }

    @Override
    public void run() {
        try {
            // Chequear que se haya seleccionado un archivo
            if (fileChosen == null) {
                throw new Exception("Ningún archivo seleccionado");
            }

            // Crear un DOMBuilder Factory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            // Crear un DOM builder
            DocumentBuilder dBuilder = factory.newDocumentBuilder();

            Document doc = null;

            switch (operacion) {
            case IMPORTAR_XML:
                // Crear una representación DOM del archivo
                doc = dBuilder.parse(fileChosen);

                // Asignar modelo de tabla resultante
                notasTableModel = NotasTableModel.getFromXML(doc);

                break;

            case EXPORTAR_XML:
                // Crear un DOM nuevo
                doc = dBuilder.newDocument();

                // Crear nodo principal
                Element parentNode = doc.createElement("estudiantes");

                // Obtener tabla XML en un fragmento
                DocumentFragment fragment = notasTableModel.getXMLFragment(doc);

                // Agregar el fragmento al nodo principal
                parentNode.appendChild(fragment);

                // Agregar el nodo principal al documento
                doc.appendChild(parentNode);

                // Crear transformador de documento
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();

                // Formato legible (indentación y saltos de línea)
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

                // Volcar DOM en archivo
                DOMSource domSource = new DOMSource(doc);
                StreamResult streamResult = new StreamResult(fileChosen);
                transformer.transform(domSource, streamResult);

                break;

            default:
                break;
            }
        } catch (Exception e) {
            // Mostrar mensaje de error
            JOptionPane.showMessageDialog(parent, e.getLocalizedMessage(), "Error de XML", JOptionPane.ERROR_MESSAGE);
        }
    }

}
