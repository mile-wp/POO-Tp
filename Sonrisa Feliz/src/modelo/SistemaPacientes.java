package modelo;

import java.util.ArrayList;
import java.util.List;

public class SistemaPacientes {

    private List<Paciente> pacientes = new ArrayList<>();

    // Alta de paciente
    public void registrar(Paciente paciente) {
        pacientes.add(paciente);
    }

    // Búsqueda por ID
    public Paciente buscarPorId(Long id) {
        for (int i = 0; i < pacientes.size(); i++) {
            Paciente p = pacientes.get(i);
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    // Búsqueda por DNI
    public Paciente buscarPorDni(String dni) {
        for (int i = 0; i < pacientes.size(); i++) {
            Paciente p = pacientes.get(i);
            if (p.getDni().equals(dni)) {
                return p;
            }
        }
        return null;
    }

    // Listado de todos los pacientes
    public List<Paciente> listarTodos() {
        return new ArrayList<>(pacientes);
    }

    // Modificación de datos
    public void modificar(Long id, Paciente nuevosDatos) {
        Paciente p = buscarPorId(id);
        if (p != null) {
            p.setNombre(nuevosDatos.getNombre());
            p.setApellido(nuevosDatos.getApellido());
            p.setDni(nuevosDatos.getDni());
            p.setEmail(nuevosDatos.getEmail());
            p.setDomicilio(nuevosDatos.getDomicilio());
        }
    }

    // Eliminación sin verificacion de turno
    public void eliminar(Long id) {
        Paciente p = buscarPorId(id); // Primera búsqueda
        if (p != null) {
            pacientes.remove(p); // Segunda búsqueda interna para borrar
        }
    }
}