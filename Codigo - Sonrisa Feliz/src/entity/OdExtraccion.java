package entity;

public class OdExtraccion extends Odontologo {
    private Double recargoEspecialidad;

    public OdExtraccion() {}

    public OdExtraccion(Long id, String nombre, String apellido, String dni, String email, String telefono, String matricula, Double recargoEspecialidad) {
        super(id, nombre, apellido, dni, email, telefono, matricula);
        this.recargoEspecialidad = recargoEspecialidad;
    }

    public Double getRecargoEspecialidad() { return recargoEspecialidad; }
    public void setRecargoEspecialidad(Double recargoEspecialidad) { this.recargoEspecialidad = recargoEspecialidad; }
}