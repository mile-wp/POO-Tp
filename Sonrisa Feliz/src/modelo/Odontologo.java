package modelo;

import Interfaces.IOdontologo;

public class Odontologo implements IOdontologo {
    private Long id;
    private String nombre;
    private String apellido;
    private String matricula;

    //Creamos el constructor

    public Odontologo(Long id, String nombre, String apellido, String matricula) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.matricula = matricula;
    }

    //Creamos getters y setters

    @Override
    public Long getid() {
        return 0L;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getnombre() {
        return "";
    }

    @Override
    public void setnombre(String nombre) {

    }

    @Override
    public String getapellido() {
        return "";
    }

    @Override
    public void setapellido(String apellido) {

    }

    @Override
    public String getmatricula() {
        return "";
    }

    @Override
    public void setmatricula(String matricula) {

    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
}
