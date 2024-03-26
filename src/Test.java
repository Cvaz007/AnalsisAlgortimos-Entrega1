import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Test {
    public static List<int[]> generarVectores(int cantidad, int rangoInicio, int rangoFin, int incremento) {
        List<int[]> vectores = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < cantidad; i++) {
            int longitud = (i + 1) * incremento + 5;
            int[] nuevoVector = new int[longitud];

            for (int j = 0; j < longitud; j++) {
                nuevoVector[j] = random.nextInt(rangoFin - rangoInicio + 1) + rangoInicio;
            }

            vectores.add(nuevoVector);
        }

        return vectores;
    }

    public static void main(String[] args) {
        generarVectores(100,1,100,5);
    }
}
