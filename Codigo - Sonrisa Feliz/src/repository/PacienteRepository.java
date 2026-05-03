package repository;

import entity.Paciente;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PacienteRepository implements IRepository<Paciente> {

    // 1. Base de datos en memoria para pacientes
    private List<Paciente> tablaPacientes;

    // 2. Generador de IDs
    private Long generadorId;

    public PacienteRepository() {
        this.tablaPacientes = new ArrayList<>();
        this.generadorId = 1L;
    }

    @Override
    public Paciente guardar(Paciente paciente) {
        paciente.setId(generadorId);
        generadorId++;
        tablaPacientes.add(paciente);
        return paciente;
    }

    @Override
    public Optional<Paciente> buscarPorId(Long id) {
        for (Paciente paciente : tablaPacientes) {
            if (paciente.getId().equals(id)) {
                return Optional.of(paciente);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Paciente> buscarTodos() {
        // Retornamos una copia para proteger la lista original
        return new ArrayList<>(tablaPacientes);
    }

    @Override
    public void eliminar(Long id) {
        tablaPacientes.removeIf(paciente -> paciente.getId().equals(id));
    }

    @Override
    public Paciente actualizar(Paciente pacienteModificado) {
        for (int i = 0; i < tablaPacientes.size(); i++) {
            if (tablaPacientes.get(i).getId().equals(pacienteModificado.getId())) {
                tablaPacientes.set(i, pacienteModificado);
                return pacienteModificado;
            }
        }
        return null;
    }
}