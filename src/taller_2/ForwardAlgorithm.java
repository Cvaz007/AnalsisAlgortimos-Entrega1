package taller_2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ForwardAlgorithm {

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
        char[] secuenciaMasProbable = (char[]) result[0];
        System.out.print("Secuencia de estados ocultos más probable (modificada): ");
        for (char estado : secuenciaMasProbable) {
            System.out.print(estado + " ");
        }
        System.out.println();

        double[][] viterbiModificado = (double[][]) result[1];
        imprimirMatrizViterbiModificada(viterbiModificado, observaciones, estados);
    }

    public static Object[] viterbiADNModificado(char[] observaciones, char[] estados, Map<Character, Double> inicio,
                                                Map<Character, Map<Character, Double>> transiciones,
                                                Map<Character, Map<Character, Double>> emisiones) {
        int n = observaciones.length;
        int m = estados.length;

        // Inicialización de matrices Viterbi y camino
        double[][] viterbiModificado = new double[n][m];
        Map<Character, char[]> caminoModificado = new HashMap<>();

        // Paso inicial
        for (char estado : estados) {
            viterbiModificado[0][getIndex(estados, estado)] = inicio.get(estado) + emisiones.get(estado).get(observaciones[0]);
            caminoModificado.put(estado, new char[]{estado});
        }

        // Paso recursivo
        for (int t = 1; t < n; t++) {
            Map<Character, char[]> nuevoCaminoModificado = new HashMap<>();

            for (char estadoActual : estados) {
                double maxProbabilidad = Double.NEGATIVE_INFINITY;
                char maxEstadoPrevio = ' ';

                for (char estadoPrevio : estados) {
                    double probabilidad = viterbiModificado[t - 1][getIndex(estados, estadoPrevio)] +
                            transiciones.get(estadoPrevio).get(estadoActual) +
                            emisiones.get(estadoActual).get(observaciones[t]);

                    if (probabilidad > maxProbabilidad) {
                        maxProbabilidad = probabilidad;
                        maxEstadoPrevio = estadoPrevio;
                    }
                }

                viterbiModificado[t][getIndex(estados, estadoActual)] = maxProbabilidad;

                char[] nuevoCaminoEstadoActual = concatenateArrays(caminoModificado.get(maxEstadoPrevio), estadoActual);
                nuevoCaminoModificado.put(estadoActual, nuevoCaminoEstadoActual);
            }

            caminoModificado = nuevoCaminoModificado;
        }

        // Obtener el estado más probable en el último paso
        double maxProbabilidadFinal = Double.NEGATIVE_INFINITY;
        char estadoFinal = ' ';
        for (char estado : estados) {
            double probabilidad = viterbiModificado[n - 1][getIndex(estados, estado)];
            if (probabilidad > maxProbabilidadFinal) {
                maxProbabilidadFinal = probabilidad;
                estadoFinal = estado;
            }
        }

        return new Object[]{caminoModificado.get(estadoFinal), viterbiModificado};
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

    public static void imprimirMatrizViterbiModificada(double[][] viterbiModificado, char[] observaciones, char[] estados) {
        System.out.println("Matriz de Viterbi (modificada):");
        System.out.print("  ");
        for (char observacion : observaciones) {
            System.out.print(observacion + " ");
        }
        System.out.println();

        for (int i = 0; i < estados.length; i++) {
            System.out.print(estados[i] + " ");
            for (double value : viterbiModificado[i]) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }
}

