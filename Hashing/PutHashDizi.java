public class PutHashDizi {

    // Basit tablo boyutu (küçük tutuyoruz, eğitim amaçlı)
    static int CAP = 7;

    // Anahtar ve değer dizileri
    static int[] keys = new int[CAP];
    static int[] vals = new int[CAP];

    // Hücre durumu: 0=boş, 1=dolu, 2=silinmiş (tombstone)
    static int[] state = new int[CAP];

    // Pozitif mod hash (negatif anahtarlar için de çalışır)
    static int hashInt(int key) {
        int h = key % CAP;
        if (h < 0) {
            h += CAP;
        }
        return h;
    }

    // Ekleme (put): aynı anahtar varsa değeri günceller, yoksa uygun yere yerleştirir
    static void put(int key, int val) {
        int idx = hashInt(key);

        // İlk gördüğümüz "silinmiş" hücreyi hatırla (yeniden kullanım için)
        int firstDeleted = -1;

        // CAP adımına kadar dene (dairesel ilerle)
        for (int step = 0; step < CAP; step++) {

            // Mevcut indis
            int i = (idx + step) % CAP;

            // Hücre doluysa ve anahtar aynıysa: güncelle ve bitir
            if (state[i] == 1 && keys[i] == key) {
                vals[i] = val;
                return;
            }

            // Silinmiş bir hücre görürsek, ilkini kaydet (boşa aramayı uzatmadan)
            if (state[i] == 2 && firstDeleted == -1) {
                firstDeleted = i;
            }

            // Boş hücre bulursak yerleştir (veya önceden silinmiş varsa oraya)
            if (state[i] == 0) {
                int place = (firstDeleted != -1) ? firstDeleted : i;
                keys[place] = key;
                vals[place] = val;
                state[place] = 1; // dolu
                return;
            }
        }

        // Tüm tablo dolu ise
        System.out.println("Tablo dolu, eklenemedi: key=" + key);
    }

    // Tabloyu basitçe yazdır (durumları görmek için)
    static void printTable() {
        System.out.println("Index | State | Key -> Val");
        for (int i = 0; i < CAP; i++) {
            String st = (state[i] == 0 ? "BOS " : state[i] == 1 ? "DOLU" : "SIL ");
            System.out.println(String.format("  %d   |  %s  |  %d -> %d", i, st, keys[i], vals[i]));
        }
        System.out.println();
    }

    public static void main(String[] args) {
        // Birkaç çakışma yaratacak anahtar deneyelim
        put(10, 100);
        put(17, 170); // 10 ve 17, CAP=7 için aynı kovaya düşebilir (çakışma)
        put(24, 240); // yine çakışma zinciri
        put(3, 30);
        put(-4, -40); // negatif anahtar örneği
        printTable();

        // Var olan anahtarı güncelle
        put(17, 999);
        printTable();
    }
}
