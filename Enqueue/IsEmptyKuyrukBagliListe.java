public class IsEmptyKuyrukBagliListe {

    // Düğüm yapısı
    static class Node {
        int val;
        Node next;
        Node(int v) { val = v; next = null; }
    }

    // Uçlar ve sayaç
    static Node head = null;   // front
    static Node tail = null;   // rear
    static int size = 0;       // eleman sayısı

    // Kuyruğa ekleme (enqueue)
    static void enqueue(int x) {
        Node n = new Node(x);
        if (head == null) {
            head = tail = n;
        } else {
            tail.next = n;
            tail = n;
        }
        size++;
    }

    // Kuyruk boş mu?
    static boolean isEmpty() {
        return head == null;   // veya: size == 0
    }

    public static void main(String[] args) {
        // Başta boş
        System.out.println("bos? " + isEmpty()); // true
        // Ekle
        enqueue(5);
        // Artık boş değil
        System.out.println("bos? " + isEmpty()); // false
    }
}
