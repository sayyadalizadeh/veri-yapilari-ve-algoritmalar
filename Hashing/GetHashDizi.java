public class GetHashDizi {

    // Basit tablo boyutu
    static int CAP = 7;

    // Veri dizileri
    static int[] keys = new int[CAP];
    static int[] vals = new int[CAP];
    static int[] state = new int[CAP]; // 0=boş, 1=dolu, 2=silinmiş

    // Pozitif mod hash
    static int hashInt(int key) {
        int h = key % CAP;
        if (h < 0) {
            h += CAP;
        }
        return h;
    }

    // Ekle (put) — arama örneğini kolay göstermek için aynı basit put
    static void put(int key, int val) {
        int idx = hashInt(key);
        int firstDeleted = -1;

        for (int step = 0; step < CAP; step++) {
            int i = (idx + step) % CAP;

            if (state[i] == 1 && keys[i] == key) {
                vals[i] = val;
                return;
            }
            if (state[i] == 2 && firstDeleted == -1) {
                firstDeleted = i;
            }
            if (state[i] == 0) {
                int place = (firstDeleted != -1) ? firstDeleted : i;
                keys[place] = key;
                vals[place] = val;
                state[place] = 1;
                return;
            }
        }
        System.out.println("Tablo dolu, eklenemedi: key=" + key);
    }

    // Ara (get): bulursa değeri döndürür, yoksa Integer.MIN_VALUE döndürür
    static int get(int key) {
        int idx = hashInt(key);

        // Linear probing: boş bir hücre görene kadar devam
        for (int step = 0; step < CAP; step++) {
            int i = (idx + step) % CAP;

            // Boş hücre → anahtar kesinlikle yok (arama biter)
            if (state[i] == 0) {
                break;
            }

            // Dolu ve anahtar eşleşirse → değeri döndür
            if (state[i] == 1 && keys[i] == key) {
                return vals[i];
            }

            // Silinmiş hücre → aramaya devam (çünkü zincirin devamı olabilir)
            // state[i] == 2 ise hiçbir şey yapmadan döngü sürer
        }

        // Bulunamadı
        return Integer.MIN_VALUE;
    }

    // Küçük yazdırma (gösterim için)
    static void printGet(int k) {
        int v = get(k);
        if (v == Integer.MIN_VALUE) {
            System.out.println("get(" + k + ") -> YOK");
        } else {
            System.out.println("get(" + k + ") -> " + v);
        }
    }

    public static void main(String[] args) {
        // Bazı verileri ekleyelim
        put(10, 100);
        put(17, 170);
        put(24, 240);
        put(-4, -40);

        // Aramalar
        printGet(10);   // 100
        printGet(17);   // 170
        printGet(24);   // 240
        printGet(-4);   // -40
        printGet(3);    // YOK
    }
}
