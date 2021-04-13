package app.models;

public class Estudiante implements Comparable<Estudiante> {
    private String apellido;
    private String nombre;
    private double promedio;

    public Estudiante(String nombre, String apellido, double promedio) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.promedio = promedio;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPromedio() {
        return promedio;
    }

    public void setPromedio(double promedio) {
        this.promedio = promedio;
    }

    public String toString() {
        return String.format("%s %s", (nombre.compareTo("") != 0 ? nombre : "(?)"), 
            (apellido.compareTo("") != 0 ? apellido : "(?)"), promedio);
    }

    @Override
    public int compareTo(Estudiante o) {
        if (this.promedio > o.promedio) {
            return 1;
        }

        if (this.promedio < o.promedio) {
            return -1;
        }
        
        return 0;
    }
}
