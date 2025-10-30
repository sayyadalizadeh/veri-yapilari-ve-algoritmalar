public class ToplamNRecursive {

    // 1 + 2 + ... + n
    static int toplam(int n) {
        // Taban durum: n<=1 ise kendisi
        if (n <= 1) {
            return n;
        }
        // Özyineleme: n + toplam(n-1)
        return n + toplam(n - 1);
    }

    public static void main(String[] args) {
        System.out.println(toplam(5)); // 15
    }
}
