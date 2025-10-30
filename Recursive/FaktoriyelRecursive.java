public class FaktoriyelRecursive {

    // n! = n * (n-1)! , 0! = 1
    static long fakt(long n) {
        // Taban durum: 0! ve 1! = 1
        if (n == 0 || n == 1) {
            return 1;
        }
        // Özyineleme: n! = n * (n-1)!
        return n * fakt(n - 1);
    }

    public static void main(String[] args) {
        System.out.println(fakt(5)); // 120
    }
}
