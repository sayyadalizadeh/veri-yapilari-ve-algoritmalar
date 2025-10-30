public class ClearKuyrukBagliListe {

    // Düğüm
    static class Node {
        int val;
        Node next;
        Node(int v) { val = v; next = null; }
    }

    // Uçlar ve sayaç
    static Node head = null;
    static Node tail = null;
    static int count = 0;

    // Kuyruğa ekleme
    static void enqueue(int x) {
        Node n = new Node(x);
        if (head == null) {
            head = tail = n;
        } else {
            tail.next = n;
            tail = n;
        }
        count++;
    }

    // Temizle
    static void clear() {
        // Bütün işaretçileri bırak ve sayacı sıfırla
        head = null;
        tail = null;
        count = 0;
    }

    // Eleman sayısı (kontrol için)
    static int size() {
        return count;
    }

    public static void main(String[] args) {
        // Ekle
        enqueue(5);
        enqueue(15);
        enqueue(25);
        System.out.println("size (once)  = " + size()); // 3
        // Temizle
        clear();
        System.out.println("size (sonra) = " + size()); // 0
    }
}
