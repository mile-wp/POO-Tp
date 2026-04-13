package modelo;

import Interfaces.IPaciente;

import java.time.LocalDate;

public class Paciente implements IPaciente {
    private Long id;
    private String nombre;
    private String apellido;
    private String dni;
    private String email;
    private LocalDate fechaIngreso;
    private Domicilio domicilio;

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


    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String getApellido() {
        return apellido;
    }

    @Override
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    @Override
    public String getDni() {
        return dni;
    }

    @Override
    public void setDni(String dni) {
        this.dni = dni;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    @Override
    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    @Override
    public Domicilio getDomicilio() {
        return domicilio;
    }

    @Override
    public void setDomicilio(Domicilio domicilio) {
        this.domicilio = domicilio;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | " + apellido + ", " + nombre + " | DNI: " + dni;
    }
}
