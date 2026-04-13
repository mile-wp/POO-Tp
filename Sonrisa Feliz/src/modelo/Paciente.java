package modelo;

import interfaces.IPaciente;

import java.time.LocalDate;

public class Paciente implements IPaciente {
    private Long id;
    private String nombre;
    private String apellido;
    private String dni;
    private String email;
    private LocalDate fechaIngreso;
    private Domicilio domicilio;

    public Paciente() {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.email = email;
        this.fechaIngreso = fechaIngreso;
        this.domicilio = domicilio;
    }

    //Agregamos constructor

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
        return 0L;
    }

    @Override
    public void setId(Long id) {

    }

    @Override
    public String getNombre() {
        return "";
    }

    @Override
    public void setNombre(String nombre) {

    }

    @Override
    public String getApellido() {
        return "";
    }

    @Override
    public void setApellido(String apellido) {

    }

    @Override
    public String getDni() {
        return "";
    }

    @Override
    public void setDni(String dni) {

    }

    @Override
    public String getEmail() {
        return "";
    }

    @Override
    public void setEmail(String email) {

    }

    @Override
    public LocalDate getFechaIngreso() {
        return null;
    }

    @Override
    public void setFechaIngreso(LocalDate fechaIngreso) {

    }

    @Override
    public Domicilio getDomicilio() {
        return null;
    }

    @Override
    public void setDomicilio(Domicilio domicilio) {

    }
}
