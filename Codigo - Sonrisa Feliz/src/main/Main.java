package main;

import entity.Paciente;
import repository.PacienteRepository;

import java.time.LocalDate;
import java.util.Optional;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== INICIANDO PRUEBA DE LA CAPA REPOSITORY ===\n");

        // 1. Instanciamos nuestra "Base de Datos"
        PacienteRepository pacienteRepo = new PacienteRepository();

        // 2. CREATE (Crear): Preparamos dos pacientes SIN ID (el repo debe asignarlo)
        Paciente p1 = new Paciente(null, "Juan", "Pérez", "11111111", "juan@mail.com", "1234", LocalDate.now(), "Calle 1", "100", "Quilmes", "BsAs");
        Paciente p2 = new Paciente(null, "María", "Gómez", "22222222", "maria@mail.com", "5678", LocalDate.now(), "Calle 2", "200", "Quilmes", "BsAs");

        System.out.println("Guardando pacientes...");
        pacienteRepo.guardar(p1);
        pacienteRepo.guardar(p2);

        // Comprobamos que el repositorio les asignó IDs automáticamente (1 y 2)
        System.out.println("✅ Paciente 1 guardado con ID: " + p1.getId());
        System.out.println("✅ Paciente 2 guardado con ID: " + p2.getId());

        // 3. READ (Leer todos): Comprobamos el tamaño de la lista
        System.out.println("\nConsultando todos los pacientes...");
        System.out.println("✅ Total de pacientes en la clínica: " + pacienteRepo.buscarTodos().size());

        // 4. READ (Leer por ID con Optional): Buscamos el ID 1
        System.out.println("\nBuscando paciente con ID 1...");
        Optional<Paciente> pacienteEncontrado = pacienteRepo.buscarPorId(1L);

        if (pacienteEncontrado.isPresent()) {
            System.out.println("✅ Encontrado: " + pacienteEncontrado.get().getNombre() + " " + pacienteEncontrado.get().getApellido());
        } else {
            System.out.println("❌ No se encontró el paciente.");
        }

        // 5. DELETE (Eliminar): Borramos al paciente 1
        System.out.println("\nEliminando paciente con ID 1...");
        pacienteRepo.eliminar(1L);

        // 6. Verificamos que se haya eliminado
        System.out.println("Consultando total de pacientes nuevamente...");
        System.out.println("✅ Total de pacientes tras borrar: " + pacienteRepo.buscarTodos().size());

        // Intentamos buscar el ID 1 otra vez para ver cómo actúa el Optional vacío
        Optional<Paciente> busquedaFallida = pacienteRepo.buscarPorId(1L);
        if (busquedaFallida.isEmpty()) {
            System.out.println("✅ Búsqueda correcta: El paciente 1 ya no existe en el sistema.");
        }

        System.out.println("\n=== PRUEBA DEL REPOSITORIO FINALIZADA CON ÉXITO ===");
    }
}