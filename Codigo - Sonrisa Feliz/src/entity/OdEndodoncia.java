package entity;

public class OdEndodoncia extends Odontologo {
    private Double recargoEspecialidad;

    public OdEndodoncia() {}

    public OdEndodoncia(Long id, String nombre, String apellido, String dni, String email, String telefono, String matricula, Double recargoEspecialidad) {
        super(id, nombre, apellido, dni, email, telefono, matricula);
        this.recargoEspecialidad = recargoEspecialidad;
    }

    public Double getRecargoEspecialidad() { return recargoEspecialidad; }
    public void setRecargoEspecialidad(Double recargoEspecialidad) { this.recargoEspecialidad = recargoEspecialidad; }
}