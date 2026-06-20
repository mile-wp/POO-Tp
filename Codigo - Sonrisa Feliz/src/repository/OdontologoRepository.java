package repository;

import entity.Odontologo;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class OdontologoRepository implements IRepository<Odontologo> {

    private Map<Long, Odontologo> tablaOdontologos = new HashMap<>();
    private Long generadorId = 1L;
    private static final String FILE_NAME = "src/data/odontologos.dat";

    public OdontologoRepository() {
        cargarDesdeArchivo();
    }

    // --- MÉTODOS DE PERSISTENCIA ---

    private void guardarEnArchivo() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(tablaOdontologos);
            out.writeObject(generadorId); // Guardamos también el ID actual para no perder la secuencia
        } catch (IOException e) {
            System.err.println("Error al guardar odontólogos en archivo: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void cargarDesdeArchivo() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                tablaOdontologos = (Map<Long, Odontologo>) in.readObject();
                generadorId = (Long) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error al cargar odontólogos desde archivo: " + e.getMessage());
            }
        }
    }

    // --- MÉTODOS SOBREESCRITOS ---

    @Override
    public Odontologo guardar(Odontologo odontologo) {
        odontologo.setId(generadorId);
        tablaOdontologos.put(generadorId, odontologo);
        generadorId++;
        guardarEnArchivo(); // Persistimos tras el cambio
        return odontologo;
    }

    @Override
    public Optional<Odontologo> buscarPorId(Long id) {
        return Optional.ofNullable(tablaOdontologos.get(id));
    }

    @Override
    public List<Odontologo> buscarTodos() {
        return new ArrayList<>(tablaOdontologos.values());
    }

    @Override
    public void eliminar(Long id) {
        tablaOdontologos.remove(id);
        guardarEnArchivo(); // Persistimos tras el cambio
    }

    @Override
    public Odontologo actualizar(Odontologo odontologoModificado) {
        if (tablaOdontologos.containsKey(odontologoModificado.getId())) {
            tablaOdontologos.put(odontologoModificado.getId(), odontologoModificado);
            guardarEnArchivo(); // Persistimos tras el cambio
            return odontologoModificado;
        }
        return null;
    }
}