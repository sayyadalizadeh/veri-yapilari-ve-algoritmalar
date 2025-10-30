public class TersCevirStringRecursive {

    // String'i ters çevir: reverse("ABC") -> "CBA"
    static String ters(String s) {
        // Taban durum: boş ya da tek karakter
        if (s.length() <= 1) {
            return s;
        }
        // Özyineleme: son karakter + geri kalan(ters)
        return s.charAt(s.length() - 1) + ters(s.substring(0, s.length() - 1));
    }

    public static void main(String[] args) {
        System.out.println(ters("merhaba")); // "abahrem"
    }
}
