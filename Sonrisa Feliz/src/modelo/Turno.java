package modelo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.Objects;

public class Turno {
    private Long id;
    private Paciente paciente;
    private Odontologo odontologo;
    private LocalDate fecha;
    private LocalTime hora;
    private EstadoTurno estado;

    // Constructor: El ID se deja para ser asignado por el sistema/DB
    // El estado inicial suele ser PENDIENTE por defecto
    public Turno(Paciente paciente, Odontologo odontologo, LocalDate fecha, LocalTime hora) {
        this.paciente = paciente;
        this.odontologo = odontologo;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = EstadoTurno.PENDIENTE;
    }

    // Lógica de negocio: Verifica si el turno es posterior al momento actual
    public boolean esFuturo() {
        if (fecha == null || hora == null) return false;

        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime momentoTurno = LocalDateTime.of(this.fecha, this.hora);

        return momentoTurno.isAfter(ahora);
    }

    // Método para cambiar el estado (Útil para cancelaciones o asistencias)
    public void cambiarEstado(EstadoTurno nuevoEstado) {
        this.estado = nuevoEstado;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Odontologo getOdontologo() {
        return odontologo;
    }

    public void setOdontologo(Odontologo odontologo) {
        this.odontologo = odontologo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public EstadoTurno getEstado() {
        return estado;
    }

    public void setEstado(EstadoTurno estado) {
        this.estado = estado;
    }

    public String toString() {
        return "Turno #" + (id != null ? id : "S/N") + " [" + estado + "]\n" +
                "Fecha: " + fecha + " a las " + hora + "hs\n" +
                "Paciente: " + paciente.getNombre() + " " + paciente.getApellido() + "\n" +
                "Odontólogo: " + odontologo.getNombre() + " " + odontologo.getApellido() + " (Mat: " + odontologo.getMatricula() + ")";
    }
}