package main;

import entity.Odontologo;
import entity.Paciente;
import entity.Turno;
import service.OdontologoService;
import service.PacienteService;
import service.TurnoService;
import java.time.LocalDate;
import java.time.LocalTime;
import view.Menu;


public class Main {
    public static void main(String[] args) {
        // 1. Instanciamos la vista (capa de presentación)
        Menu menu = new Menu();
        
        // 2. Damos la bienvenida al usuario
        System.out.println("SISTEMA DE GESTIÓN CLINICA 'SONRISA FELIZ' - CARGANDO...");
        
        // 3. Iniciamos el menú. 
        // Como ahora el menú se maneja de forma recursiva, 
        // este método no terminará hasta que el usuario elija "0. Salir".
        menu.iniciar();
        
        // 4. Mensaje final al cerrar la aplicación
        System.out.println("\nGracias por utilizar el sistema. ¡Hasta pronto!");
    }
}
