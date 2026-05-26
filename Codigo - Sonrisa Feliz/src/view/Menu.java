package view;

import controller.ClinicaController;
import entity.*;
import service.ClinicaException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Menu {

    private final ClinicaController controller;
    private final Scanner scanner;

    public Menu() {
        this.controller = new ClinicaController();
        this.scanner = new Scanner(System.in);
    }

    // ==========================================
    // MÉTODOS DE VALIDACIÓN LOCAL DE ENTRADA
    // ==========================================

    private String leerTextoSoloLetras(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine().trim();
            if (entrada.isEmpty()) {
                System.out.println("❌ El campo no puede estar vacío. Intente de nuevo.");
                continue;
            }
            if (!entrada.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
                System.out.println("❌ Dato inválido. Este campo solo permite letras y espacios.");
                continue;
            }
            return entrada;
        }
    }

    private String leerTextoSoloNumeros(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine().trim();
            if (entrada.isEmpty()) {
                System.out.println("❌ El campo no puede estar vacío. Intente de nuevo.");
                continue;
            }
            if (!entrada.matches("^\\d+$")) {
                System.out.println("❌ Dato inválido. Este campo solo permite números (sin letras ni espacios).");
                continue;
            }
            return entrada;
        }
    }

    private String leerTextoLibre(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine().trim();
            if (entrada.isEmpty()) {
                System.out.println("❌ El campo no puede estar vacío. Intente de nuevo.");
                continue;
            }
            return entrada;
        }
    }

    private Double leerDoublePositivo(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine().trim().replace(",", ".");
            try {
                double valor = Double.parseDouble(entrada);
                if (valor <= 0) {
                    System.out.println("❌ El valor debe ser un monto mayor a cero.");
                    continue;
                }
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("❌ Entrada inválida. Ingrese un número decimal válido (ej: 1500.50).");
            }
        }
    }

    private Long leerLong(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine().trim();
            try {
                return Long.parseLong(entrada);
            } catch (NumberFormatException e) {
                System.out.println("❌ Entrada inválida. Debe ingresar un número entero.");
            }
        }
    }

    private LocalDate leerFecha(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine().trim();
            try {
                return LocalDate.parse(entrada); 
            } catch (DateTimeParseException e) {
                System.out.println("❌ Formato de fecha incorrecto. Use el formato AAAA-MM-DD (ej: 2026-05-15).");
            }
        }
    }

    private LocalTime leerHora(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine().trim();
            try {
                return LocalTime.parse(entrada); 
            } catch (DateTimeParseException e) {
                System.out.println("❌ Formato de hora incorrecto. Use el formato HH:MM (ej: 14:30).");
            }
        }
    }

    // ==========================================
    // MENÚS Y FLUJOS PRINCIPALES
    // ==========================================

    public void iniciar() {
        int opcion = -1;
        do {
            System.out.println("\n=== CLINICA ODONTOLOGICA: SONRISA FELIZ ===");
            System.out.println("1. Gestión de Pacientes");
            System.out.println("2. Gestión de Odontólogos");
            System.out.println("3. Gestión de Turnos");
            System.out.println("0. Salir");
            
            opcion = leerLong("Seleccione una opción: ").intValue();

            switch (opcion) {
                case 1 -> menuPacientes();
                case 2 -> menuOdontologos();
                case 3 -> menuTurnos();
                case 0 -> System.out.println("\nGracias por utilizar el sistema. ¡Hasta pronto!");
                default -> System.out.println("❌ Opción no válida.");
            }
        } while (opcion != 0);
    }

    private void menuPacientes() {
        int opcion = -1;
        do {
            System.out.println("\n--- GESTIÓN DE PACIENTES ---");
            System.out.println("1. Registrar Paciente");
            System.out.println("2. Listar Todos");
            System.out.println("0. Volver al inicio");
            
            opcion = leerLong("Seleccione: ").intValue();

            switch (opcion) {
                case 1 -> registrarPaciente();
                case 2 -> listarPacientes();
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("❌ Opción no válida.");
            }
        } while (opcion != 0);
    }

    private void registrarPaciente() {
        System.out.println("\n--- Datos Personales del Paciente ---");
        String nom = leerTextoSoloLetras("Nombre: ");
        String ape = leerTextoSoloLetras("Apellido: ");
        String dni = leerTextoSoloNumeros("DNI (solo números): ");
        String email = leerTextoLibre("Email: ");
        String tel = leerTextoSoloNumeros("Teléfono: ");

        System.out.println("--- Datos del Domicilio ---");
        String calle = leerTextoLibre("Calle: ");
        String altura = leerTextoSoloNumeros("Altura/Número: ");
        String localidad = leerTextoSoloLetras("Localidad: ");
        String provincia = leerTextoSoloLetras("Provincia: ");

        Domicilio dom = new Domicilio();
        dom.setCalle(calle);
        dom.setAltura(altura);
        dom.setLocalidad(localidad);
        dom.setProvincia(provincia);

        Paciente p = null;
        int tipoPac = -1;
        while (p == null) {
            System.out.println("--- COBERTURA MÉDICA ---");
            System.out.println("1. Con Obra Social");
            System.out.println("2. Particular");
            tipoPac = leerLong("Seleccione una opción: ").intValue();
            
            if (tipoPac == 1) {
                PacienteObraSocial pOS = new PacienteObraSocial();
                pOS.setNombreObraSocial(leerTextoSoloLetras("Nombre de la Obra Social: "));
                pOS.setNumAfiliado(leerTextoSoloNumeros("Número de Afiliado: "));
                p = pOS; 
            } else if (tipoPac == 2) {
                PacienteParticular pP = new PacienteParticular();
                pP.setTarifaBase(leerDoublePositivo("Tarifa Base del Paciente: "));
                p = pP; 
            } else {
                System.out.println("❌ Opción de cobertura incorrecta. Elija 1 o 2.");
            }
        }

        // Seteo de atributos heredados en la clase abstracta
        p.setNombre(nom);
        p.setApellido(ape);
        p.setDni(dni);
        p.setEmail(email);    
        p.setTelefono(tel);   
        p.setFechaIngreso(LocalDate.now()); 
        p.setDomicilio(dom);

        try {
            Paciente registrado = controller.registrarPaciente(p);
            if (registrado != null) {
                System.out.println("✅ Paciente registrado con éxito. ID Asignado: " + registrado.getId());
            } else {
                System.out.println("❌ No se pudo registrar el paciente debido a una falla en las validaciones básicas.");
            }
        } catch (ClinicaException e) {
            System.out.println("\n❌ Error de negocio [" + e.getCodigoError() + "]: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\n❌ Error inesperado al registrar: " + e.getMessage());
        }
    }

    private void listarPacientes() {
        System.out.println("\n--- LISTADO DE PACIENTES ---");
        try {
            List<Paciente> pacientes = controller.listarPacientes();
            if (pacientes.isEmpty()) {
                System.out.println("No hay pacientes registrados.");
            } else {
                pacientes.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("❌ Error al recuperar la lista de pacientes: " + e.getMessage());
        }
    }

    // ==========================================
    // GESTIÓN DE ODONTÓLOGOS (CONSTRUCTORES CORREGIDOS)
    // ==========================================

    private void menuOdontologos() {
        int opcion = -1;
        do {
            System.out.println("\n--- GESTIÓN DE ODONTÓLOGOS ---");
            System.out.println("1. Registrar Odontólogo");
            System.out.println("2. Listar Todos");
            System.out.println("0. Volver");
            
            opcion = leerLong("Seleccione: ").intValue();

            switch (opcion) {
                case 1 -> registrarOdontologo();
                case 2 -> listarOdontologos();
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("❌ Opción no válida.");
            }
        } while (opcion != 0);
    }

    private void registrarOdontologo() {
        System.out.println("\n--- Registrar Nuevo Odontólogo ---");
        String nom = leerTextoSoloLetras("Nombre: ");
        String ape = leerTextoSoloLetras("Apellido: ");
        String dni = leerTextoSoloNumeros("DNI (solo números): ");
        String mat = leerTextoSoloNumeros("Matrícula (solo números): ");

        Odontologo o = null;
        int espOp = -1;
        
        while (o == null) {
            System.out.println("--- SELECCIÓN DE ESPECIALIDAD ---");
            System.out.println("1. ORTODONCIA");
            System.out.println("2. ENDODONCIA");
            System.out.println("3. EXTRACCIONES");
            espOp = leerLong("Opción: ").intValue();

            if (espOp >= 1 && espOp <= 3) {
                Double recargo = leerDoublePositivo("Ingrese el recargo de especialidad (ej: 1.5): ");
                
                // Corrección: Usamos instanciación limpia y asignación de recargo vía setters
                // Evitamos llamar a constructores parametrizados erróneos o incompletos
                switch (espOp) {
                    case 1 -> {
                        OdOrtodoncia od = new OdOrtodoncia();
                        od.setRecargoEspecialidad(recargo);
                        o = od;
                    }
                    case 2 -> {
                        OdEndodoncia od = new OdEndodoncia();
                        od.setRecargoEspecialidad(recargo);
                        o = od;
                    }
                    case 3 -> {
                        OdExtraccion od = new OdExtraccion();
                        od.setRecargoEspecialidad(recargo);
                        o = od;
                    }
                }
            } else {
                System.out.println("❌ Opción inválida. Seleccione una especialidad del 1 al 3.");
            }
        }

        // Seteamos los datos comunes heredados en la entidad abstracta madre
        o.setNombre(nom);
        o.setApellido(ape);
        o.setDni(dni);
        o.setMatricula(mat);

        try {
            Odontologo registrado = controller.registrarOdontologo(o);
            if (registrado != null) {
                System.out.println("✅ Odontólogo registrado con éxito. ID Asignado: " + registrado.getId());
            } else {
                System.out.println("❌ No se pudo guardar el odontólogo por infracciones en reglas de validación.");
            }
        } catch (ClinicaException e) {
            System.out.println("\n❌ Error de negocio [" + e.getCodigoError() + "]: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\n❌ Error inesperado: " + e.getMessage());
        }
    }

    private void listarOdontologos() {
        System.out.println("\n--- LISTADO DE ODONTÓLOGOS ---");
        try {
            List<Odontologo> odontologos = controller.listarOdontologos();
            if (odontologos.isEmpty()) {
                System.out.println("No hay odontólogos registrados.");
            } else {
                odontologos.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("❌ Error al recuperar la lista de odontólogos: " + e.getMessage());
        }
    }

    // ==========================================
    // GESTIÓN DE TURNOS (MÉTODOS EXACTOS DEL CONTROLADOR)
    // ==========================================

    private void menuTurnos() {
        int opcion = -1;
        do {
            System.out.println("\n--- GESTIÓN DE TURNOS ---");
            System.out.println("1. Reservar Nuevo Turno");
            System.out.println("2. Listar Todos los Turnos");
            System.out.println("0. Volver al Inicio");
            
            opcion = leerLong("Seleccione una opción: ").intValue();

            switch (opcion) {
                case 1 -> agendarTurno();
                case 2 -> listarTurnos();
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("❌ Opción no válida.");
            }
        } while (opcion != 0);
    }

    private void agendarTurno() {
        System.out.println("\n--- Reservar Nuevo Turno ---");
        
        // Uso exacto de métodos del controlador: buscarPacienteId y buscarOdontologoId
        Long idPac = leerLong("Ingrese ID del Paciente: ");
        var pacienteOpt = controller.buscarPacienteId(idPac);

        if (pacienteOpt.isEmpty()) {
            System.out.println("❌ Error: El paciente con ID " + idPac + " no existe. Operación cancelada.");
            return;
        }

        Long idOdonto = leerLong("Ingrese ID del Odontólogo: ");
        var odontoOpt = controller.buscarOdontologoId(idOdonto);

        if (odontoOpt.isEmpty()) {
            System.out.println("❌ Error: El odontólogo con ID " + idOdonto + " no existe. Operación cancelada.");
            return;
        }

        // Lectura de parámetros temporales aislada y segura
        LocalDate fecha = leerFecha("Ingrese la fecha (Año-Mes-Día, ej: 2026-05-15): ");
        LocalTime hora = leerHora("Ingrese la hora (Hora:Minutos, ej: 14:30): ");

        try {
            Turno nuevoTurno = new Turno();
            nuevoTurno.setPaciente(pacienteOpt.get());
            nuevoTurno.setOdontologo(odontoOpt.get());
            nuevoTurno.setFecha(fecha);
            nuevoTurno.setHora(hora);

            Turno registrado = controller.agendarTurno(nuevoTurno);

            if (registrado != null) {
                System.out.println("\n=============================================");
                System.out.println("✅ ¡Turno agendado con éxito! ID Asignado: " + registrado.getId());
                System.out.println("Monto calculado de facturación: $" + registrado.getMontoFacturacion());
                System.out.println("=============================================");
            } else {
                System.out.println("❌ No se pudo agendar el turno. Revise las reglas de negocio en consola.");
            }
        } catch (ClinicaException e) {
            System.out.println("\n❌ Error de Agenda [" + e.getCodigoError() + "]: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Ocurrió un error inesperado al procesar el turno en el sistema.");
        }
    }

    private void listarTurnos() {
        System.out.println("\n--- LISTADO DE TURNOS ---");
        try {
            var turnos = controller.listarTurnos();
            if (turnos.isEmpty()) {
                System.out.println("No hay turnos registrados.");
            } else {
                turnos.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("❌ Error al recuperar la agenda: " + e.getMessage());
        }
    }
}