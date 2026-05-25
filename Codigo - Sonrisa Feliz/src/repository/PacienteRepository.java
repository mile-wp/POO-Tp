package repository;

import entity.Paciente;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PacienteRepository implements IRepository<Paciente> {
    // Implementación usando HashMap como pide el documento
    private Map<Long, Paciente> tablaPacientes = new HashMap<>();
    private Long generadorId = 1L;

    @Override
    public Paciente guardar(Paciente paciente) {
        paciente.setId(generadorId);
        tablaPacientes.put(generadorId, paciente); // El ID es la clave
        generadorId++;
        return paciente;
    }

    @Override
    public Optional<Paciente> buscarPorId(Long id) {
        // La búsqueda en HashMap es directa y eficiente
        return Optional.ofNullable(tablaPacientes.get(id));
    }

    @Override
    public List<Paciente> buscarTodos() {
        // Convertimos los valores del mapa a una lista para la UI
        return new ArrayList<>(tablaPacientes.values());
    }

    @Override
    public void eliminar(Long id) {
        tablaPacientes.remove(id);
    }

    @Override
    public Paciente actualizar(Paciente pacienteModificado) {
        if (tablaPacientes.containsKey(pacienteModificado.getId())) {
            tablaPacientes.put(pacienteModificado.getId(), pacienteModificado);
            return pacienteModificado;
        }
        return null;
    }
}