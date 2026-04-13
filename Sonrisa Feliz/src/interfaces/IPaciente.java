package interfaces;

import modelo.Domicilio;
import java.time.LocalDate;

public interface IPaciente {
    Long getId();
    void setId(Long id);

    String getNombre();
    void setNombre(String nombre);

    String getApellido();
    void setApellido(String apellido);

    String getDni();
    void setDni(String dni);

    String getEmail();
    void setEmail(String email);

    LocalDate getFechaIngreso();
    void setFechaIngreso(LocalDate fechaIngreso);

    Domicilio getDomicilio();
    void setDomicilio(Domicilio domicilio);
}