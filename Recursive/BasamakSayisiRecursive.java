public class BasamakSayisiRecursive {

    // n sayısının basamak adedi (ör. 472 → 3)
    static int basamakSayisi(int n) {
        // Negatif için mutlak değer
        n = Math.abs(n);
        // 0 özel durum: 1 basamak
        if (n == 0) {
            return 1;
        }
        // Taban durum: 1 basamak
        if (n < 10) {
            return 1;
        }
        // 1 + (n/10'un basamak sayısı)
        return 1 + basamakSayisi(n / 10);
    }

    public static void main(String[] args) {
        System.out.println(basamakSayisi(0));    // 1
        System.out.println(basamakSayisi(7));    // 1
        System.out.println(basamakSayisi(472));  // 3
        System.out.println(basamakSayisi(-123)); // 3
    }
}
