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
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }


    @Override
    public String getCalle() {
        return calle;
    }

    @Override
    public void setCalle(String calle) {
        this.calle = calle;
    }

    @Override
    public String getNumero() {
        return numero;
    }

    @Override
    public void setNumero(String numero) {
        this.numero = numero;

    }

    @Override
    public String getLocalidad() {
        return localidad;
    }

    @Override
    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    @Override
    public String getProvincia() {
        return provincia;
    }

    @Override
    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    @Override
    public String toString() {
        return "Domicilio ID: " + id + ", CALLE: " + calle + ", NUMERO: " + numero + ", LOCALIDAD: " + localidad + ", PROVINCIA: " + provincia + "]";
        
    }
}
