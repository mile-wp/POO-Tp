package repository;

import entity.Turno;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TurnoRepository implements IRepository<Turno> {

    private List<Turno> tablaTurnos;

    private Long generadorId;

    public TurnoRepository() {
        this.tablaTurnos = new ArrayList<>();
        this.generadorId = 1L; // El primer turno que se guarde tendrá el ID 1
    }

    @Override
    public Turno guardar(Turno turno) {
        turno.setId(generadorId);
        generadorId++;
        tablaTurnos.add(turno);
        return turno;
    }

    @Override
    public Optional<Turno> buscarPorId(Long id) {
        for (Turno turno : tablaTurnos) {
            if (turno.getId().equals(id)) {
                return Optional.of(turno);
            }
        }
        return Optional.empty(); // Retorno seguro si el turno no existe
    }

    @Override
    public List<Turno> buscarTodos() {
        return new ArrayList<>(tablaTurnos);
    }

    @Override
    public void eliminar(Long id) {
        tablaTurnos.removeIf(turno -> turno.getId().equals(id));
    }

    @Override
    public Turno actualizar(Turno turnoModificado) {
        for (int i = 0; i < tablaTurnos.size(); i++) {
            if (tablaTurnos.get(i).getId().equals(turnoModificado.getId())) {
                tablaTurnos.set(i, turnoModificado);
                return turnoModificado;
            }
        }

        return null;
    }
}