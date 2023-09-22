class VectorProcessor implements Runnable {
    private int[] vector;
    private int start;
    private int end;


    public VectorProcessor(int[] vector, int start, int end) {
        this.vector = vector;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        for (int i = start; i < end; i++) {
            vector[i] = factorial(vector[i]);
        }
    }

    public static int factorial(int num) {
        if (num <= 1) {
            return 1;
        } else {
            return num * factorial(num - 1);
        }
    }
}