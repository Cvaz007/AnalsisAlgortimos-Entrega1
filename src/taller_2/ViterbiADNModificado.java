package taller_2;

import java.util.HashMap;
import java.util.Map;

public class ViterbiADNModificado {

    public static void main(String[] args) {
        char[] observaciones = {'A', 'T', 'G', 'C', 'A', 'T', 'C', 'G', 'A'};
        char[] estados = {'E', 'F'};
        Map<Character, Double> inicio = new HashMap<>();
        inicio.put('E', -1.0);
        inicio.put('F', -1.0);
        Map<Character, Map<Character, Double>> transiciones = new HashMap<>();
        transiciones.put('E', new HashMap<Character, Double>() {{
            put('E', -1.0);
            put('F', -1.0);
        }});
        transiciones.put('F', new HashMap<Character, Double>() {{
            put('E', -1.322);
            put('F', -0.737);
        }});
        Map<Character, Map<Character, Double>> emisiones = new HashMap<>();
        emisiones.put('E', new HashMap<Character, Double>() {{
            put('A', -2.322);
            put('C', -1.737);
            put('G', -1.737);
            put('T', -2.322);
        }});
        emisiones.put('F', new HashMap<Character, Double>() {{
            put('A', -1.737);
            put('C', -2.322);
            put('G', -2.322);
            put('T', -1.737);
        }});

        Object[] result = viterbiADNModificado(observaciones, estados, inicio, transiciones, emisiones);
        char[] mostProbableSequence = (char[]) result[0];
        System.out.print("Secuencia de estados ocultos más probable (modificada): ");
        for (char state : mostProbableSequence) {
            System.out.print(state + " ");
        }
        System.out.println();

        double[][] viterbiModified = (double[][]) result[1];
        printViterbiMatrix(viterbiModified, observaciones, estados);
    }

    public static Object[] viterbiADNModificado(char[] observaciones, char[] estados, Map<Character, Double> inicio,
                                                Map<Character, Map<Character, Double>> transiciones,
                                                Map<Character, Map<Character, Double>> emisiones) {
        int n = observaciones.length;
        int m = estados.length;

        // Initialization of Viterbi matrix and path
        double[][] viterbiModified = new double[n][m];
        Map<Character, char[]> pathModified = new HashMap<>();

        // Initial step
        for (char estado : estados) {
            viterbiModified[0][getIndex(estados, estado)] = inicio.get(estado) + emisiones.get(estado).get(observaciones[0]);
            pathModified.put(estado, new char[]{estado});
        }

        // Recursion step
        for (int t = 1; t < n; t++) {
            Map<Character, char[]> newPathModified = new HashMap<>();

            for (char estadoActual : estados) {
                double maxProbability = Double.NEGATIVE_INFINITY;
                char maxPrevState = ' ';

                for (char estadoPrevio : estados) {
                    double probability = viterbiModified[t - 1][getIndex(estados, estadoPrevio)] +
                            transiciones.get(estadoPrevio).get(estadoActual) +
                            emisiones.get(estadoActual).get(observaciones[t]);

                    if (probability > maxProbability) {
                        maxProbability = probability;
                        maxPrevState = estadoPrevio;
                    }
                }
                viterbiModified[t][getIndex(estados, estadoActual)] = maxProbability;
                newPathModified.put(estadoActual, concatenateArrays(pathModified.get(maxPrevState), estadoActual));
            }
            pathModified = newPathModified;
        }

        // Find the most probable state in the last step
        double maxFinalProbability = Double.NEGATIVE_INFINITY;
        char finalState = ' ';
        for (char estado : estados) {
            if (viterbiModified[n - 1][getIndex(estados, estado)] > maxFinalProbability) {
                maxFinalProbability = viterbiModified[n - 1][getIndex(estados, estado)];
                finalState = estado;
            }
        }

        return new Object[]{pathModified.get(finalState), viterbiModified};
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

    public static void printViterbiMatrix(double[][] viterbiModified, char[] observaciones, char[] estados) {
        System.out.println("Matriz de Viterbi (modificada):");
        System.out.print("  ");
        for (char observation : observaciones) {
            System.out.print(observation + " ");
        }
        System.out.println();

        for (int i = 0; i < estados.length; i++) {
            System.out.print(estados[i] + " ");
            for (double value : viterbiModified[i]) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }
}
