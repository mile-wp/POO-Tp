package controller;
//Agregamos * para que pase toda la información de las clases
import entity.*;
import service.*;
import java.util.List;
import java.util.Optional;

public class ClinicaController {
    private final PacienteService pacienteService;
    private final OdontologoService odontologoService;
    private final TurnoService turnoService;

    public ClinicaController() {
        this.pacienteService = new PacienteService();
        this.odontologoService = new OdontologoService();
        this.turnoService = new TurnoService(pacienteService, odontologoService);
    }

    // --- Métodos de Delegación ---
    public Paciente registrarPaciente(Paciente p) { return pacienteService.registrar(p); }
    public List<Paciente> listarPacientes() { return pacienteService.listarTodos(); }

    public Odontologo registrarOdontologo(Odontologo o) { return odontologoService.registrar(o); }
    public List<Odontologo> listarOdontologos() { return odontologoService.listarTodos(); }

    public Turno agendarTurno(Turno t) { return turnoService.registrar(t); }
    public List<Turno> listarTurnos() { return turnoService.listarTodos(); }

    public Optional<Paciente> buscarPacienteId(Long id) { return pacienteService.buscarPorId(id); }
    public Optional<Odontologo> buscarOdontologoId(Long id) { return odontologoService.buscarPorId(id); }


}

