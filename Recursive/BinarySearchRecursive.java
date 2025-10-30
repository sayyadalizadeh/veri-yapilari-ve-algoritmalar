public class BinarySearchRecursive {

    // Sıralı dizide hedefi arar; bulursa indeksini, yoksa -1 döndürür
    static int ara(int[] a, int left, int right, int target) {
        // Aralık bitti mi?
        if (left > right) {
            return -1;
        }
        // Orta indisi bul
        int mid = left + (right - left) / 2;

        // Orta eleman kontrol
        if (a[mid] == target) {
            return mid;
        }
        // Hedef ortadan küçükse sol yarıda ara
        if (target < a[mid]) {
            return ara(a, left, mid - 1, target);
        }
        // Değilse sağ yarıda ara
        return ara(a, mid + 1, right, target);
    }

    public static void main(String[] args) {
        // Sıralı dizi
        int[] dizi = {2, 5, 9, 12, 15, 21, 30};

        System.out.println(ara(dizi, 0, dizi.length - 1, 12)); // 3
        System.out.println(ara(dizi, 0, dizi.length - 1, 7));  // -1
    }
}
