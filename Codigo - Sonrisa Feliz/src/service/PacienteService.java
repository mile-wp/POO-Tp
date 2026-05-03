package service;

import entity.Domicilio;
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
        validarPaciente(paciente);
        return pacienteRepository.guardar(paciente);
    }

    public void validarPaciente(Paciente paciente) {
        if (paciente == null) {
            throw new IllegalArgumentException("El paciente no puede ser nulo");
        }

        validarNombre(paciente.getNombre());
        validarApellido(paciente.getApellido());
        validarDni(paciente.getDni());
        validarDniDuplicado(paciente.getDni());
        validarEmail(paciente.getEmail());
        validarEmailDuplicado(paciente.getEmail());
        validarTelefono(paciente.getTelefono());
        validarTelefonoDuplicado(paciente.getTelefono());

        validarDomicilio(paciente.getDomicilio());
    }

    public void validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }

        if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            throw new IllegalArgumentException("El nombre solo debe contener letras");
        }
    }

    public void validarApellido(String apellido) {
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido es obligatorio");
        }

        if (!apellido.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            throw new IllegalArgumentException("El apellido solo debe contener letras");
        }
    }

    public void validarDni(String dni) {
        if (dni == null || dni.trim().isEmpty()) {
            throw new IllegalArgumentException("El DNI es obligatorio");
        }

        if (!dni.matches("\\d+")) {
            throw new IllegalArgumentException("El DNI debe contener solo números");
        }
    }

    public void validarDniDuplicado(String dni) {
        List<Paciente> pacientes = pacienteRepository.buscarTodos();

        for (Paciente p : pacientes) {
            if (p.getDni().equals(dni)) {
                throw new IllegalArgumentException("Ya existe un paciente con ese DNI");
            }
        }
    }

    public void validarEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email es obligatorio");
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("Formato de email inválido");
        }
    }

    public void validarEmailDuplicado(String email) {
        List<Paciente> pacientes = pacienteRepository.buscarTodos();

        for (Paciente p : pacientes) {
            if (p.getEmail().equalsIgnoreCase(email)) {
                throw new IllegalArgumentException("Ya existe un paciente con ese email");
            }
        }
    }

    public void validarTelefono(String telefono) {
        if (telefono == null || telefono.trim().isEmpty()) {
            throw new IllegalArgumentException("El teléfono es obligatorio");
        }

        // Solo números
        if (!telefono.matches("\\d+")) {
            throw new IllegalArgumentException("El teléfono debe contener solo números");
        }

        // Validar formato argentino (10 dígitos, ej: 11xxxxxxxx)
        if (telefono.length() != 10) {
            throw new IllegalArgumentException("El teléfono debe contener 10 dígitos (ej: 11xxxxxxxx)");
        }

        // Validar que empiece con 11
        if (!telefono.startsWith("11")) {
            throw new IllegalArgumentException("El teléfono debe comenzar con 11");
        }
    }

    public void validarTelefonoDuplicado(String telefono) {
        List<Paciente> pacientes = pacienteRepository.buscarTodos();

        for (Paciente p : pacientes) {
            if (p.getTelefono().equals(telefono)) {
                throw new IllegalArgumentException("Ya existe un paciente con ese teléfono");
            }
        }
    }

    public void validarDomicilio(Domicilio domicilio) {
        if (domicilio == null) {
            throw new IllegalArgumentException("El domicilio no puede ser nulo");
        }

        validarCalle(domicilio.getCalle());
        validarAltura(domicilio.getAltura());
        validarLocalidad(domicilio.getCalle());
        validarProvincia(domicilio.getProvincia());
    }

    // Acepta calles con nros, no solo letras
    public void validarCalle(String calle) {
        if (calle == null) {
            throw new IllegalArgumentException("La calle es obligatoria");
        }
        if (!calle.matches("[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ ]+")) {
            throw new IllegalArgumentException("Calle inválida");
        }
    }
    public void validarAltura(String altura) {
        if (altura == null || altura.trim().isEmpty()) {
            throw new IllegalArgumentException("La altura es obligatoria");
        }

        if (!altura.matches("\\d+")) {
            throw new IllegalArgumentException("La altura debe ser numérica");
        }
    }

    public void validarLocalidad(String localidad) {
        if (localidad == null || localidad.trim().isEmpty()) {
            throw new IllegalArgumentException("Localidad obligatoria");
        }

        if (!localidad.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            throw new IllegalArgumentException("Localidad inválida");
        }
    }

    public void validarProvincia(String provincia) {
        if (provincia == null || provincia.trim().isEmpty()) {
            throw new IllegalArgumentException("Provincia obligatoria");
        }

        if (!provincia.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            throw new IllegalArgumentException("Provincia inválida");
        }
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