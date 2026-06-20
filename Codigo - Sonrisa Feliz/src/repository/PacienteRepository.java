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
        crearCarpetaSiNoExiste();
        cargarDesdeArchivo();
    }

    // --- MÉTODOS DE PERSISTENCIA ---

    private void crearCarpetaSiNoExiste() {
        File carpeta = new File("src/data");
        if (!carpeta.exists()) {
            boolean creada = carpeta.mkdirs();
            if (creada) {
                System.out.println("Carpeta 'src/data' creada correctamente.");
            } else {
                System.err.println("No se pudo crear la carpeta 'src/data'.");
            }
        }
    }

    private void guardarEnArchivo() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(tablaPacientes);
            out.writeObject(generadorId); // Guardamos también el ID actual para no perder la secuencia
        } catch (IOException e) {
            System.err.println("Error al guardar pacientes en archivo: " + e.getMessage());
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
                System.err.println("Error al cargar pacientes desde archivo: " + e.getMessage());
            }
        }
    }

    // --- MÉTODOS SOBREESCRITOS ---

    @Override
    public Paciente guardar(Paciente paciente) {
        paciente.setId(generadorId);
        tablaPacientes.put(generadorId, paciente);
        generadorId++;
        guardarEnArchivo(); // Persistimos tras el cambio
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
        guardarEnArchivo(); // Persistimos tras el cambio
    }

    @Override
    public Paciente actualizar(Paciente pacienteModificado) {
        if (tablaPacientes.containsKey(pacienteModificado.getId())) {
            tablaPacientes.put(pacienteModificado.getId(), pacienteModificado);
            guardarEnArchivo(); // Persistimos tras el cambio
            return pacienteModificado;
        }
        return null;
    }
}