import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.util.Arrays;

public class QuickSortAndPlot {
    public static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }

    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    public static void main(String[] args) {
        int[][] vectores = {
                {23, 80, 48, 62, 40, 71, 83, 27, 8, 83},
                {32, 67, 88, 90, 8, 9, 73, 12, 62, 29, 71, 68, 81, 50, 15},
                {32, 32, 96, 25, 27, 97, 85, 37, 32, 80, 53, 75, 79, 3, 59, 68, 57, 42, 19, 78},
                {27, 82, 8, 24, 11, 50, 67, 82, 78, 37, 3, 3, 90, 85, 71, 61, 4, 79, 54, 91, 90, 22, 78, 78, 74},
                {68, 98, 60, 17, 60, 12, 95, 12, 23, 11, 41, 67, 68, 42, 79, 96, 74, 21, 35, 20, 44, 23, 8, 51, 61, 6, 99, 27, 60, 2},
                {37, 52, 52, 20, 73, 96, 100, 15, 77, 49, 36, 39, 14, 89, 66, 28, 61, 59, 25, 94, 60, 63, 95, 25, 94, 49, 6, 35, 92, 41, 16, 44, 79, 10, 55},
                {83, 55, 3, 80, 69, 11, 44, 52, 17, 30, 29, 90, 42, 99, 89, 33, 6, 74, 79, 28, 25, 65, 88, 73, 78, 76, 66, 46, 44, 73, 93, 48, 67, 88, 81, 90, 75, 70, 66, 44},
                {40, 54, 76, 24, 24, 18, 11, 96, 4, 81, 56, 58, 56, 88, 41, 66, 92, 37, 67, 97, 86, 33, 32, 52, 63, 79, 26, 57, 32, 54, 49, 73, 48, 28, 22, 3, 8, 92, 19, 10, 92, 64, 54, 2, 4},
                {23, 90, 54, 97, 60, 74, 47, 51, 14, 22, 83, 50, 95, 29, 54, 53, 84, 96, 8, 100, 44, 35, 31, 4, 2, 60, 41, 27, 87, 38, 25, 58, 68, 11, 15, 18, 14, 80, 33, 39, 31, 29, 48, 37, 73, 74, 24, 12, 14, 32},
                {2, 36, 78, 78, 45, 53, 37, 39, 62, 46, 94, 88, 28, 32, 55, 97, 30, 50, 3, 22, 91, 93, 47, 34, 27, 95, 62, 78, 1, 82, 30, 65, 86, 91, 93, 83, 54, 57, 75, 35, 84, 84, 72, 31, 70, 14, 13, 2, 33, 31, 3, 54, 21, 98, 15}
        };

        XYSeries series = new XYSeries("Vectores");
        for (int i = 0; i < vectores.length; i++) {
            int[] vector = Arrays.copyOf(vectores[i], vectores[i].length);
            long inicio = System.nanoTime();
            quickSort(vector, 0, vector.length - 1);
            long fin = System.nanoTime();
            long tiempoTranscurrido = fin - inicio;
            series.add(vector.length, tiempoTranscurrido);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Tiempos de Quick Sort", // Título del gráfico
                "Tamaño del Vector", // Etiqueta del eje X
                "Tiempo de Ejecución (ns)", // Etiqueta del eje Y
                dataset // Conjunto de datos
        );

        // Crear el panel del gráfico
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));

        // Crear una ventana para mostrar el gráfico
        JFrame frame = new JFrame("Gráfico de Tiempos de Insertion Sort");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(chartPanel);
        frame.pack();
        frame.setVisible(true);
    }
}

