package modelo;

import java.time.LocalDate;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {

        System.out.println("=== INICIANDO SISTEMA DE CLÍNICA ODONTOLÓGICA ===\n");

        // 1. CREACIÓN DE DOMICILIOS
        // Creamos dos domicilios diferentes
        Domicilio domFamiliaPerez = new Domicilio("Av. San Martín", "1500", "Florida", "Buenos Aires");
        Domicilio domGomez = new Domicilio("Calle Belgrano", "345", "Olivos", "Buenos Aires");

        // 2. CREACIÓN DE PACIENTES (3)
        // Dos pacientes comparten la misma referencia de domicilio (Familia)
        Paciente p1 = new Paciente("Juan", "Pérez", "11111111", "juan@mail.com", "11-4444-5555", domFamiliaPerez);
        Paciente p2 = new Paciente("Ana", "Pérez", "22222222", "ana@mail.com", "11-4444-6666", domFamiliaPerez);
        Paciente p3 = new Paciente("Carlos", "Gómez", "33333333", "carlos@mail.com", "11-7777-8888", domGomez);

        // 3. CREACIÓN DE ODONTÓLOGOS (3)
        Odontologo o1 = new Odontologo("Roberto", "García", "MAT-001");
        o1.agregarEspecialidad("Odontología General");
        o1.agregarEspecialidad("Extracciones"); // Comprobamos que el Set guarda varias especialidades

        Odontologo o2 = new Odontologo("María", "Fernández", "MAT-002");
        o2.agregarEspecialidad("Ortodoncia");

        Odontologo o3 = new Odontologo("Laura", "Martínez", "MAT-003");
        o3.agregarEspecialidad("Implantología");

        // 4. CREACIÓN DE TURNOS (2)
        // Turno 1: Para la semana que viene (Futuro)
        LocalDate fechaTurno1 = LocalDate.now().plusDays(7); // Hoy + 7 días
        LocalTime horaTurno1 = LocalTime.of(10, 30); // 10:30 AM
        Turno turno1 = new Turno(p1, o1, fechaTurno1, horaTurno1);

        // Turno 2: Hace 3 días (Pasado)
        LocalDate fechaTurno2 = LocalDate.now().minusDays(3); // Hoy - 3 días
        LocalTime horaTurno2 = LocalTime.of(15, 0); // 15:00 PM
        Turno turno2 = new Turno(p3, o2, fechaTurno2, horaTurno2);
        turno2.cambiarEstado(EstadoTurno.COMPLETADO); // Simulamos que ya se atendió

        // 5. IMPRESIÓN Y PRUEBA DEL MOTOR DE OBJETOS
        System.out.println("--- LISTADO DE ODONTÓLOGOS ---");
        System.out.println(o1);
        System.out.println(o2);
        System.out.println(o3);

        System.out.println("\n--- LISTADO DE PACIENTES ---");
        System.out.println(p1);

        System.out.println("\n--- GESTIÓN DE TURNOS ---");
        System.out.println(turno1);
        System.out.println("Validación temporal -> ¿Es un turno a futuro?: " + turno1.esFuturo());

        System.out.println("\n-------------------------------------------------");

        System.out.println(turno2);
        System.out.println("Validación temporal -> ¿Es un turno a futuro?: " + turno2.esFuturo());

        System.out.println("\n=== FIN DE LAS PRUEBAS ===");
    }
}
