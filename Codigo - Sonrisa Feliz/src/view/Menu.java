package view;

import controller.ClinicaController;
import entity.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

// Agregamos scanner para ingresar datos desde teclado
public class Menu {
    private final ClinicaController controller = new ClinicaController();
    private final Scanner scanner = new Scanner(System.in);

    // método que inicializa el menú
    public void iniciar() {
        System.out.println("\n=== CLINICA SONRISA FELIZ ===");
        System.out.println("1. Gestión de Pacientes");
        System.out.println("2. Gestión de Odontólogos");
        System.out.println("3. Gestión de Turnos");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");

        int opcion;

        try {
            opcion = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Error: Debe ingresar un número válido.");
            iniciar();
            return;
        }

        procesarOpcionPrincipal(opcion);
    }

    private void procesarOpcionPrincipal(int opcion) {
        if (opcion == 0) {
            System.out.println("Cerrando el sistema...");
            return; // Finaliza la ejecución
        }

        switch (opcion) {
            case 1 -> menuPacientes();
            case 2 -> menuOdontologos();
            case 3 -> menuTurnos();
            default -> {
                System.out.println("Opción no válida.");
                iniciar();
            }
        }
    }

    // --- MENÚ PARA PACIENTES ---
    private void menuPacientes() {
        System.out.println("\n--- GESTIÓN DE PACIENTES ---");
        System.out.println("1. Registrar Paciente");
        System.out.println("2. Listar Todos");
        System.out.println("3. Volver al inicio");
        System.out.print("Seleccione: ");

        int op;

        try {
            op = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Error: Debe ingresar un número.");
            menuPacientes();
            return;
        }

        if (op == 1) {
            System.out.print("Nombre: "); String nom = scanner.nextLine();
            System.out.print("Apellido: "); String ape = scanner.nextLine();
            System.out.print("DNI: "); String dni = scanner.nextLine();
            System.out.print("Email: "); String email = scanner.nextLine();
            System.out.print("Teléfono: "); String tel = scanner.nextLine();

            // Pide datos de domicilio
            System.out.println("--- DATOS DEL DOMICILIO ---");
            System.out.print("Calle: "); String calle = scanner.nextLine();
            System.out.print("Altura/Número: "); String altura = scanner.nextLine();
            System.out.print("Localidad: "); String localidad = scanner.nextLine();
            System.out.print("Provincia: "); String provincia = scanner.nextLine();

            // 1. Crear el objeto Domicilio
            Domicilio dom = new Domicilio();
            dom.setCalle(calle);
            dom.setAltura(altura);
            dom.setLocalidad(localidad);
            dom.setProvincia(provincia);

            // 2. SELECCIÓN DE TIPO DE PACIENTE (El gran cambio)
            System.out.println("--- COBERTURA MÉDICA ---");
            System.out.println("1. Con Obra Social");
            System.out.println("2. Particular");
            System.out.print("Seleccione: ");

            int tipoPac;

            try {
                tipoPac = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar una opción válida.");
                menuPacientes();
                return;
            }

            // Declaramos la variable de la clase abstracta (Polimorfismo)
            Paciente p;

            if (tipoPac == 1) {
                System.out.print("Nombre de la Obra Social: "); String obs = scanner.nextLine();
                System.out.print("Número de Afiliado: "); String numAfi = scanner.nextLine();

                PacienteObraSocial pOS = new PacienteObraSocial();
                pOS.setNombreObraSocial(obs);
                pOS.setNumAfiliado(numAfi);
                p = pOS; // Asignamos el hijo a la variable padre
            } else {
                System.out.print("Tarifa Base del Paciente (ej: 5000.50): ");
                String inputTarifa = scanner.nextLine();
                double tarifa = Double.parseDouble(inputTarifa.replace(",", "."));

                PacienteParticular pP = new PacienteParticular();
                pP.setTarifaBase(tarifa);
                p = pP; // Asignamos el hijo a la variable padre
            }

            // 3. Setear los datos comunes de la clase abstracta Persona/Paciente
            p.setNombre(nom);
            p.setApellido(ape);
            p.setDni(dni);
            p.setEmail(email);    // Ya no debería dar error si está en la Entity
            p.setTelefono(tel);   // Ya no debería dar error si está en la Entity
            p.setFechaIngreso(LocalDate.now()); // Seteamos la fecha de hoy automáticamente
            p.setDomicilio(dom);

            try {
                controller.registrarPaciente(p);
                System.out.println("Paciente registrado con éxito.");
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
            menuPacientes();

        } else if (op == 2) {
            controller.listarPacientes().forEach(System.out::println);
            menuPacientes();
        } else {
            iniciar();
        }
    }

    // --- MENÚ PARA ODONTÓLOGOS ---
    private void menuOdontologos() {
        System.out.println("\n--- GESTIÓN DE ODONTÓLOGOS ---");
        System.out.println("1. Registrar Odontólogo");
        System.out.println("2. Listar Todos");
        System.out.println("3. Volver");
        System.out.print("Seleccione: ");

        int op;

        try {
            op = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Error: Debe ingresar un número válido.");
            iniciar();
            return;
        }

        if (op == 1) {
            System.out.print("Nombre: "); String nom = scanner.nextLine();
            System.out.print("Apellido: "); String ape = scanner.nextLine();
            System.out.print("DNI: "); String dni = scanner.nextLine();
            System.out.print("Matrícula: "); String mat = scanner.nextLine();

            System.out.println("--- SELECCIÓN DE ESPECIALIDAD ---");
            System.out.println("1. ORTODONCIA");
            System.out.println("2. ENDODONCIA");
            System.out.println("3. EXTRACCIONES");
            System.out.print("Opción: ");

            int espOp;

            try {
                espOp = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar una opción válida.");
                menuOdontologos();
                return;
            }

            System.out.print("Ingrese el recargo de especialidad (ej: 1.5): ");
            String inputRecargo = scanner.nextLine(); // Leemos como texto

            double recargo;

            try {
                recargo = Double.parseDouble(inputRecargo.replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.println("Error: El recargo debe ser numérico.");
                menuOdontologos();
                return;
            }

            // Declaramos la variable de la clase abstracta
            Odontologo o;

            // Instanciamos la clase concreta elegida
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
                default -> {
                    System.out.println("Opción inválida, se asignará ORTODONCIA por defecto.");
                    OdOrtodoncia od = new OdOrtodoncia();
                    od.setRecargoEspecialidad(recargo);
                    o = od;
                }
            }

            // Seteamos los datos comunes heredados
            o.setNombre(nom);
            o.setApellido(ape);
            o.setDni(dni);
            o.setMatricula(mat);

            try {
                controller.registrarOdontologo(o);
                System.out.println("Odontólogo registrado con éxito.");

            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
            menuOdontologos();

        } else if (op == 2) {
            controller.listarOdontologos().forEach(System.out::println);
            menuOdontologos();
        } else {
            iniciar();
        }
    }

    // --- MENÚ PARA TURNOS ---
    private void menuTurnos() {
        System.out.println("\n--- GESTIÓN DE TURNOS ---");
        System.out.println("1. Reservar Nuevo Turno");
        System.out.println("2. Listar Todos los Turnos");
        System.out.println("3. Volver al Inicio");
        System.out.print("Seleccione: ");

        int op;

        try {
            op = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Error: Debe ingresar un número.");
            menuOdontologos();
            return;
        }

        if (op == 1) {
            System.out.print("Ingrese ID del Paciente: ");

            Long idPac;

            try {
                idPac = Long.parseLong(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: El ID del paciente debe ser numérico.");
                menuTurnos();
                return;
            }

            var pacienteOpt = controller.buscarPacienteId(idPac);

            if (pacienteOpt.isEmpty()) {
                System.out.println("Error: El paciente con ID " + idPac + " no existe.");
                menuTurnos();
                return;
            }

            System.out.print("Ingrese ID del Odontólogo: ");

            Long idOdonto;

            try {
                idOdonto = Long.parseLong(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: El ID del odontólogo debe ser numérico.");
                menuTurnos();
                return;
            }

            var odontoOpt = controller.buscarOdontologoId(idOdonto);

            if (odontoOpt.isEmpty()) {
                System.out.println("Error: El odontólogo con ID " + idOdonto + " no existe.");
                menuTurnos();
                return;
            }

            System.out.print("Ingrese la fecha (Año-Mes-Día, ej: 2026-05-15): ");
            String fechaStr = scanner.next();
            System.out.print("Ingrese la hora (Hora:Minutos, ej: 14:30): ");
            String horaStr = scanner.next();

            try {
                Turno nuevoTurno = new Turno();
                nuevoTurno.setPaciente(pacienteOpt.get());
                nuevoTurno.setOdontologo(odontoOpt.get());
                nuevoTurno.setFecha(LocalDate.parse(fechaStr));
                nuevoTurno.setHora(LocalTime.parse(horaStr));

                // NOTA: Si aún no creaste el Enum EstadoTurno en la entidad, comentalo temporalmente.
                // nuevoTurno.setEstado(EstadoTurno.PENDIENTE);

                Turno registrado = controller.agendarTurno(nuevoTurno);

                if (registrado != null) {
                    System.out.println("¡Turno agendado con éxito! ID: " + registrado.getId());
                } else {
                    System.out.println("No se pudo agendar el turno. Revise las reglas de negocio (horarios o choques).");
                }
            } catch (Exception e) {
                System.out.println("Error en el formato de fecha u hora. Intente nuevamente.");
            }

            menuTurnos();

        } else if (op == 2) {
            System.out.println("\n--- LISTADO DE TURNOS ---");
            var turnos = controller.listarTurnos();
            if (turnos.isEmpty()) {
                System.out.println("No hay turnos registrados.");
            } else {
                turnos.forEach(System.out::println);
            }
            menuTurnos();
        } else {
            iniciar();
        }
    }
}