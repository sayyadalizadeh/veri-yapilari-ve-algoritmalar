public class EBOBRecursive {

    // Öklid Algoritması: gcd(a,b) = gcd(b, a%b), gcd(a,0)=a
    static int gcd(int a, int b) {
        // Taban durum: b=0 ise cevap a'nın mutlak değeri
        if (b == 0) {
            return Math.abs(a);
        }
        // Özyineleme
        return gcd(b, a % b);
    }

    public static void main(String[] args) {
        System.out.println(gcd(48, 18)); // 6
    }
}
