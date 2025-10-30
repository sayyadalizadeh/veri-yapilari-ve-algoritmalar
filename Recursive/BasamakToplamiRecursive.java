public class BasamakToplamiRecursive {

    // n sayısının basamakları toplamı (ör. 472 → 4+7+2=13)
    static int basamakToplam(int n) {
        // Negatifler için mutlak değer al
        n = Math.abs(n);
        // Taban durum: tek basamaklı ise kendisi
        if (n < 10) {
            return n;
        }
        // Son basamak + kalan sayıların toplamı
        return (n % 10) + basamakToplam(n / 10);
    }

    public static void main(String[] args) {
        System.out.println(basamakToplam(472));  // 13
        System.out.println(basamakToplam(-905)); // 14
    }
}
