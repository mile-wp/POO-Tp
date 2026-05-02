package service;

import entity.Odontologo;
import repository.IRepository;
import repository.OdontologoRepository;

import java.util.List;
import java.util.Optional;

public class OdontologoService implements IService<Odontologo> {

    private IRepository<Odontologo> odontologoRepository;

    public OdontologoService() {
        this.odontologoRepository = new OdontologoRepository();
    }

    @Override
    public Odontologo registrar(Odontologo odontologo) {

        // 1. Validación: El nombre no puede estar vacío
        if (odontologo.getNombre() == null || odontologo.getNombre().trim().isEmpty()) {
            System.out.println("Error: El nombre del odontólogo es obligatorio.");
            return null;
        }

        // 2. Validación: El apellido no puede estar vacío
        if (odontologo.getApellido() == null || odontologo.getApellido().trim().isEmpty()) {
            System.out.println("Error: El apellido del odontólogo es obligatorio.");
            return null;
        }

        // 3. Validación: La matrícula no puede estar vacía
        if (odontologo.getMatricula() == null || odontologo.getMatricula().trim().isEmpty()) {
            System.out.println("Error: El número de matrícula es obligatorio.");
            return null;
        }

        // 4. Validación de Lógica de Negocio: Matrícula Única
        List<Odontologo> odontologosExistentes = odontologoRepository.buscarTodos();
        for (Odontologo o : odontologosExistentes) {
            if (o.getMatricula().equalsIgnoreCase(odontologo.getMatricula())) {
                System.out.println("Error: Ya existe un odontólogo registrado con la matrícula: " + odontologo.getMatricula());
                return null;
            }
        }

        // Si superó todas las barreras, lo guardamos en la "base de datos"
        System.out.println("Validaciones superadas. Registrando odontólogo...");
        return odontologoRepository.guardar(odontologo);
    }

    @Override
    public Optional<Odontologo> buscarPorId(Long id) {
        return odontologoRepository.buscarPorId(id);
    }

    @Override
    public void eliminarPorId(Long id) {
        // Validamos si realmente existe antes de intentar borrar
        Optional<Odontologo> existente = odontologoRepository.buscarPorId(id);
        if (existente.isEmpty()) {
            System.out.println("Error: No se puede eliminar. No existe odontólogo con ID: " + id);
            return;
        }
        odontologoRepository.eliminar(id);
        System.out.println("Odontólogo eliminado correctamente.");
    }

    @Override
    public Odontologo actualizar(Odontologo odontologo) {
        // Validamos que nos estén pasando un objeto que ya tiene ID
        if (odontologo.getId() == null) {
            System.out.println("Error: Para actualizar, el odontólogo debe tener un ID asignado.");
            return null;
        }
        return odontologoRepository.actualizar(odontologo);
    }

    @Override
    public List<Odontologo> listarTodos() {
        return odontologoRepository.buscarTodos();
    }
}