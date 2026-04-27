package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Paciente {
    private Long id;
    private String nombre;
    private String apellido;
    private String dni;
    private String email;
    private LocalDate fechaIngreso;

    // Relación de COMPOSICIÓN (El paciente es dueño de su domicilio)
    private Domicilio domicilio;

    // Relación de ASOCIACIÓN (El paciente tiene un historial cronológico de citas)
    private List<Turno> turnos;

    // -----------------------------------------------------------------------------
    // CONSTRUCTOR 1: Para pacientes NUEVOS (Sin ID, lo asignará la Base de Datos)
    // Fíjate que recibe los datos de la dirección sueltos, no un objeto Domicilio.
    // -----------------------------------------------------------------------------
    public Paciente(String nombre, String apellido, String dni, String email, LocalDate fechaIngreso,
                    String calle, String altura, String localidad, String provincia) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.email = email;
        this.fechaIngreso = fechaIngreso;

        // COMPOSICIÓN PURA: El Paciente fabrica y controla la vida de su Domicilio
        this.domicilio = new Domicilio(calle, altura, localidad, provincia);

        // PREVENCIÓN DE ERRORES: Inicializamos la lista vacía
        this.turnos = new ArrayList<>();
    }

    // -----------------------------------------------------------------------------
    // CONSTRUCTOR 2: Para pacientes EXISTENTES (Con ID, recuperados de la Base de Datos)
    // -----------------------------------------------------------------------------
    public Paciente(Long id, String nombre, String apellido, String dni, String email, LocalDate fechaIngreso,
                    String calle, String altura, String localidad, String provincia) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.email = email;
        this.fechaIngreso = fechaIngreso;

        // COMPOSICIÓN PURA
        this.domicilio = new Domicilio(calle, altura, localidad, provincia);

        this.turnos = new ArrayList<>();
    }

    // Método de negocio para registrar citas médicas
    public void agregarTurno(Turno turno) {
        this.turnos.add(turno);
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getNombreCompleto() { return this.apellido + ", " + this.nombre; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDate getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(LocalDate fechaIngreso) { this.fechaIngreso = fechaIngreso; }

    public Domicilio getDomicilio() { return domicilio; }
    public void setDomicilio(Domicilio domicilio) { this.domicilio = domicilio; }

    public List<Turno> getTurnos() { return turnos; }
    public void setTurnos(List<Turno> turnos) { this.turnos = turnos; }

    // Sobrescritura de equals utilizando identidad de negocio (DNI)
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

    // Impresión elegante. (Omitimos imprimir la lista de turnos para evitar bucles infinitos en consola)
    @Override
    public String toString() {
        String idMostrar = (id != null) ? String.valueOf(id) : "S/N";
        return "Paciente [ID: " + idMostrar + "] " + apellido + ", " + nombre +
                " | DNI: " + dni + " | Email: " + email + " | Vive en: " + domicilio.toString();
    }
}