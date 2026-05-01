package repository;

import java.util.List;

public interface IRepository<T> {
    T guardar(T entidad);
    T buscarPorId(Long id);
    List<T> buscarTodos();
    void eliminar(Long id);
    T actualizar(T entidad);
}