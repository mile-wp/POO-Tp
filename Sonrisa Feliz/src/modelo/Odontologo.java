package modelo;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Odontologo {
    private Long id;
    private String nombre;
    private String apellido;
    private String matricula;
    private Set<String> especialidades; // Agregamos el Set como en el UML

    // Constructor (Sin el ID, como acordamos en el UML)
    public Odontologo(String nombre, String apellido, String matricula) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.matricula = matricula;
        this.especialidades = new HashSet<>(); // Inicializamos el Set vacío
    }

    // Método de negocio (UML)
    public void agregarEspecialidad(String especialidad) {
        this.especialidades.add(especialidad);
    }

    // Método de negocio (UML)
    public String getNombreCompleto() {
        return this.apellido + ", " + this.nombre;
    }

    // Getters y Setters respetando camelCase
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

    public Set<String> getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(Set<String> especialidades) {
        this.especialidades = especialidades;
    }

    // Sobrescritura de equals y hashCode usando LA MATRÍCULA como identidad única
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Odontologo that = (Odontologo) o;
        return Objects.equals(matricula, that.matricula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matricula);
    }

    // Sobrescritura de toString para imprimir el objeto fácilmente
    @Override
    public String toString() {
        return "Odontologo{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", matricula='" + matricula + '\'' +
                ", especialidades=" + especialidades +
                '}';
    }
}
