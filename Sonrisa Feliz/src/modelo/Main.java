package modelo;

import java.time.LocalDate;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {

        System.out.println("=== INICIANDO SISTEMA DE CLÍNICA ODONTOLÓGICA ===\n");

        // 1. CREACIÓN DE DOMICILIOS
        // Creamos dos domicilios diferentes
        Domicilio domFamiliaPerez = new Domicilio(123L, "Av. San Martín", "1500", "Florida", "Buenos Aires");
        Domicilio domGomez = new Domicilio(456L, "Calle Belgrano", "345", "Olivos", "Buenos Aires");

        // 2. CREACIÓN DE PACIENTES (3)
        // Dos pacientes comparten la misma referencia de domicilio (Familia)
        Paciente p1 = new Paciente(1L, "Juan", "Pérez", "11111111", "juan@mail.com", LocalDate.of(2025, 1, 1), domFamiliaPerez);
        Paciente p2 = new Paciente(2L, "Ana", "Pérez", "22222222", "ana@mail.com", LocalDate.of(2026, 1, 1), domFamiliaPerez);
        Paciente p3 = new Paciente(3L, "Carlos", "Gómez", "33333333", "carlos@mail.com", LocalDate.of(2023, 1, 12), domGomez);

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

        // Crear domicilios
        Domicilio dom1 = new Domicilio(1L, "San Martin", "123", "CABA", "Buenos Aires");
        Domicilio dom2 = new Domicilio(2L, "Belgrano", "456", "Lanus", "Buenos Aires");

        // Crear pacientes
        Paciente p4 = new Paciente(1L, "Juan", "Perez", "12345678", "juan@gmail.com",LocalDate.of(2023, 1, 12), dom1);
        Paciente p5 = new Paciente(2L, "Ana", "Gomez", "87654321", "ana@gmail.com", LocalDate.of(2023, 1, 12), dom2);

        //Registrar pacientes
        Paciente.registrar(p4);
        Paciente.registrar(p5);

        //Listado de Domicilios
        System.out.println("\n=== DOMICILIOS REGISTRADOS ===");
        System.out.println(dom1.toString());
        System.out.println(dom2.toString());

        //Listado inicial
        System.out.println("\n=== LISTADO INICIAL ===");
        for (Paciente p : Paciente.listarTodos()) {
            System.out.println(p.toString());
        }

        //Buscar por ID
        System.out.println("\n=== BUSCAR POR ID ===");
        Paciente buscadoId = Paciente.buscarPorId(1L);
        System.out.println(buscadoId.toString());

        //Buscar por DNI
        System.out.println("\n=== BUSCAR POR DNI ===");
        Paciente buscadoDni = Paciente.buscarPorDni("87654321");
        System.out.println(buscadoDni.toString());

        //Modificar paciente
        Paciente nuevosDatos = new Paciente(
                1L,
                "Juan Modificado",
                "Perez Modificado",
                "11111111",
                "nuevo@gmail.com",
                LocalDate.of(2023, 1, 12),
                dom1
        );

        p4.modificar(1L, nuevosDatos);

        System.out.println("\n=== DESPUES DE MODIFICAR ===");
        System.out.println(Paciente.buscarPorId(1L).toString());

        //Eliminar paciente
        Paciente.eliminar(2L);

        System.out.println("\n=== DESPUES DE ELIMINAR LISTA DE TODOS LOS PACIENTES ===");
        for (Paciente p : Paciente.listarTodos()) {
            System.out.println(p.toString());
        }
    }

}
