class StarvationExample {

    public static void main(String[] args) {

        Runnable task = () -> {
            String name = Thread.currentThread().getName();
            while (true) {
                System.out.println(name + " is running");
            }
        };

        Thread highPriority = new Thread(task, "High-Priority-Thread");
        Thread lowPriority = new Thread(task, "Low-Priority-Thread");

        highPriority.setPriority(Thread.MAX_PRIORITY); // 10
        lowPriority.setPriority(Thread.MIN_PRIORITY);  // 1

        highPriority.start();
        lowPriority.start();
    }
}
