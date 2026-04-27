package modelo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

public class Turno {
    private Long id;
    private Paciente paciente;
    private Odontologo odontologo;
    private LocalDate fecha;
    private LocalTime hora;
    private EstadoTurno estado;

    // -----------------------------------------------------------------------------
    // CONSTRUCTOR 1: Para turnos NUEVOS (Sin ID, nacen como PENDIENTES)
    // -----------------------------------------------------------------------------
    public Turno(Paciente paciente, Odontologo odontologo, LocalDate fecha, LocalTime hora) {
        this.paciente = paciente;
        this.odontologo = odontologo;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = EstadoTurno.PENDIENTE; // Por defecto al nacer

        // INTEGRIDAD REFERENCIAL BIDIRECCIONAL:
        // El turno se auto-registra en las listas de sus protagonistas
        if (paciente != null) {
            this.paciente.agregarTurno(this);
        }
        if (odontologo != null) {
            this.odontologo.agregarTurno(this);
        }
    }

    // -----------------------------------------------------------------------------
    // CONSTRUCTOR 2: Para turnos EXISTENTES (Con ID y Estado recuperados de la BD)
    // -----------------------------------------------------------------------------
    public Turno(Long id, Paciente paciente, Odontologo odontologo, LocalDate fecha, LocalTime hora, EstadoTurno estado) {
        this.id = id;
        this.paciente = paciente;
        this.odontologo = odontologo;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado; // Recupera el estado exacto de la base de datos

        // INTEGRIDAD REFERENCIAL BIDIRECCIONAL
        if (paciente != null) {
            this.paciente.agregarTurno(this);
        }
        if (odontologo != null) {
            this.odontologo.agregarTurno(this);
        }
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
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }

    public Odontologo getOdontologo() { return odontologo; }
    public void setOdontologo(Odontologo odontologo) { this.odontologo = odontologo; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public LocalTime getHora() { return hora; }
    public void setHora(LocalTime hora) { this.hora = hora; }

    public EstadoTurno getEstado() { return estado; }
    public void setEstado(EstadoTurno estado) { this.estado = estado; }

    // toString protegido contra bucles infinitos (imprime nombres, no los objetos completos)
    @Override
    public String toString() {
        String idMostrar = (id != null) ? String.valueOf(id) : "S/N";
        return "Turno #" + idMostrar + " [" + estado + "]\n" +
                "  -> Fecha: " + fecha + " a las " + hora + "hs\n" +
                "  -> Paciente: " + paciente.getNombreCompleto() + "\n" +
                "  -> Odontólogo: " + odontologo.getNombreCompleto() + " (Mat: " + odontologo.getMatricula() + ")";
    }
}
