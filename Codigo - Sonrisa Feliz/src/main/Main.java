package main;

import java.time.LocalDate;
import java.time.LocalTime;

// Importamos todas las entidades desde nuestro paquete de negocio
import modelo.Paciente;
import modelo.Odontologo;
import modelo.Turno;
import modelo.EstadoTurno;
import modelo.Especialidad;

public class Main {
    public static void main(String[] args) {

        System.out.println("=== INICIANDO SISTEMA DE CLÍNICA ODONTOLÓGICA ===\n");

        // ---------------------------------------------------------
        // FASE 1: CREACIÓN DE OBJETOS EN MEMORIA
        // ---------------------------------------------------------

        // PACIENTES (Aplicando COMPOSICIÓN estricta)
        // Ya no existe el "new Domicilio()" aquí. El Main solo pasa la receta,
        // y es el Paciente quien construye y es dueño de su casa. Utilizamos el constructor sin ID.
        Paciente p1 = new Paciente("Juan", "Pérez", "11111111", "juan@mail.com",
                LocalDate.of(2025, 1, 1), "Av. San Martín", "1500", "Florida", "Buenos Aires");

        Paciente p2 = new Paciente("Ana", "Pérez", "22222222", "ana@mail.com",
                LocalDate.of(2026, 1, 1), "Av. San Martín", "1500", "Florida", "Buenos Aires");

        Paciente p3 = new Paciente("Carlos", "Gómez", "33333333", "carlos@mail.com",
                LocalDate.of(2023, 1, 12), "Calle Belgrano", "345", "Olivos", "Buenos Aires");

        // ODONTÓLOGOS (Aplicando regla * a 1 con Enums)
        // Cada médico nace con su única especialidad fuertemente tipada. Utilizamos el constructor sin ID.
        Odontologo o1 = new Odontologo("Roberto", "García", "MAT-001", Especialidad.ODONTOLOGIA_GENERAL);
        Odontologo o2 = new Odontologo("María", "Fernández", "MAT-002", Especialidad.ORTODONCIA);
        Odontologo o3 = new Odontologo("Laura", "Martínez", "MAT-003", Especialidad.IMPLANTOLOGIA);

        // ---------------------------------------------------------
        // FASE 2: PRUEBA DE LÓGICA DE NEGOCIO Y RELACIONES
        // ---------------------------------------------------------

        // Turno 1: Futuro
        LocalDate fechaTurno1 = LocalDate.now().plusDays(7);
        LocalTime horaTurno1 = LocalTime.of(10, 30);

        // ¡MAGIA DE LA INTEGRIDAD BIDIRECCIONAL!
        // Al ejecutar esta línea, el turno se guarda solo en las listas de 'p1' y 'o1'.
        Turno turno1 = new Turno(p1, o1, fechaTurno1, horaTurno1);

        // Turno 2: Pasado y completado
        LocalDate fechaTurno2 = LocalDate.now().minusDays(3);
        LocalTime horaTurno2 = LocalTime.of(15, 0);
        Turno turno2 = new Turno(p3, o2, fechaTurno2, horaTurno2);
        turno2.cambiarEstado(EstadoTurno.COMPLETADO);

        // ---------------------------------------------------------
        // FASE 3: VERIFICACIÓN VISUAL EN CONSOLA
        // ---------------------------------------------------------

        System.out.println("--- ESTADO DE TURNOS ---");
        System.out.println("Turno 1 -> ¿Es a futuro?: " + turno1.esFuturo());
        System.out.println("Turno 2 -> ¿Es a futuro?: " + turno2.esFuturo());

        System.out.println("\n--- LISTADO DE PACIENTES ---");
        System.out.println(p1);
        System.out.println(p2);

        System.out.println("\n--- LISTADO DE ODONTÓLOGOS ---");
        System.out.println(o1);
        System.out.println(o2);

        System.out.println("\n--- PRUEBA DE INTEGRIDAD REFERENCIAL BIDIRECCIONAL ---");
        System.out.println("Agenda del Dr. " + o1.getApellido() + " tiene " + o1.getTurnos().size() + " turno(s) agendado(s).");
        System.out.println("Historial del paciente " + p1.getNombre() + " tiene " + p1.getTurnos().size() + " turno(s) registrado(s).");

        System.out.println("\n=== FIN DE LAS PRUEBAS ===");
    }
}
