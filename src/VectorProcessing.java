import java.util.Arrays;

public class VectorProcessing {

    // Последовательная обработка вектора
    public static void sequentialProcessing(int[] vector) {
        for (int i = 0; i < vector.length; i++) {
            vector[i] = VectorProcessor.factorial(vector[i]);
        }
    }

    // Многопоточная обработка вектора
    public static void multiThreadedProcessing(int[] vector, int numThreads) {
        int chunkSize = vector.length / numThreads;

        Thread[] threads = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            int start = i * chunkSize;
            int end = (i == numThreads - 1) ? vector.length : (i + 1) * chunkSize;
            threads[i] = new Thread(new VectorProcessor(vector, start, end));
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

        for (int N : sizes) {
            int[] vector = new int[N];
            Arrays.fill(vector, 5); // Инициализация вектора значениями 1

            System.out.println("размер вектора: " + N);

            // Последовательная обработка
            long startTime = System.currentTimeMillis();
            sequentialProcessing(vector.clone());
            long endTime = System.currentTimeMillis();
            System.out.println("время последовательной обработки: " + (endTime - startTime) + " миллисекунд");

            // Многопоточная обработка
            for (int M : threadCounts) {
                int[] vectorCopy = Arrays.copyOf(vector, vector.length);
                startTime = System.currentTimeMillis();
                multiThreadedProcessing(vectorCopy, M);
                endTime = System.currentTimeMillis();
                System.out.println("Многопоточная обработка с " + M + " потоками: " + (endTime - startTime) + " миллисекунд");
            }

            System.out.println();
        }
    }
}
