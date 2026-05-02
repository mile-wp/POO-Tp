package main;

import entity.Odontologo;
import entity.Paciente;
import entity.Turno;
import service.OdontologoService;
import service.PacienteService;
import service.TurnoService;
import java.time.LocalDate;
import java.time.LocalTime;

public class Main {

    public static void main(String[] args) {

        System.out.println("=== INICIANDO TEST DE INTEGRACIÓN Y REGLAS DE NEGOCIO ===\n");

        // 1. COMPARTIENDO LA MEMORIA (Inyección de dependencias manual)
        PacienteService pacienteService = new PacienteService();
        OdontologoService odontologoService = new OdontologoService();
        TurnoService turnoService = new TurnoService(pacienteService, odontologoService);

        // ==========================================
        // PRUEBA 1: VALIDACIONES DE PACIENTE
        // ==========================================
        System.out.println("--- PRUEBA 1: PACIENTES ---");
        Paciente p1 = new Paciente();
        p1.setNombre("Juan");
        p1.setApellido("Perez");
        p1.setDni("11111111");

        Paciente p2 = new Paciente();
        p2.setNombre("Maria");
        p2.setApellido("Gomez");
        p2.setDni("11111111"); // DNI DUPLICADO INTENCIONALMENTE

        pacienteService.registrar(p1); // Debería funcionar
        pacienteService.registrar(p2); // Debería dar ERROR de DNI duplicado

        // ==========================================
        // PRUEBA 2: VALIDACIONES DE ODONTÓLOGO
        // ==========================================
        System.out.println("\n--- PRUEBA 2: ODONTÓLOGOS ---");
        Odontologo o1 = new Odontologo();
        o1.setNombre("Dr. Carlos");
        o1.setApellido("Lopez");
        o1.setMatricula("MP-100");

        Odontologo o2 = new Odontologo();
        o2.setNombre("Dra. Ana");
        o2.setApellido("Martinez");
        o2.setMatricula("MP-100"); // MATRÍCULA DUPLICADA INTENCIONALMENTE

        odontologoService.registrar(o1); // Debería funcionar
        odontologoService.registrar(o2); // Debería dar ERROR de Matrícula duplicada

        // ==========================================
        // PRUEBA 3: VALIDACIONES DE TURNO (EL PLATO FUERTE)
        // ==========================================
        System.out.println("\n--- PRUEBA 3: TURNOS ---");

        // Turno 1: Éxito total (Turno para un paciente y doc que existen, en horario válido)
        Turno turnoValido = new Turno();
        turnoValido.setPaciente(p1); // Juan (ID 1)
        turnoValido.setOdontologo(o1); // Dr. Carlos (ID 1)
        // Usamos una fecha futura (ej: 10 de Mayo de 2026) y hora válida (10:00 AM)
        turnoValido.setFecha(LocalDate.of(2026, 5, 10));
        turnoValido.setHora(LocalTime.of(10, 0));

        System.out.println("> Intentando registrar Turno 1 (Válido)...");
        turnoService.registrar(turnoValido); // Debería registrarse con éxito

        // Turno 2: Falla por Viaje en el Tiempo (Fecha en el pasado)
        Turno turnoPasado = new Turno();
        turnoPasado.setPaciente(p1);
        turnoPasado.setOdontologo(o1);
        turnoPasado.setFecha(LocalDate.of(2025, 1, 1)); // Año pasado
        turnoPasado.setHora(LocalTime.of(10, 0));

        System.out.println("\n> Intentando registrar Turno 2 (Fecha pasada)...");
        turnoService.registrar(turnoPasado); // Debería dar ERROR

        // Turno 3: Falla por Horario de Clínica (Ej: 20:00 PM)
        Turno turnoFueraDeHora = new Turno();
        turnoFueraDeHora.setPaciente(p1);
        turnoFueraDeHora.setOdontologo(o1);
        turnoFueraDeHora.setFecha(LocalDate.of(2026, 5, 11));
        turnoFueraDeHora.setHora(LocalTime.of(20, 0)); // La clínica cierra a las 18:00

        System.out.println("\n> Intentando registrar Turno 3 (Fuera de horario)...");
        turnoService.registrar(turnoFueraDeHora); // Debería dar ERROR

        // Turno 4: Falla por Choque de Agenda (Mismo Doc, misma fecha y hora que Turno 1)
        Turno turnoChoque = new Turno();
        turnoChoque.setPaciente(p1);
        turnoChoque.setOdontologo(o1);
        turnoChoque.setFecha(LocalDate.of(2026, 5, 10)); // Misma fecha que el turno 1
        turnoChoque.setHora(LocalTime.of(10, 0));        // Misma hora que el turno 1

        System.out.println("\n> Intentando registrar Turno 4 (Choque de horarios)...");
        turnoService.registrar(turnoChoque); // Debería dar ERROR

        System.out.println("\n=== TEST FINALIZADO ===");
    }
}