public class PutHashBagliListe {

    // Kova (bucket) sayısı
    static int CAP = 7;

    // Her kovada bağlı liste başı (head) tutulur
    static Node[] buckets = new Node[CAP];

    // Bağlı liste düğümü
    static class Node {
        int key;
        int val;
        Node next;

        Node(int k, int v, Node n) {
            key = k;
            val = v;
            next = n;
        }
    }

    // Pozitif mod hash (negatif anahtarlar için de)
    static int hashInt(int key) {
        int h = key % CAP;
        if (h < 0) {
            h += CAP;
        }
        return h;
    }

    // Ekle/Güncelle (put): aynı anahtar varsa değeri günceller; yoksa başa ekler
    static void put(int key, int val) {
        int idx = hashInt(key);

        // Kovadaki listeyi tara: varsa güncelle
        Node cur = buckets[idx];
        while (cur != null) {
            if (cur.key == key) {
                cur.val = val; // güncelle
                return;
            }
            cur = cur.next;
        }

        // Yoksa listenin başına yeni düğüm ekle (O(1))
        buckets[idx] = new Node(key, val, buckets[idx]);
    }

    // Tablonun basit yazdırımı (kovaları ve içeriklerini göster)
    static void printTable() {
        for (int i = 0; i < CAP; i++) {
            System.out.print(i + " : ");
            Node cur = buckets[i];
            if (cur == null) {
                System.out.println("∅");
                continue;
            }
            while (cur != null) {
                System.out.print("(" + cur.key + "->" + cur.val + ")");
                if (cur.next != null) {
                    System.out.print(" -> ");
                }
                cur = cur.next;
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        // Çakışma üretecek anahtarlar ekleyelim
        put(10, 100);
        put(17, 170);   // CAP=7 için 10 ve 17 aynı kovaya düşebilir
        put(24, 240);   // yine aynı kovaya düşebilir
        put(-4, -40);

        printTable();

        // Var olan anahtarın değerini güncelle
        put(17, 999);
        printTable();
    }
}
