package entity;

public class Odontologo extends Persona {
    private String matricula;
    private Especialidad especialidad;

    public Odontologo() {
    }

    public Odontologo(Long id, String nombre, String apellido, String dni, String email, String telefono, String matricula, Especialidad especialidad) {
        // Delegamos al constructor del padre
        super(id, nombre, apellido, dni, email, telefono);
        // Inicializamos lo exclusivo del hijo
        this.matricula = matricula;
        this.especialidad = especialidad;
    }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public Especialidad getEspecialidad() { return especialidad; }
    public void setEspecialidad(Especialidad especialidad) { this.especialidad = especialidad; }

    @Override
    public String toString() {
        return "Odontólogo [ID=" + getId() + ", Dr. " + getApellido() + " - Especialidad=" + especialidad + "]";
    }
}