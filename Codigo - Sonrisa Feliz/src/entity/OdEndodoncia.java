package entity;

public class OdEndodoncia extends Odontologo {
    
    public OdEndodoncia() {}

    public OdEndodoncia(Long id, String nombre, String apellido, String dni, String email, String telefono, String matricula, Double recargoEspecialidad) {
        super(id, nombre, apellido, dni, email, telefono, matricula, recargoEspecialidad);
    }

}