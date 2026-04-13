import java.time.LocalDate;
import java.util.List;

import modelo.Domicilio;
import modelo.Paciente;
import modelo.SistemaPacientes;

public class Main {
    public static void main(String[] args) {
        
        System.out.println("¡Iniciando Sistema Sonrisa Feliz!");
        
        SistemaPacientes sistema = new SistemaPacientes();

        // Crear domicilios
        Domicilio dom1 = new Domicilio(1L, "San Martin", "123", "CABA", "Buenos Aires");
        Domicilio dom2 = new Domicilio(2L, "Belgrano", "456", "Lanus", "Buenos Aires");

        // Crear pacientes
        Paciente p1 = new Paciente(1L, "Juan", "Perez", "12345678", "juan@gmail.com",LocalDate.of(2023, 1, 12), dom1);
        Paciente p2 = new Paciente(2L, "Ana", "Gomez", "87654321", "ana@gmail.com", LocalDate.of(2023, 1, 12), dom2);

        //Registrar pacientes
        sistema.registrar(p1);
        sistema.registrar(p2);


        //Listado inicial
        System.out.println("\n=== LISTADO INICIAL ===");
        for (Paciente p : sistema.listarTodos()) {
            System.out.println(p.toString());
        }

        //Buscar por ID
        System.out.println("\n=== BUSCAR POR ID ===");
        Paciente buscadoId = sistema.buscarPorId(1L);
        System.out.println(buscadoId.toString());

        //Buscar por DNI
        System.out.println("\n=== BUSCAR POR DNI ===");
        Paciente buscadoDni = sistema.buscarPorDni("87654321");
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

        sistema.modificar(1L, nuevosDatos);

        System.out.println("\n=== DESPUES DE MODIFICAR ===");
        System.out.println(sistema.buscarPorId(1L).toString());

        //Eliminar paciente
        sistema.eliminar(2L);

        System.out.println("\n=== DESPUES DE ELIMINAR ===");
        for (Paciente p : sistema.listarTodos()) {
            System.out.println(p.toString());
        }
    }

    
}
