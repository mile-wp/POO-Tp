package main;

import view.Menu;


public class Main {
    public static void main(String[] args) {

        Menu menu = new Menu();

        System.out.println("SISTEMA DE GESTIÓN CLINICA 'SONRISA FELIZ' - CARGANDO...");

        menu.iniciar();

        System.out.println("\nGracias por utilizar el sistema. ¡Hasta pronto!");
    }
}
