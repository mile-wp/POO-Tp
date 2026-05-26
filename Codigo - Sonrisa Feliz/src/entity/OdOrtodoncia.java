package entity;

public class OdOrtodoncia extends Odontologo {

    public OdOrtodoncia() {}

    public OdOrtodoncia(Long id, String nombre, String apellido, String dni, String email, String telefono, String matricula, Double recargoEspecialidad) {
        super(id, nombre, apellido, dni, email, telefono, matricula, recargoEspecialidad);
    }

}

