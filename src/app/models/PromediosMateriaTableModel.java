package app.models;

import javax.swing.table.AbstractTableModel;

public class PromediosMateriaTableModel extends AbstractTableModel {
    
    
        /**
     *
     */
    private static final long serialVersionUID = 1L;


    private double[] promedios;
    private String[] materias;

    public static final int MATERIAS_COL = 0;
    public static final int PROMEDIO_COL = 1;

     // Revisar si la ubicación en la tabla es válida
    private boolean esLocacionValida(int rowIndex, int columnIndex) {
        return rowIndex < promedios.length && columnIndex < 2;
    }

    // Setters
    public void setPromedios(double[] promedios) {
        this.promedios = promedios;
    }
    
    public void setMaterias(String[] materias) {
        this.materias = materias;
    }

    @Override
    public int getRowCount() {
        return promedios.length;
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public String getColumnName(int columnIndex) {
        if (columnIndex == 0) {
            return "Materia";
        }

        return "Promedio";
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex) {
            case 0:
                return String.class;
        }

        return Double.class;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (esLocacionValida(rowIndex, columnIndex)) {
            switch(columnIndex) {
                case 0:
                    return materias[rowIndex];
            }
    
            return promedios[rowIndex];
        }

        return null; 
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}
