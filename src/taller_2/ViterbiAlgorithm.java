package taller_2;

import java.util.HashMap;
import java.util.Map;

public class ViterbiAlgorithm {

    public static void main(String[] args) {
        char[] observaciones = {'G', 'G', 'C', 'A', 'C', 'T', 'G', 'A', 'A'};
        char[] estados = {'H', 'L'};
        Map<Character, Double> inicio = new HashMap<>();
        inicio.put('H', 0.5);
        inicio.put('L', 0.5);
        Map<Character, Map<Character, Double>> transiciones = new HashMap<>();
        transiciones.put('H', new HashMap<Character, Double>() {{
            put('H', 0.5);
            put('L', 0.5);
        }});
        transiciones.put('L', new HashMap<Character, Double>() {{
            put('H', 0.4);
            put('L', 0.6);
        }});
        Map<Character, Map<Character, Double>> emisiones = new HashMap<>();
        emisiones.put('H', new HashMap<Character, Double>() {{
            put('A', 0.2);
            put('C', 0.3);
            put('G', 0.3);
            put('T', 0.2);
        }});
        emisiones.put('L', new HashMap<Character, Double>() {{
            put('A', 0.3);
            put('C', 0.2);
            put('G', 0.2);
            put('T', 0.3);
        }});

        Object[] result = viterbiAlgorithm(observaciones, estados, inicio, transiciones, emisiones);
        char[] mostProbableSequence = (char[]) result[0];
        System.out.print("Secuencia de estados ocultos más probable: ");
        for (char state : mostProbableSequence) {
            System.out.print(state + " ");
        }
        System.out.println();

        double[][] viterbiMatrix = (double[][]) result[1];
        printViterbiMatrix(viterbiMatrix, observaciones, estados);
    }

    public static Object[] viterbiAlgorithm(char[] observaciones, char[] estados, Map<Character, Double> inicio,
                                            Map<Character, Map<Character, Double>> transiciones,
                                            Map<Character, Map<Character, Double>> emisiones) {
        int n = observaciones.length;
        int m = estados.length;

        // Inicialización de matrices Viterbi y camino
        double[][] viterbi = new double[n][m];
        Map<Character, char[]> camino = new HashMap<>();

        // Paso inicial
        for (char estado : estados) {
            viterbi[0][getIndex(estados, estado)] = inicio.get(estado) * emisiones.get(estado).get(observaciones[0]);
            camino.put(estado, new char[]{estado});
        }

        // Paso recursivo
        for (int t = 1; t < n; t++) {
            Map<Character, char[]> nuevoCamino = new HashMap<>();

            for (char estadoActual : estados) {
                double maxProbabilidad = 0;
                char maxEstadoPrevio = ' ';

                for (char estadoPrevio : estados) {
                    double probabilidad = viterbi[t - 1][getIndex(estados, estadoPrevio)] *
                            transiciones.get(estadoPrevio).get(estadoActual) *
                            emisiones.get(estadoActual).get(observaciones[t]);

                    if (probabilidad > maxProbabilidad) {
                        maxProbabilidad = probabilidad;
                        maxEstadoPrevio = estadoPrevio;
                    }
                }
                viterbi[t][getIndex(estados, estadoActual)] = maxProbabilidad;
                nuevoCamino.put(estadoActual, concatenateArrays(camino.get(maxEstadoPrevio), estadoActual));
            }
            camino = nuevoCamino;
        }

        // Obtener el estado más probable en el último paso
        double maxProbabilidadFinal = 0;
        char estadoFinal = ' ';
        for (char estado : estados) {
            if (viterbi[n - 1][getIndex(estados, estado)] > maxProbabilidadFinal) {
                maxProbabilidadFinal = viterbi[n - 1][getIndex(estados, estado)];
                estadoFinal = estado;
            }
        }

        return new Object[]{camino.get(estadoFinal), viterbi};
    }

    public static char[] concatenateArrays(char[] a, char b) {
        char[] result = new char[a.length + 1];
        System.arraycopy(a, 0, result, 0, a.length);
        result[a.length] = b;
        return result;
    }

    public static int getIndex(char[] array, char value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) {
                return i;
            }
        }
        return -1;
    }

    public static void printViterbiMatrix(double[][] viterbi, char[] observaciones, char[] estados) {
        System.out.println("Matriz de Viterbi:");
        System.out.print("  ");
        for (char observation : observaciones) {
            System.out.print(observation + " ");
        }
        System.out.println();

        for (int i = 0; i < estados.length; i++) {
            System.out.print(estados[i] + " ");
            for (double value : viterbi[i]) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }
}
