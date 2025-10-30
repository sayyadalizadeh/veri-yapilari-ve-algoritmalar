public class DiziToplamRecursive {

    // Dizinin 0..n-1 arası toplamı
    static int diziToplam(int[] a, int n) {
        // Taban durum: n==0 ise toplam 0
        if (n == 0) {
            return 0;
        }
        // Özyineleme: son eleman + geri kalan
        return a[n - 1] + diziToplam(a, n - 1);
    }

    public static void main(String[] args) {
        int[] arr = {2, 4, 6, 8};
        System.out.println(diziToplam(arr, arr.length)); // 20
    }
}
