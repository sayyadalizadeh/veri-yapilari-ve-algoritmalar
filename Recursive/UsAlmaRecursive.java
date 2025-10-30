public class UsAlmaRecursive {

    // a^b (b >= 0)
    static long us(long a, int b) {
        // Taban durum: a^0 = 1
        if (b == 0) {
            return 1;
        }
        // Özyineleme: a^b = a * a^(b-1)
        return a * us(a, b - 1);
    }

    public static void main(String[] args) {
        System.out.println(us(2, 10)); // 1024
    }
}
