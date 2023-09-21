import java.util.Arrays;

public class VectorProcessing {

    // Последовательная обработка вектора
    public static void sequentialProcessing(double[] vector, double multiplier) {
        for (int i = 0; i < vector.length; i++) {
            vector[i] *= multiplier;
        }
    }

    // Многопоточная обработка вектора
    public static void multiThreadedProcessing(double[] vector, double multiplier, int numThreads) {
        int chunkSize = vector.length / numThreads;

        Thread[] threads = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            int start = i * chunkSize;
            int end = (i == numThreads - 1) ? vector.length : (i + 1) * chunkSize;
            threads[i] = new Thread(new VectorProcessor(vector, start, end, multiplier));
            threads[i].start();
        }

        try {
            for (int i = 0; i < numThreads; i++) {
                threads[i].join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Класс для обработки части вектора в отдельном потоке


    public static void main(String[] args) {
        int[] sizes = {10, 100, 1000, 100000};
        int[] threadCounts = {2, 3, 4, 5, 10};
        double multiplier = 2.0;

        for (int N : sizes) {
            double[] vector = new double[N];
            Arrays.fill(vector, 1.0); // Инициализация вектора значениями 1

            System.out.println("размер вектора: " + N);

            // Последовательная обработка
            long startTime = System.currentTimeMillis();
            sequentialProcessing(vector.clone(), multiplier);
            long endTime = System.currentTimeMillis();
            System.out.println("время последовательной обработки: " + (endTime - startTime) + " миллисекунд");

            // Многопоточная обработка
            for (int M : threadCounts) {
                startTime = System.currentTimeMillis();
                multiThreadedProcessing(vector.clone(), multiplier, M);
                endTime = System.currentTimeMillis();
                System.out.println("Многопоточная обработка с " + M + " потоками: " + (endTime - startTime) + " миллисекунд");
            }

            System.out.println();
        }
    }
}
