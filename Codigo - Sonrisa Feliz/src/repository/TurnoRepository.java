package repository;

import entity.Turno;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TurnoRepository implements IRepository<Turno> {

    private List<Turno> tablaTurnos = new ArrayList<>();
    private Long generadorId = 1L;
    private static final String FILE_NAME = "C:/Users/Usuario/OneDrive/Escritorio/POO-Tp/Codigo - Sonrisa Feliz/src/data/turnos.dat";

    public TurnoRepository() {
        cargarDesdeArchivo();
    }

    // --- MÉTODOS DE PERSISTENCIA ---

    private void guardarEnArchivo() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(tablaTurnos);
            out.writeObject(generadorId);
        } catch (IOException e) {
            System.err.println("Error al guardar turnos: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void cargarDesdeArchivo() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                tablaTurnos = (List<Turno>) in.readObject();
                generadorId = (Long) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error al cargar turnos: " + e.getMessage());
            }
        }
    }

    // --- MÉTODOS DE NEGOCIO ---

    @Override
    public Turno guardar(Turno turno) {
        turno.setId(generadorId);
        generadorId++;
        tablaTurnos.add(turno);
        guardarEnArchivo(); // Persistencia automática
        return turno;
    }

    @Override
    public Optional<Turno> buscarPorId(Long id) {
        return tablaTurnos.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Turno> buscarTodos() {
        return new ArrayList<>(tablaTurnos);
    }

    @Override
    public void eliminar(Long id) {
        if (tablaTurnos.removeIf(turno -> turno.getId().equals(id))) {
            guardarEnArchivo(); // Persistencia automática
        }
    }

    @Override
    public Turno actualizar(Turno turnoModificado) {
        for (int i = 0; i < tablaTurnos.size(); i++) {
            if (tablaTurnos.get(i).getId().equals(turnoModificado.getId())) {
                tablaTurnos.set(i, turnoModificado);
                guardarEnArchivo(); // Persistencia automática
                return turnoModificado;
            }
        }
        return null;
    }
}