package entity;

import java.time.LocalDate;

public class Paciente extends Persona {
    private LocalDate fechaIngreso;
    private Domicilio domicilio;

    public Paciente() {
    }

    public Paciente(Long id, String nombre, String apellido, String dni, String email, String telefono, LocalDate fechaIngreso,
                    String calle, String altura, String localidad, String provincia) {

        super(id, nombre, apellido, dni, email, telefono);

        this.fechaIngreso = fechaIngreso;

        this.domicilio = new Domicilio(calle, altura, localidad, provincia);
    }

    // Getters y Setters
    public LocalDate getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(LocalDate fechaIngreso) { this.fechaIngreso = fechaIngreso; }

    public Domicilio getDomicilio() { return domicilio; }
    public void setDomicilio(Domicilio domicilio) { this.domicilio = domicilio; }

    @Override
    public String toString() {
        return "Paciente [ID= " + getId() + ", Nombre= " + getNombre() + " " + getApellido() +
                ", DNI= " + getDni() + ", Email= " + getEmail() + " "+ ", Teléfono= " + getTelefono() + " " + ", Domicilio= " + domicilio.toString() + "]";
    }
}