package service;

import entity.Odontologo;
import entity.OdOrtodoncia;
import entity.OdEndodoncia;
import entity.OdExtraccion;
import entity.Paciente;
import entity.PacienteParticular;
import entity.PacienteObraSocial;
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

            if (t.getOdontologo().getId().equals(turno.getOdontologo().getId()) && t.getFecha().equals(turno.getFecha())) {

                long diferenciaMinutos = java.time.Duration.between(t.getHora(), turno.getHora()).abs().toMinutes();

                if (diferenciaMinutos < 30) {
                    System.out.println("Error: Choque de agenda. El odontólogo ya tiene un turno a las "
                            + t.getHora() + ". Debe haber una diferencia mínima de 30 minutos.");
                    return null;
                }
            }
        }

        // ==========================================
        // REGLA 5: CÁLCULO DE FACTURACIÓN (Polimorfismo)
        // ==========================================
        Paciente pacienteReal = pacienteExistente.get();
        Odontologo odontologoReal = odontologoExistente.get();

        System.out.println("\n--- DETALLE DE FACTURACIÓN ---");

        if (pacienteReal instanceof PacienteParticular) {
            PacienteParticular pP = (PacienteParticular) pacienteReal;
            double costoFinal = pP.getTarifaBase();

            if (odontologoReal instanceof OdOrtodoncia) {
                costoFinal *= ((OdOrtodoncia) odontologoReal).getRecargoEspecialidad();
            } else if (odontologoReal instanceof OdEndodoncia) {
                costoFinal *= ((OdEndodoncia) odontologoReal).getRecargoEspecialidad();
            } else if (odontologoReal instanceof OdExtraccion) {
                costoFinal *= ((OdExtraccion) odontologoReal).getRecargoEspecialidad();
            }

            // ¡NUEVO! Guardamos el valor calculado en el objeto
            turno.setMontoFacturacion(costoFinal);

            System.out.println("Tipo de cobertura: PACIENTE PARTICULAR");
            System.out.println("Monto a abonar en mostrador: $" + costoFinal);

        } else if (pacienteReal instanceof PacienteObraSocial) {
            PacienteObraSocial pOS = (PacienteObraSocial) pacienteReal;

            // ¡NUEVO! Guardamos 0.0 porque está cubierto
            turno.setMontoFacturacion(0.0);

            System.out.println("Tipo de cobertura: OBRA SOCIAL");
            System.out.println("Entidad a facturar: " + pOS.getNombreObraSocial());
            System.out.println("Monto a abonar en mostrador: $0.0 (Cubierto)");
        }

        System.out.println("------------------------------\n");

        // ==========================================
        // ÉXITO: GUARDAMOS EL TURNO
        // ==========================================
        turno.setPaciente(pacienteReal);
        turno.setOdontologo(odontologoReal);
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