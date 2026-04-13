package modelo;

import interfaces.ITurno;

import java.time.LocalDate;
import java.time.LocalTime;

public class Turno implements ITurno {
    private Long id;
    private Paciente paciente;
    private Odontologo odontologo;
    private LocalDate fecha;
    private LocalTime hora;
    private EstadoTurno estado;

    //Creamos constructor

    public Turno(Long id, Paciente paciente, Odontologo odontologo, LocalDate fecha, LocalTime hora, EstadoTurno estado) {
        this.id = id;
        this.paciente = paciente;
        this.odontologo = odontologo;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
    }


    //Agregamos getters y setters

    @Override
    public Long getId() {
        return 0L;
    }

    @Override
    public void setId(Long Id) {

    }

    @Override
    public Paciente getPaciente() {
        return null;
    }

    @Override
    public void setPaciente(Paciente paciente) {

    }

    @Override
    public Odontologo getOdontologo() {
        return null;
    }

    @Override
    public void setOdontologo(Odontologo odontologo) {

    }

    @Override
    public LocalDate getFecha() {
        return null;
    }

    @Override
    public void setFecha(LocalDate fecha) {

    }

    @Override
    public LocalTime GetHora() {
        return null;
    }

    @Override
    public void setHora(LocalTime hora) {

    }

    @Override
    public EstadoTurno getEstado() {
        return null;
    }

    @Override
    public void setEstado(EstadoTurno estado) {

    }
}
