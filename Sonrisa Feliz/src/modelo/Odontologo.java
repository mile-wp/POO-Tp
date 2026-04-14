package modelo;

import org.w3c.dom.ls.LSOutput;

import java.util.HashSet;
import java.util.Set;

public class Odontologo {
    private Long id;
    private String nombre;
    private String apellido;
    private String matricula;
    private Set<String> especialidades;

    public Odontologo(Long id, String nombre, String apellido, String matricula, Set<String> especialidades) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.matricula = matricula;
        this.especialidades = new HashSet<>(especialidades);
    }

    public Odontologo(Long id, String nombre, String apellido, String matricula) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.matricula = matricula;
    }

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

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public void agregarEspecialidad(String especialidad){
        especialidades.add(especialidad);
    }

    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "Odontologo | Id: "+id + "| Nombre y apellido: " +nombre + apellido +"| matricula: "+matricula + "| especialidad: "+especialidades;
    }
}
