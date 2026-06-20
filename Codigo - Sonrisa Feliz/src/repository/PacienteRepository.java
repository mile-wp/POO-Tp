package repository;

import entity.Paciente;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PacienteRepository implements IRepository<Paciente> {

    private Map<Long, Paciente> tablaPacientes = new HashMap<>();
    private Long generadorId = 1L;
    private static final String FILE_NAME = "src/data/pacientes.dat";

    public PacienteRepository() {
        cargarDesdeArchivo();
    }

    // --- MÉTODOS DE PERSISTENCIA ---

    private void guardarEnArchivo() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(tablaPacientes);
            out.writeObject(generadorId);
        } catch (IOException e) {
            System.err.println("Error al guardar pacientes: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void cargarDesdeArchivo() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                tablaPacientes = (Map<Long, Paciente>) in.readObject();
                generadorId = (Long) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error al cargar pacientes: " + e.getMessage());
            }
        }
    }

    // --- MÉTODOS DE NEGOCIO ---

    @Override
    public Paciente guardar(Paciente paciente) {
        paciente.setId(generadorId);
        tablaPacientes.put(generadorId, paciente);
        generadorId++;
        guardarEnArchivo(); // Persistencia automática
        return paciente;
    }

    @Override
    public Optional<Paciente> buscarPorId(Long id) {
        return Optional.ofNullable(tablaPacientes.get(id));
    }

    @Override
    public List<Paciente> buscarTodos() {
        return new ArrayList<>(tablaPacientes.values());
    }

    @Override
    public void eliminar(Long id) {
        tablaPacientes.remove(id);
        guardarEnArchivo(); // Actualizamos archivo tras eliminar
    }

    @Override
    public Paciente actualizar(Paciente pacienteModificado) {
        if (tablaPacientes.containsKey(pacienteModificado.getId())) {
            tablaPacientes.put(pacienteModificado.getId(), pacienteModificado);
            guardarEnArchivo(); // Actualizamos archivo tras modificar
            return pacienteModificado;
        }
        return null;
    }
}