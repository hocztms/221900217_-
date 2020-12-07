import java.util.Scanner;

public class Work4 {
    public static void main(String[] args)throws InterruptedException{
        Scanner imput = new Scanner(System.in);
        System.out.println("输入指定数字");
        long x = imput.nextLong();
        Thread [] threads = new Thread[100000];
        for (int i = 0;i<100000;i++) {
            threads[i] = new Additon(10000 * i + 1, x);
            threads[i].start();
        }
        for (int i = 0;i<100000;i++){
            threads[i].join();
        }
        System.out.println(Additon.getSum());

    }
}
class Additon extends Thread{
    private final long currNum;
    private static long sum;
    private final long x;
    public  Additon(long currNum,long x){
        this.currNum = currNum;
        this.x = x;
    }
    public static synchronized void add(long num){
        sum = sum +num;
    }
    public void run(){
        long sum = 0;
        for (long i=0;i<10000;i++){
            if (contain(currNum+i,x)) sum +=currNum + i;
        }
        add(sum);
    }
    private static boolean contain(long num, long x) {
        return String.valueOf(num).contains(String.valueOf(x));
    }

    public static long getSum() {
        return sum;
    }

}

