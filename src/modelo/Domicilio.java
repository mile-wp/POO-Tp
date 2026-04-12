package modelo;

public class Domicilio {
    private Long id;
    private String calle;
    private String numero;
    private String localidad;
    private String provincia;

    public Domicilio(Long id, String calle, String numero, String localidad, String provincia) {
        this.id = id;
        this.calle = calle;
        this.numero = numero;
        this.localidad = localidad;
        this.provincia = provincia;
    }

}
