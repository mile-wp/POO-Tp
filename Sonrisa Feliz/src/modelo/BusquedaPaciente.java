package modelo;

import java.util.List;

public class BusquedaPaciente {

    //Busca un paciente por su ID dentro de una lista

    public static Paciente buscarPorId(List<Paciente> pacientes, Long id) {
        for (Paciente paciente : pacientes) {
            if (paciente.getId().equals(id)) {
                return paciente;
            }
        }
        return null;
    }
}