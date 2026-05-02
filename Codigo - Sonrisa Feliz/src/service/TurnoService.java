package service;

import entity.Odontologo;
import entity.Paciente;
import entity.Turno;
import entity.EstadoTurno;
import repository.IRepository;
import repository.TurnoRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class TurnoService implements IService<Turno> {

    private IRepository<Turno> turnoRepository;
    private PacienteService pacienteService;
    private OdontologoService odontologoService;

    public TurnoService(PacienteService pacienteService, OdontologoService odontologoService) {
        this.turnoRepository = new TurnoRepository();
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
    }

    @Override
    public Turno registrar(Turno turno) {

        System.out.println("Iniciando validaciones para agendar nuevo turno...");

        // ==========================================
        // REGLA 1: INTEGRIDAD REFERENCIAL (Actores)
        // ==========================================
        if (turno.getPaciente() == null || turno.getPaciente().getId() == null) {
            System.out.println("Error: El turno debe tener un Paciente con un ID asignado.");
            return null;
        }

        Optional<Paciente> pacienteExistente = pacienteService.buscarPorId(turno.getPaciente().getId());
        if (pacienteExistente.isEmpty()) {
            System.out.println("Error: No se puede agendar el turno. El Paciente con ID " + turno.getPaciente().getId() + " no existe.");
            return null;
        }

        if (turno.getOdontologo() == null || turno.getOdontologo().getId() == null) {
            System.out.println("Error: El turno debe tener un Odontólogo con un ID asignado.");
            return null;
        }

        Optional<Odontologo> odontologoExistente = odontologoService.buscarPorId(turno.getOdontologo().getId());
        if (odontologoExistente.isEmpty()) {
            System.out.println("Error: No se puede agendar el turno. El Odontólogo con ID " + turno.getOdontologo().getId() + " no existe.");
            return null;
        }

        // ==========================================
        // REGLA 2: LÓGICA TEMPORAL
        // ==========================================
        if (turno.getFecha() == null || turno.getHora() == null) {
            System.out.println("Error: El turno debe tener una fecha y una hora asignadas.");
            return null;
        }

        LocalDate hoy = LocalDate.now();
        LocalTime ahora = LocalTime.now();

        // 2.a Validar que la fecha no sea anterior a hoy
        if (turno.getFecha().isBefore(hoy)) {
            System.out.println("Error: No se puede agendar un turno para una fecha pasada (" + turno.getFecha() + ").");
            return null;
        }

        // 2.b Si el turno es para hoy, validar que la hora no haya pasado ya
        if (turno.getFecha().equals(hoy) && turno.getHora().isBefore(ahora)) {
            System.out.println("Error: La hora solicitada (" + turno.getHora() + ") ya pasó en el día de la fecha.");
            return null;
        }

        // ==========================================
        // REGLA 3: HORARIOS OPERATIVOS (08:00 a 18:00)
        // ==========================================
        LocalTime horaTurno = turno.getHora();
        LocalTime horaApertura = LocalTime.of(8, 0);
        LocalTime horaCierre = LocalTime.of(18, 0);

        if (horaTurno.isBefore(horaApertura) || horaTurno.isAfter(horaCierre)) {
            System.out.println("Error: El horario solicitado (" + horaTurno + ") está fuera del horario de atención (08:00 a 18:00).");
            return null;
        }

        // ==========================================
        // REGLA 4: SUPERPOSICIÓN DE AGENDA (Choques)
        // ==========================================
        List<Turno> turnosAgendados = turnoRepository.buscarTodos();
        for (Turno t : turnosAgendados) {
            if (t.getOdontologo().getId().equals(turno.getOdontologo().getId())) {
                // Ahora debemos comparar tanto la fecha como la hora por separado
                if (t.getFecha().equals(turno.getFecha()) && t.getHora().equals(turno.getHora())) {
                    System.out.println("Error: El odontólogo ya tiene un turno asignado el " + turno.getFecha() + " a las " + turno.getHora());
                    return null;
                }
            }
        }

        // ==========================================
        // ÉXITO: GUARDAMOS EL TURNO
        // ==========================================
        turno.setPaciente(pacienteExistente.get());
        turno.setOdontologo(odontologoExistente.get());
        turno.setEstado(EstadoTurno.PENDIENTE);

        System.out.println("✅ Turno validado y agendado exitosamente para el " + turno.getFecha() + " a las " + turno.getHora());
        return turnoRepository.guardar(turno);
    }

    @Override
    public Optional<Turno> buscarPorId(Long id) {
        return turnoRepository.buscarPorId(id);
    }

    @Override
    public void eliminarPorId(Long id) {
        Optional<Turno> existente = turnoRepository.buscarPorId(id);
        if (existente.isEmpty()) {
            System.out.println("Error: No se puede cancelar. No existe turno con ID: " + id);
            return;
        }
        turnoRepository.eliminar(id);
        System.out.println("Turno cancelado correctamente.");
    }

    @Override
    public Turno actualizar(Turno turno) {
        if (turno.getId() == null) {
            System.out.println("Error: Para actualizar, el turno debe tener un ID asignado.");
            return null;
        }
        return turnoRepository.actualizar(turno);
    }

    @Override
    public List<Turno> listarTodos() {
        return turnoRepository.buscarTodos();
    }
}