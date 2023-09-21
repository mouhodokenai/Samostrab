class VectorProcessor implements Runnable {
    private double[] vector;
    private int start;
    private int end;
    private double multiplier;

    public VectorProcessor(double[] vector, int start, int end, double multiplier) {
        this.vector = vector;
        this.start = start;
        this.end = end;
        this.multiplier = multiplier;
    }

    @Override
    public void run() {
        for (int i = start; i < end; i++) {
            vector[i] *= multiplier;
        }
    }
}