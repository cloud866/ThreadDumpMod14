import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {

    private Object lock1 = new Object();
    private Object lock2 = new Object();

    private void doWork1() {
        synchronized (lock1) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
            synchronized (lock2) {
                System.out.println("Working on job1");
            }
        }
    }

    private void doWork2() {
        synchronized (lock2) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
            synchronized (lock1) {
                System.out.println("Working on job2");
            }
        }
    }

    public static void main(String[] args) {
        Test test = new Test();

        ExecutorService service = Executors.newFixedThreadPool(2);
        service.execute(() -> test.doWork1());
        service.execute(() -> test.doWork2());

        service.shutdown();
    }
}