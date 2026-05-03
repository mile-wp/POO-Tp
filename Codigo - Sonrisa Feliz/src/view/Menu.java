package view;

import controller.ClinicaController;
import entity.*;
import java.util.Scanner;

//Agregamos scanner para ingresar datos desde teclado
public class Menu {
    private final ClinicaController controller = new ClinicaController();
    private final Scanner scanner = new Scanner(System.in);

    //método que inicializa el menú
    public void iniciar() {
        System.out.println("\n=== CLINICA SONRISA FELIZ ===");
        System.out.println("1. Gestionar Pacientes");
        System.out.println("2. Gestionar Odontólogos");
        System.out.println("3. Gestión de Turnos");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");

        int opcion = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

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


    //Hacemos un menú por cada clase para no mezclar los datos

    //Menú para pacientes

    private void menuPacientes() {
        System.out.println("\n--- GESTIÓN DE PACIENTES ---");
        System.out.println("1. Registrar Paciente");
        System.out.println("2. Listar Todos");
        System.out.println("3. Volver al inicio");
        System.out.print("Seleccione: ");

        int op = scanner.nextInt();
        scanner.nextLine();

        //Registro un paciente
        if (op == 1) {
            System.out.print("Nombre: "); String nom = scanner.nextLine();
            System.out.print("Apellido: "); String ape = scanner.nextLine();
            System.out.print("DNI: "); String dni = scanner.nextLine();
            System.out.println("Email: "); String email = scanner.nextLine();
            System.out.println("Teléfono: "); String tel = scanner.nextLine();


            //Pide datos de domicilio

            System.out.println("--- DATOS DEL DOMICILIO ---");
            System.out.print("Calle: "); String calle = scanner.nextLine();
            System.out.print("Altura/Número: "); String altura = scanner.nextLine();
            System.out.print("Localidad: "); String localidad = scanner.nextLine();
            System.out.print("Provincia: "); String provincia = scanner.nextLine();

            // 1. Crear el objeto Domicilio primero
            Domicilio dom = new Domicilio(calle, altura, localidad, provincia);

            // 2. Crear el Paciente y le asigna los datos
            Paciente p = new Paciente();
            p.setNombre(nom);
            p.setApellido(ape);
            p.setDni(dni);

            //CHEQUEAR POR QUÉ DA ERROR
            //p.setEmail(email);
            //p.setTelefono(tele);

            p.setDomicilio(dom);


            controller.registrarPaciente(p);
            menuPacientes();



            //Lista todos los pacientes
        } else if (op == 2) {
            controller.listarPacientes().forEach(System.out::println);
            menuPacientes();

            //Vuelve al inicio
        } else {
            iniciar(); // Regresa al menú raíz
        }
    }

    //Menú para odontólogos

    private void menuOdontologos() {
        System.out.println("\n--- GESTIÓN DE ODONTÓLOGOS ---");
        System.out.println("1. Registrar Odontólogo");
        System.out.println("2. Listar Todos");
        System.out.println("3. Volver");
        System.out.print("Seleccione: ");

        int op = scanner.nextInt();
        scanner.nextLine();

        if (op == 1) {
            System.out.print("Nombre: "); String nom = scanner.nextLine();
            System.out.print("Apellido: "); String ape = scanner.nextLine();
            System.out.print("Matrícula: "); String mat = scanner.nextLine();

            // --- SELECCIÓN DE ESPECIALIDAD ---
            System.out.println("Seleccione Especialidad:");
            System.out.println("1. ORTODONCIA");
            System.out.println("2. ENDODONCIA");
            System.out.println("3. IMPLANTOLOGIA");
            System.out.println("4. EXTRACCIONES");
            System.out.print("Opción: ");
            int espOp = scanner.nextInt();
            scanner.nextLine();

            Especialidad especialidadSeleccionada = switch (espOp) {
                case 1 -> Especialidad.ORTODONCIA;
                case 2 -> Especialidad.ENDODONCIA;
                case 3 -> Especialidad.IMPLANTOLOGIA;
                case 4 -> Especialidad.EXTRACCIONES;
                default -> {
                    System.out.println("Opción inválida, se asignará ORTODONCIA por defecto.");
                    //Yield lo usamos porque al ser un enum retorna más de un posible valor
                    yield Especialidad.ORTODONCIA;
                }
            };

            // Crear objeto y asignar datos
            Odontologo o = new Odontologo();
            o.setNombre(nom);
            o.setApellido(ape);
            o.setMatricula(mat);
            o.setEspecialidad(especialidadSeleccionada); // Asignación de la especialidad

            controller.registrarOdontologo(o);
            System.out.println("Odontólogo registrado con éxito.");
            menuOdontologos();
        } else if (op == 2) {
            controller.listarOdontologos().forEach(System.out::println);
            menuOdontologos();
        } else {
            iniciar();
        }
    }


    //Menú para turnos
    private void menuTurnos() {
        System.out.println("\n--- GESTIÓN DE TURNOS ---");
        System.out.println("1. Reservar Nuevo Turno");
        System.out.println("2. Listar Todos los Turnos");
        System.out.println("3. Volver al Inicio");
        System.out.print("Seleccione: ");

        int op = scanner.nextInt();
        scanner.nextLine();

        if (op == 1) {
            // 1. Selección de Paciente
            System.out.print("Ingrese ID del Paciente: ");
            Long idPac = scanner.nextLong();
            var pacienteOpt = controller.buscarPacienteId(idPac);

            if (pacienteOpt.isEmpty()) {
                System.out.println("Error: El paciente con ID " + idPac + " no existe.");
                menuTurnos();
                return;
            }

            // 2. Selección de Odontólogo
            System.out.print("Ingrese ID del Odontólogo: ");
            Long idOdonto = scanner.nextLong();
            var odontoOpt = controller.buscarOdontologoId(idOdonto);

            if (odontoOpt.isEmpty()) {
                System.out.println("Error: El odontólogo con ID " + idOdonto + " no existe.");
                menuTurnos();
                return;
            }

            // 3. Ingreso de Fecha y Hora
            System.out.println("Ingrese la fecha (Año-Mes-Día, ej: 2026-05-15): ");
            String fechaStr = scanner.next();
            System.out.println("Ingrese la hora (Hora:Minutos, ej: 14:30): ");
            String horaStr = scanner.next();

            try {
                Turno nuevoTurno = new Turno();
                nuevoTurno.setPaciente(pacienteOpt.get());
                nuevoTurno.setOdontologo(odontoOpt.get());
                nuevoTurno.setFecha(java.time.LocalDate.parse(fechaStr));
                nuevoTurno.setHora(java.time.LocalTime.parse(horaStr));
                nuevoTurno.setEstado(EstadoTurno.PENDIENTE); // Estado inicial

                // 4. Intento de registro a través del Controller
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