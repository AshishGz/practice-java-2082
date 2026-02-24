class MyTask implements Runnable {
    private String name;

    MyTask(String name) {
        this.name = name;
    }

    public void run() {
        for (int i = 1; i <= 3; i++) {
            System.out.println(name + " running - " + i);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Thread t1 = new Thread(new MyTask("Thread-1"));
        Thread t2 = new Thread(new MyTask("Thread-2"));
        Thread t3 = new Thread(new MyTask("Thread-3"));

        t1.start();
        t2.start();
        t3.start();
    }
}
