package interfaces;

import modelo.EstadoTurno;
import modelo.Odontologo;
import modelo.Paciente;

import java.time.LocalDate;
import java.time.LocalTime;

public interface ITurno {

    Long getId();
    void setId(Long Id);

    Paciente getPaciente();
    void setPaciente(Paciente paciente);

    Odontologo getOdontologo();
    void setOdontologo(Odontologo odontologo);


    LocalDate getFecha();
    void setFecha(LocalDate fecha);


    LocalTime GetHora();
    void setHora(LocalTime hora);

    EstadoTurno getEstado();
    void setEstado(EstadoTurno estado);
}

