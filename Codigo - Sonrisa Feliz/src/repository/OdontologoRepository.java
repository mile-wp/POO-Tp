package repository;

import entity.Odontologo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class OdontologoRepository implements IRepository<Odontologo> {
    // Definición del HashMap para almacenamiento en memoria
    private Map<Long, Odontologo> tablaOdontologos = new HashMap<>();
    
    // Generador de IDs secuenciales
    private Long generadorId = 1L;

    @Override
    public Odontologo guardar(Odontologo odontologo) {
        // Asignamos el ID autogenerado antes de guardar
        odontologo.setId(generadorId);
        tablaOdontologos.put(generadorId, odontologo);
        generadorId++;
        return odontologo;
    }

    @Override
    public Optional<Odontologo> buscarPorId(Long id) {
        // Acceso directo por clave (ID) en tiempo constante O(1)
        return Optional.ofNullable(tablaOdontologos.get(id));
    }

    @Override
    public List<Odontologo> buscarTodos() {
        // Retornamos una copia de los valores del mapa como lista
        return new ArrayList<>(tablaOdontologos.values());
    }

    @Override
    public void eliminar(Long id) {
        // Eliminación directa por clave[cite: 2]
        tablaOdontologos.remove(id);
    }

    @Override
    public Odontologo actualizar(Odontologo odontologoModificado) {
        // Verificamos existencia antes de actualizar para cumplir con el patrón Expert[cite: 2]
        if (tablaOdontologos.containsKey(odontologoModificado.getId())) {
            tablaOdontologos.put(odontologoModificado.getId(), odontologoModificado);
            return odontologoModificado;
        }
        return null;
    }
}