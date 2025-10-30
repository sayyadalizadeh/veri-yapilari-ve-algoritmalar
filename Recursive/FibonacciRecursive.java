public class FibonacciRecursive {

    // fib(0)=0, fib(1)=1, fib(n)=fib(n-1)+fib(n-2)
    static long fib(int n) {
        // Taban durumları
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        // Özyineleme
        return fib(n - 1) + fib(n - 2);
    }

    public static void main(String[] args) {
        System.out.println(fib(6)); // 8
    }
}
