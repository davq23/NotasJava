package app.models;

import java.util.List;

import javax.swing.AbstractListModel;

public class ListaEstudiantesListModel extends AbstractListModel<Estudiante> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    List<Estudiante> estudiantes;

    public void setEstudiantes(List<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
    }

    @Override
    public int getSize() {
        if (estudiantes != null) {
            return estudiantes.size();
        }
        return 0;
    }

    @Override
    public Estudiante getElementAt(int index) {
        if (index >= 0 && index < estudiantes.size()) {
            return estudiantes.get(index);
        }

        return null;
    }
    
}
