package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Odontologo {
    private Long id;
    private String nombre;
    private String apellido;
    private String matricula;

    // CORRECCIÓN: Atributo único, ya no es una colección (Set)
    private Especialidad especialidad;

    // Relación de Asociación (La agenda del profesional)
    private List<Turno> turnos;

    // -----------------------------------------------------------------------------
    // CONSTRUCTOR 1: Para profesionales NUEVOS (Sin ID)
    // -----------------------------------------------------------------------------
    public Odontologo(String nombre, String apellido, String matricula, Especialidad especialidad) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.matricula = matricula;
        this.especialidad = especialidad; // Asignación directa

        this.turnos = new ArrayList<>();
    }

    // -----------------------------------------------------------------------------
    // CONSTRUCTOR 2: Para profesionales EXISTENTES (Con ID)
    // -----------------------------------------------------------------------------
    public Odontologo(Long id, String nombre, String apellido, String matricula, Especialidad especialidad) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.matricula = matricula;
        this.especialidad = especialidad; // Asignación directa

        this.turnos = new ArrayList<>();
    }

    // Métodos de negocio
    public void agregarTurno(Turno turno) {
        this.turnos.add(turno);
    }

    public String getNombreCompleto() {
        return this.apellido + ", " + this.nombre;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    // Getter y Setter actualizados para un solo Enum
    public Especialidad getEspecialidad() { return especialidad; }
    public void setEspecialidad(Especialidad especialidad) { this.especialidad = especialidad; }

    public List<Turno> getTurnos() { return turnos; }
    public void setTurnos(List<Turno> turnos) { this.turnos = turnos; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Odontologo that = (Odontologo) o;
        return Objects.equals(matricula, that.matricula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matricula);
    }

    @Override
    public String toString() {
        String idMostrar = (id != null) ? String.valueOf(id) : "S/N";
        return "Odontólogo [ID: " + idMostrar + "] " + apellido + ", " + nombre +
                " | Matrícula: " + matricula +
                " | Especialidad: " + especialidad; // Imprime la única especialidad
    }
}