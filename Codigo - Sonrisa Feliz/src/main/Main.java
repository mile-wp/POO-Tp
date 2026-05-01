package main;

import entity.Especialidad;
import entity.EstadoTurno;
import entity.Odontologo;
import entity.Paciente;
import entity.Turno;

import java.time.LocalDate;
import java.time.LocalTime;

public class Main {

    public static void main(String[] args) {

        System.out.println("=== INICIANDO PRUEBA FINAL DE LA CAPA ENTITY ===\n");

        // 1. Creación del Odontólogo
        Odontologo odontologo = new Odontologo(
                1L,
                "Ana",
                "López",
                "32111222",
                "ana.lopez@clinica.com", // NUEVO
                "1155556666",            // NUEVO
                "MP-9001",
                Especialidad.ENDODONCIA
        );

        // 2. Creación del Paciente
        Paciente paciente = new Paciente(
                1L,
                "Martín",
                "Gómez",
                "41222333",
                "martin.gomez@gmail.com", // NUEVO
                "1144447777",             // NUEVO
                LocalDate.now(),
                "Av. Rivadavia",
                "450",
                "Quilmes",
                "Buenos Aires"
        );

        System.out.println("\n✅ Paciente instanciado (con Domicilio integrado):");
        System.out.println(paciente);

        // 3. Creación del Turno (Prueba de Asociación y consola limpia)
        Turno turno = new Turno(
                1L,
                paciente,
                odontologo,
                LocalDate.of(2026, 5, 25),
                LocalTime.of(16, 45),
                EstadoTurno.PENDIENTE
        );
        System.out.println("\n✅ Turno creado (El núcleo de nuestra clínica):");
        // Aquí veremos en acción el nuevo toString() resumido que diseñamos
        System.out.println(turno);

        System.out.println("\n=== LA ARQUITECTURA DE DOMINIO FUNCIONA PERFECTAMENTE ===");
    }
}