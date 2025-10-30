public class DiziMaxRecursive {

    // Dizinin 0..n-1 arası maksimumunu bul
    static int diziMax(int[] a, int n) {
        // Taban durum: tek eleman varsa o maksimumdur
        if (n == 1) {
            return a[0];
        }
        // Özyineleme: son eleman ile (0..n-2) maksimumunu kıyasla
        int maxKalan = diziMax(a, n - 1);
        return Math.max(maxKalan, a[n - 1]);
    }

    public static void main(String[] args) {
        int[] arr = {5, 1, 9, 3, 7};
        System.out.println(diziMax(arr, arr.length)); // 9
    }
}
