package service;

import entity.Paciente;
import repository.IRepository;
import repository.PacienteRepository;

import java.util.List;
import java.util.Optional;

public class PacienteService implements IService<Paciente> {

    private IRepository<Paciente> pacienteRepository;

    public PacienteService() {
        this.pacienteRepository = new PacienteRepository();
    }

    @Override
    public Paciente registrar(Paciente paciente) {

        // 1. Validación: El nombre no puede estar vacío
        if (paciente.getNombre() == null || paciente.getNombre().trim().isEmpty()) {
            System.out.println("Error: El nombre del paciente es obligatorio.");
            return null;
        }

        // 2. Validación: El apellido no puede estar vacío
        if (paciente.getApellido() == null || paciente.getApellido().trim().isEmpty()) {
            System.out.println("Error: El apellido del paciente es obligatorio.");
            return null;
        }

        // 3. Validación: El DNI no puede estar vacío
        if (paciente.getDni() == null || paciente.getDni().trim().isEmpty()) {
            System.out.println("Error: El DNI del paciente es obligatorio.");
            return null;
        }

        // 4. Validación de Lógica de Negocio: No puede haber DNI duplicado
        List<Paciente> pacientesExistentes = pacienteRepository.buscarTodos();
        for (Paciente p : pacientesExistentes) {
            if (p.getDni().equals(paciente.getDni())) {
                System.out.println("Error: Ya existe un paciente registrado con el DNI: " + paciente.getDni());
                return null;
            }
        }

        // Si pasó todos los "if", tiene luz verde para guardarse
        System.out.println("Validaciones superadas. Registrando paciente...");
        return pacienteRepository.guardar(paciente);
    }

    @Override
    public Optional<Paciente> buscarPorId(Long id) {
        return pacienteRepository.buscarPorId(id);
    }

    @Override
    public void eliminarPorId(Long id) {
        // Podríamos validar si existe antes de eliminar
        Optional<Paciente> existente = pacienteRepository.buscarPorId(id);
        if (existente.isEmpty()) {
            System.out.println("Error: No se puede eliminar. No existe paciente con ID: " + id);
            return;
        }
        pacienteRepository.eliminar(id);
        System.out.println("Paciente eliminado correctamente.");
    }

    @Override
    public Paciente actualizar(Paciente paciente) {
        // Validamos que el paciente que nos pasan tenga un ID válido
        if (paciente.getId() == null) {
            System.out.println("Error: Para actualizar, el paciente debe tener un ID asignado.");
            return null;
        }
        return pacienteRepository.actualizar(paciente);
    }

    @Override
    public List<Paciente> listarTodos() {
        return pacienteRepository.buscarTodos();
    }
}