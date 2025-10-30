public class RemoveHashDizi {

    // Tablo boyutu
    static int CAP = 7;

    // Diziler ve durumlar
    static int[] keys = new int[CAP];
    static int[] vals = new int[CAP];
    static int[] state = new int[CAP]; // 0=boş, 1=dolu, 2=silinmiş (tombstone)

    // Pozitif mod hash
    static int hashInt(int key) {
        int h = key % CAP;
        if (h < 0) {
            h += CAP;
        }
        return h;
    }

    // Ekle (put)
    static void put(int key, int val) {
        int idx = hashInt(key);
        int firstDeleted = -1;

        for (int step = 0; step < CAP; step++) {
            int i = (idx + step) % CAP;

            if (state[i] == 1 && keys[i] == key) {
                vals[i] = val; // güncelle
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

    // Sil (remove): bulursa siler (hücresini "SILINMIS" yapar) ve true döndürür; yoksa false
    static boolean remove(int key) {
        int idx = hashInt(key);

        for (int step = 0; step < CAP; step++) {
            int i = (idx + step) % CAP;

            // Boş hücreye gelindiyse aranan yoktur
            if (state[i] == 0) {
                return false;
            }

            // Dolu ve anahtar eşleşirse → sil
            if (state[i] == 1 && keys[i] == key) {
                state[i] = 2;     // silinmiş (tombstone)
                // (İsteğe bağlı) keys/vals temizlemek şart değil ama okunaklılık için:
                // keys[i] = 0; vals[i] = 0;
                return true;
            }

            // Silinmiş hücre ise devam et
        }

        // Bulunamadı
        return false;
    }

    // Tabloyu yazdır (gözlem için)
    static void printTable() {
        System.out.println("Index | State | Key -> Val");
        for (int i = 0; i < CAP; i++) {
            String st = (state[i] == 0 ? "BOS " : state[i] == 1 ? "DOLU" : "SIL ");
            System.out.println(String.format("  %d   |  %s  |  %d -> %d", i, st, keys[i], vals[i]));
        }
        System.out.println();
    }

    public static void main(String[] args) {
        // Eklemeler
        put(10, 100);
        put(17, 170);
        put(24, 240);
        put(3, 30);
        printTable();

        // Silmeler
        System.out.println("remove(17) -> " + remove(17)); // true
        printTable();

        // Aynı anahtar tekrar eklenebilir (silinmiş hücre yeniden kullanılacaktır)
        put(31, 310); // 31, 17'nin yerine düşebilir (CAP=7'de aynı kovaya denk gelir)
        printTable();

        // Olmayan anahtar silme
        System.out.println("remove(99) -> " + remove(99)); // false
        printTable();
    }
}
