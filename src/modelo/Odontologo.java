package modelo;

public class Odontologo {
    private Long id;
    private String nombre;
    private String apellido;
    private String matricula;

    public Odontologo(Long id, String nombre, String apellido, String matricula) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.matricula = matricula;
    }
}
