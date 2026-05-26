package entity;

public class OdExtraccion extends Odontologo {

    public OdExtraccion() {}

    public OdExtraccion(Long id, String nombre, String apellido, String dni, String email, String telefono, String matricula, Double recargoEspecialidad) {
        super(id, nombre, apellido, dni, email, telefono, matricula, recargoEspecialidad);
    }


}