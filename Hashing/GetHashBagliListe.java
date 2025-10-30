public class GetHashBagliListe {

    // Kova sayısı
    static int CAP = 7;

    // Kovalar (her biri bir bağlı listenin başını tutar)
    static Node[] buckets = new Node[CAP];

    // Düğüm
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

    // Pozitif mod hash
    static int hashInt(int key) {
        int h = key % CAP;
        if (h < 0) {
            h += CAP;
        }
        return h;
    }

    // Basit put (baş tarafa ekler, varsa günceller) — arama örneği için
    static void put(int key, int val) {
        int idx = hashInt(key);
        Node cur = buckets[idx];
        while (cur != null) {
            if (cur.key == key) {
                cur.val = val;
                return;
            }
            cur = cur.next;
        }
        buckets[idx] = new Node(key, val, buckets[idx]);
    }

    // Ara (get): bulursa değeri döndür, yoksa Integer.MIN_VALUE
    static int get(int key) {
        int idx = hashInt(key);
        Node cur = buckets[idx];

        // Kovadaki bağlı listeyi tara
        while (cur != null) {
            if (cur.key == key) {
                return cur.val;
            }
            cur = cur.next;
        }

        // Bulunamadı
        return Integer.MIN_VALUE;
    }

    // Küçük yardımcı: sonucu yazdır
    static void printGet(int k) {
        int v = get(k);
        if (v == Integer.MIN_VALUE) {
            System.out.println("get(" + k + ") -> YOK");
        } else {
            System.out.println("get(" + k + ") -> " + v);
        }
    }

    public static void main(String[] args) {
        // Örnek veriler
        put(10, 100);
        put(17, 170);
        put(24, 240);
        put(3, 30);

        // Aramalar
        printGet(10);   // 100
        printGet(17);   // 170
        printGet(24);   // 240
        printGet(99);   // YOK
    }
}
