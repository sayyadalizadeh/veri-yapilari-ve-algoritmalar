public class StringHashPolinom {

    // Basit polinomik string hash:
    // h = (s[0]*p^(n-1) + s[1]*p^(n-2) + ... + s[n-1]*p^0) mod M
    // Burada p sabit küçük bir asal (örn. 31), M tablo boyutu.
    static int hashString(String s, int p, int M) {
        long h = 0; // taşmayı azaltmak için long hesaplayıp sonda int'e indiriyoruz

        // Karakterleri soldan sağa işleyelim
        for (int i = 0; i < s.length(); i++) {
            // Karakterin sayısal değeri (sade olsun diye direkt char kodunu kullanıyoruz)
            int val = s.charAt(i);

            // h = (h*p + val) mod M
            h = (h * p + val) % M;
        }

        // Sonuç int aralığına indir
        return (int) h;
    }

    public static void main(String[] args) {
        int M = 101;   // tablo boyutu olarak küçük bir asal
        int p = 31;    // taban (genelde 31, 53 gibi küçük asal seçilir)

        // Örnek anahtarlar
        String[] keys = {"ahmet", "mehmet", "data", "structures", "algorithms"};

        for (String k : keys) {
            int h = hashString(k, p, M);
            System.out.println("key=\"" + k + "\"  ->  hash=" + h);
        }
    }
}
