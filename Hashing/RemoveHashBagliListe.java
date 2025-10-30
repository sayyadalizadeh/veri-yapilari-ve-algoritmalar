public class RemoveHashBagliListe {

    // Kova sayısı
    static int CAP = 7;

    // Kovalar (bağlı liste başları)
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

    // Basit put (baş tarafa ekle; varsa güncelle)
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

    // Sil (remove): bulursa düğümü listeden çıkar ve true döndür; yoksa false
    static boolean remove(int key) {
        int idx = hashInt(key);
        Node cur = buckets[idx];
        Node prev = null;

        // Kovadaki bağlı listeyi tara
        while (cur != null) {
            if (cur.key == key) {
                // Bağlantıları atlayarak düğümü çıkar
                if (prev == null) {
                    // ilk düğüm ise, başı bir sonrakine kaydır
                    buckets[idx] = cur.next;
                } else {
                    prev.next = cur.next;
                }
                return true;
            }
            prev = cur;
            cur = cur.next;
        }
        // Bulunamadı
        return false;
    }

    // Tabloyu yazdır (kovalar)
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
        // Eklemeler
        put(10, 100);
        put(17, 170);
        put(24, 240);
        put(31, 310); // 10,17,24,31 CAP=7 için aynı kova hattına düşebilir

        printTable();

        // Silmeler
        System.out.println("remove(17) -> " + remove(17)); // true
        printTable();

        System.out.println("remove(99) -> " + remove(99)); // false
        printTable();
    }
}
