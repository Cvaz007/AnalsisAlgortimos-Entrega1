import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
public class HeapSortAndPlot extends JFrame {
    public HeapSortAndPlot() {
        initUI();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HeapSortAndPlot example = new HeapSortAndPlot();
            example.setVisible(true);
        });
    }

    private void initUI() {
        XYSeries series = new XYSeries("Tiempos de ejecución");
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

        for (int[] vector : vectores) {
            long inicio = System.nanoTime();
            heapSort(vector);
            long fin = System.nanoTime();
            series.add(vector.length, fin - inicio);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Heap Sort Execution Time",
                "Vector Size",
                "Time (nanoseconds)",
                dataset
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        add(chartPanel);

        pack();
        setTitle("Heap Sort Execution Time");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Métodos heapify y heapSort
    public static void heapify(int[] arr, int n, int i) {
        int largest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;

        if (l < n && arr[l] > arr[largest]) {
            largest = l;
        }

        if (r < n && arr[r] > arr[largest]) {
            largest = r;
        }

        if (largest != i) {
            int temp = arr[i];
            arr[i] = arr[largest];
            arr[largest] = temp;
            heapify(arr, n, largest);
        }
    }

    public static void heapSort(int[] arr) {
        int n = arr.length;

        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        for (int i = n - 1; i > 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            heapify(arr, i, 0);
        }
    }

}
