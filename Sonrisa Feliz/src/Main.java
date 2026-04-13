import modelo.Odontologo;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        System.out.println("¡Iniciando Sistema Sonrisa Feliz!");

        Set<String> esp1 = new HashSet<>();
        esp1.add("Ortodoncia");

        Odontologo o1 = new Odontologo(1L, "Camila", "Fernandez", "111111111111", esp1);

        Set<String> esp2 = new HashSet<>();
        esp2.add("Cirugía");

        Odontologo o2 = new Odontologo(2L, "Franco", "Lalin", "1111111111112", esp2);

        List<Odontologo> listadoOdon = new ArrayList<>();
        listadoOdon.add(o1);
        listadoOdon.add(o2);

        for (Odontologo o : listadoOdon) {
            System.out.println(o);
        }
    }
}
