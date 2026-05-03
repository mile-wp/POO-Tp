package repository;

import entity.Odontologo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OdontologoRepository implements IRepository<Odontologo> {

    // 1. Base de datos en memoria para odontólogos
    private List<Odontologo> tablaOdontologos;

    // 2. Generador de IDs
    private Long generadorId;

    public OdontologoRepository() {
        this.tablaOdontologos = new ArrayList<>();
        this.generadorId = 1L;
    }

    @Override
    public Odontologo guardar(Odontologo odontologo) {
        odontologo.setId(generadorId);
        generadorId++;
        tablaOdontologos.add(odontologo);
        return odontologo;
    }

    @Override
    public Optional<Odontologo> buscarPorId(Long id) {
        for (Odontologo odontologo : tablaOdontologos) {
            if (odontologo.getId().equals(id)) {
                return Optional.of(odontologo);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Odontologo> buscarTodos() {
        // Retornamos una copia para proteger la lista original
        return new ArrayList<>(tablaOdontologos);
    }

    @Override
    public void eliminar(Long id) {
        tablaOdontologos.removeIf(odontologo -> odontologo.getId().equals(id));
    }

    @Override
    public Odontologo actualizar(Odontologo odontologoModificado) {
        for (int i = 0; i < tablaOdontologos.size(); i++) {
            if (tablaOdontologos.get(i).getId().equals(odontologoModificado.getId())) {
                tablaOdontologos.set(i, odontologoModificado);
                return odontologoModificado;
            }
        }
        return null;
    }
}