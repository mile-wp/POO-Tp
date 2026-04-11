package modelo;

import Interfaces.IDomicilio;

public class Domicilio implements IDomicilio {
    private Long id;
    private String calle;
    private String numero;
    private String localidad;
    private String provincia;

    //Creamos constructor

    public Domicilio(Long id, String calle, String numero, String localidad, String provincia) {
        this.id = id;
        this.calle = calle;
        this.numero = numero;
        this.localidad = localidad;
        this.provincia = provincia;
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
    public String getCalle() {
        return "";
    }

    @Override
    public void setCalle(String calle) {

    }

    @Override
    public String getNumero() {
        return "";
    }

    @Override
    public void setNumero(String numero) {

    }

    @Override
    public String getLocalidad() {
        return "";
    }

    @Override
    public void setLocalidad(String localidad) {

    }

    @Override
    public String getProvincia() {
        return "";
    }

    @Override
    public void setProvincia(String provincia) {

    }
}
