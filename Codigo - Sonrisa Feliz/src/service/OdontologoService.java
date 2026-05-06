package service;

import entity.Odontologo;
import entity.Paciente;
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
        validarOdontologo(odontologo);
        return odontologoRepository.guardar(odontologo);

    }

    private void validarOdontologo(Odontologo odontologo) {
        if (odontologo == null) {
            throw new IllegalArgumentException("Odontólogo null");
        }

        validarNombre(odontologo.getNombre());
        validarApellido(odontologo.getApellido());

        validarDni(odontologo.getDni());
        validarDniDuplicado(odontologo.getDni());

        validarEmail(odontologo.getEmail());
        validarEmailDuplicado(odontologo.getEmail());

        validarTelefono(odontologo.getTelefono());
        validarTelefonoDuplicado(odontologo.getTelefono());

        validarMatricula(odontologo.getMatricula());
        validarMatriculaDuplicada(odontologo.getMatricula());
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
        List<Odontologo> odontologos = odontologoRepository.buscarTodos();

        for (Odontologo o : odontologos) {
            if (o.getDni().equals(dni)) {
                throw new IllegalArgumentException("Ya existe un Odontologo con ese DNI");
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
        List<Odontologo> odontologos = odontologoRepository.buscarTodos();

        for (Odontologo o : odontologos) {
            if (o.getEmail().equalsIgnoreCase(email)) {
                throw new IllegalArgumentException("Ya existe un odontologo con ese email");
            }
        }
    }

    public void validarTelefono(String telefono) {
        if (telefono == null || telefono.trim().isEmpty()) {
            throw new IllegalArgumentException("El número de teléfono es obligatorio");
        }

        else if (!telefono.matches("\\d+")) {
            throw new IllegalArgumentException("El número de teléfono debe contener solo números");
        }

        else if (telefono.length() != 10) {
            throw new IllegalArgumentException("El número de teléfono debe contener 10 dígitos (ej: 11xxxxxxxx)");
        }

        else if (!telefono.startsWith("11")) {
            throw new IllegalArgumentException("El número de teléfono debe comenzar con 11");
        }
        else {
            System.out.println("El número de telefono es válido");
        }
    }

    public void validarTelefonoDuplicado(String telefono) {
        List<Odontologo> odontologos = odontologoRepository.buscarTodos();

        for (Odontologo o : odontologos) {
            if (o.getTelefono().equals(telefono)) {
                throw new IllegalArgumentException("Ya existe un paciente con ese teléfono");
            }
        }
    }

    // No existe un formato universal para la matricula, se eligió uno razonable
    private void validarMatricula(String matricula) {
        if (matricula == null || matricula.trim().isEmpty()) {
            throw new IllegalArgumentException("La matrícula es obligatoria");
        }

        // Letras y nros solamente
        if (!matricula.matches("[a-zA-Z0-9]+")) {
            throw new IllegalArgumentException("La matrícula debe ser alfanumérica");
        }

        // longitud
        if (matricula.length() < 4 || matricula.length() > 10) {
            throw new IllegalArgumentException("La matrícula debe tener entre 4 y 10 caracteres");
        }
    }

    private void validarMatriculaDuplicada(String matricula) {
        List<Odontologo> lista = odontologoRepository.buscarTodos();

        for (Odontologo o : lista) {
            if (o.getMatricula().equalsIgnoreCase(matricula)) {
                throw new IllegalArgumentException("Ya existe un odontólogo con esa matrícula");
            }
        }
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
