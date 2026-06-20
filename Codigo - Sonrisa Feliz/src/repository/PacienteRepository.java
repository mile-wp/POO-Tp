package repository;

import entity.Paciente;
import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PacienteRepository implements IRepository<Paciente> {

    private Map<Long, Paciente> tablaPacientes = new HashMap<>();
    private Long generadorId = 1L;
    private static final String FILE_NAME = obtenerRutaArchivo();

    public PacienteRepository() {
        crearCarpetaSiNoExiste();
        cargarDesdeArchivo();
    }

    // --- MÉTODOS DE PERSISTENCIA ---

    /**
     * Calcula la ruta absoluta de "src/data/pacientes.dat" basándose en la
     * raíz real del proyecto (donde está la carpeta "src"), sin depender del
     * working directory configurado en cada IDE.
     */
    private static String obtenerRutaArchivo() {
        try {
            File ubicacionClases = new File(
                    PacienteRepository.class.getProtectionDomain()
                            .getCodeSource()
                            .getLocation()
                            .toURI()
            );

            // ubicacionClases suele ser .../NombreProyecto/out/production/NombreModulo
            // Subimos hasta encontrar la raíz del proyecto (donde está "src")
            File directorioActual = ubicacionClases;
            while (directorioActual != null && !new File(directorioActual, "src").isDirectory()) {
                directorioActual = directorioActual.getParentFile();
            }

            if (directorioActual == null) {
                // No se encontró "src" subiendo en el árbol: usamos ruta relativa como respaldo
                System.err.println("No se pudo localizar la carpeta 'src'. Se usará ruta relativa.");
                return "src/data/pacientes.dat";
            }

            return new File(directorioActual, "src/data/pacientes.dat").getAbsolutePath();

        } catch (URISyntaxException | NullPointerException e) {
            System.err.println("Error al calcular la ruta del archivo: " + e.getMessage());
            return "src/data/pacientes.dat";
        }
    }

    private void crearCarpetaSiNoExiste() {
        File carpeta = new File(FILE_NAME).getParentFile();
        if (carpeta != null && !carpeta.exists()) {
            boolean creada = carpeta.mkdirs();
            if (creada) {
                System.out.println("Carpeta '" + carpeta.getPath() + "' creada correctamente.");
            } else {
                System.err.println("No se pudo crear la carpeta '" + carpeta.getPath() + "'.");
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