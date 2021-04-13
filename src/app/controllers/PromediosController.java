package app.controllers;


public class PromediosController {
    public static double[] getPromediosPorEstudiante(double[][] calificaciones) {
        // Calificaciones por estudiante (por fila)
        double[] promedios = new double[calificaciones.length];
        
        for (int i = 0; i < calificaciones.length; i++) {
            // Sumatoria de notas
            for (int j = 0; j < calificaciones[i].length; j++) {
                promedios[i] += calificaciones[i][j];
            }

            // División por número de notas
            promedios[i] /= calificaciones[i].length;
        }

        return promedios;
    }

    public static double[] getPromediosPorMateria(double[][] calificaciones) {
        // Calificaciones por materia (por columna)
        double[] promedios = new double[calificaciones[0].length];
    
        // Asumiendo igual número de columnas por fila
        for (int i = 0; i < calificaciones[0].length; i++) {
            // Sumatoria de notas
            for (int j = 0; j < calificaciones.length; j++) {
                promedios[i] += calificaciones[j][i];
            }

            // División por número de notas
            promedios[i] /= calificaciones[0].length;
        }

        return promedios;
    }
}