package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Paciente {
    private Long id;
    private String nombre;
    private String apellido;
    private String dni;
    private String email;
    private LocalDate fechaIngreso;
    private Domicilio domicilio;

    //Lista de pacientes
    private static List<Paciente> pacientes = new ArrayList<>();

    //Constructor
    public Paciente(Long id, String nombre, String apellido, String dni, String email, LocalDate fechaIngreso, Domicilio domicilio) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.email = email;
        this.fechaIngreso = fechaIngreso;
        this.domicilio = domicilio;
    }


    public void mostrarPaciente() {
        System.out.println("Id: "+ id);
        System.out.println("Nombre completo: "+ nombre +apellido);
        System.out.println("Documento de identificación: "+ dni);
        System.out.println("Correo electrónico: "+ email);
        System.out.println("Fecha de ingreso: "+ fechaIngreso);
        System.out.println("Domicilio: "+ domicilio);;
    }


    //Agregamos getters y setters
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombreCompleto() {
        return this.apellido + ", " + this.nombre;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Domicilio getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Domicilio domicilio) {
        this.domicilio = domicilio;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Paciente paciente = (Paciente) o;
        return Objects.equals(dni, paciente.dni);
    }

    public String toString() {
        return "ID: " + id + " | " + apellido + ", " + nombre + " | DNI: " + dni +  " | Email: " + email;
    }

    // Alta de paciente
    public static void registrar(Paciente paciente) {
        pacientes.add(paciente);
    }

    // Búsqueda por ID
    public static Paciente buscarPorId(Long id) {
        for (int i = 0; i < pacientes.size(); i++) {
            Paciente p = pacientes.get(i);
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    // Búsqueda por DNI
    public static Paciente buscarPorDni(String dni) {
        for (int i = 0; i < pacientes.size(); i++) {
            Paciente p = pacientes.get(i);
            if (p.getDni().equals(dni)) {
                return p;
            }
        }
        return null;
    }

    // Listado de todos los pacientes
    public static List<Paciente> listarTodos() {
        return new ArrayList<>(pacientes);
    }

    // Modificación de datos
    public void modificar(Long id, Paciente nuevosDatos) {
        Paciente p = buscarPorId(id);
        if (p != null) {
            p.setNombre(nuevosDatos.getNombre());
            p.setApellido(nuevosDatos.getApellido());
            p.setDni(nuevosDatos.getDni());
            p.setEmail(nuevosDatos.getEmail());
            p.setDomicilio(nuevosDatos.getDomicilio());
        }
    }

    // Eliminación sin verificacion de turno
    public static void eliminar(Long id) {
        Paciente p = buscarPorId(id); // Primera búsqueda
        if (p != null) {
            pacientes.remove(p); // Segunda búsqueda interna para borrar
        }
    }

}
