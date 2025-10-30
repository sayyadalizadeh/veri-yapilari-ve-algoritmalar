public class HashFonksiyonuMod {

    // Basit hash: tamsayı için mod alma
    // Negatif sayılar için de pozitif sonuç üretir
    static int hashInt(int key, int tableSize) {
        // mod sonucu negatif çıkmasın diye +tableSize ile düzeltiyoruz
        int h = key % tableSize;
        if (h < 0) {
            h += tableSize;
        }
        return h;
    }

    public static void main(String[] args) {
        int tableSize = 10; // tablo boyutu
        // Örnek anahtarlar
        int[] keys = {12, 22, -7, 35, 100};

        // Her anahtarın hash'ini yazdıralım
        for (int k : keys) {
            int h = hashInt(k, tableSize);
            System.out.println("key=" + k + "  ->  hash=" + h);
        }
    }
}
