package modelo;

import java.time.LocalDate;
import java.util.Objects;

public class Paciente {
    private Long id;
    private String nombre;
    private String apellido;
    private String dni;
    private String email;
    private String telefono; // Agregado para coincidir con el UML
    private LocalDate fechaIngreso;
    private Domicilio domicilio;

    // Constructor sin id y sin fechaIngreso (se genera sola)
    public Paciente(String nombre, String apellido, String dni, String email, String telefono, Domicilio domicilio) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.email = email;
        this.telefono = telefono;
        this.domicilio = domicilio;
        this.fechaIngreso = LocalDate.now(); // La fecha se setea automáticamente al crear el objeto
    }

    // Método de negocio
    public String getNombreCompleto() {
        return this.apellido + ", " + this.nombre;
    }

    // Getters y Setters reales (ya no devuelven strings vacíos ni nulos)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Domicilio getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Domicilio domicilio) {
        this.domicilio = domicilio;
    }

    // equals y hashCode basados en el DNI (La clave natural de la persona)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Paciente paciente = (Paciente) o;
        return Objects.equals(dni, paciente.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dni);
    }

    // toString (Reemplaza a mostrarPaciente() para buenas prácticas de POO)
    @Override
    public String toString() {
        return "Paciente{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", dni='" + dni + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                ", fechaIngreso=" + fechaIngreso +
                ", domicilio=" + domicilio + // Esto llamará automáticamente al toString() de Domicilio
                '}';
    }
}