package app.models;

import javax.swing.table.AbstractTableModel;

public class PromedioEstudiantesTableModel extends AbstractTableModel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String[] nombres;
    private String[] apellidos;
    private double[] promedios;

    public static final int NOMBRE_COL = 0;
    public static final int APELLIDO_COL = 1;
    public static final int PROMEDIO_COL = 2;

     // Revisar si la ubicación en la tabla es válida
    private boolean esLocacionValida(int rowIndex, int columnIndex) {
        return rowIndex < nombres.length && columnIndex < 3;
    }

    // Setters
    public void setNombres(String[] nombres) {
        this.nombres = nombres;
    }

    public void setApellidos(String[] apellidos) {
        this.apellidos = apellidos;
    }

    public void setPromedios(double[] promedios) {
        this.promedios = promedios;
    }

    @Override
    public int getRowCount() {
        return nombres.length;
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public String getColumnName(int columnIndex) {
        if (columnIndex == 0) {
            return "Nombre";
        }

        if (columnIndex == 1) {
            return "Apellido";
        }

        return "Promedio";
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex) {
            case 0:
            case 1:
                return String.class;
        }

        return Double.class;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (esLocacionValida(rowIndex, columnIndex)) {
            switch(columnIndex) {
                case 0:
                    return nombres[rowIndex];
                case 1:
                    return apellidos[rowIndex];    
                default:
                    return promedios[rowIndex];
            }
        }

        return null;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}
