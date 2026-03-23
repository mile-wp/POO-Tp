package modelo;

import java.time.LocalDate;

public class Paciente {
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

    public void mostrarPaciente() {
        System.out.println("Id: "+ id);
        System.out.println("Nombre completo: "+ nombre +apellido);
        System.out.println("Documento de identificación: "+ dni);
        System.out.println("Correo electrónico: "+ email);
        System.out.println("Fecha de ingreso: "+ fechaIngreso);
        System.out.println("Domicilio: "+ domicilio);;
    }
}
