package service;

import entity.OdOrtodoncia;
import entity.OdEndodoncia;
import entity.OdExtraccion;
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

        // =========================================================================
        // NUEVAS VALIDACIONES DE SUBCLASE (INTEGRIDAD DEL RECARGO DE ESPECIALIDAD)
        // =========================================================================
        Double recargo = null;

        // Identificamos dinámicamente qué tipo de especialista está ingresando
        if (odontologo instanceof OdOrtodoncia) {
            recargo = ((OdOrtodoncia) odontologo).getRecargoEspecialidad();
        } else if (odontologo instanceof OdEndodoncia) {
            recargo = ((OdEndodoncia) odontologo).getRecargoEspecialidad();
        } else if (odontologo instanceof OdExtraccion) {
            recargo = ((OdExtraccion) odontologo).getRecargoEspecialidad();
        }

        // Validamos de forma centralizada el atributo exclusivo de las clases hijas
        if (recargo == null || recargo <= 0) {
            System.out.println("Error: El recargo de especialidad debe ser un multiplicador mayor a 0.");
            return null;
        }

        // Si superó todas las barreras generales y específicas, se guarda
        System.out.println("Validaciones superadas con éxito. Registrando odontólogo...");
        return odontologoRepository.guardar(odontologo);
    }

    @Override
    public Optional<Odontologo> buscarPorId(Long id) {
        return odontologoRepository.buscarPorId(id);
    }

    @Override
    public void eliminarPorId(Long id) {
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