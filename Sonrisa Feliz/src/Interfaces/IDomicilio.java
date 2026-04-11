package Interfaces;

public interface IDomicilio {
    Long getId();
    void setId(Long id);

    String getCalle();
    void setCalle(String calle);

    String getNumero();
    void setNumero(String numero);

    String getLocalidad();
    void setLocalidad(String localidad);

    String getProvincia();
    void setProvincia(String provincia);
}