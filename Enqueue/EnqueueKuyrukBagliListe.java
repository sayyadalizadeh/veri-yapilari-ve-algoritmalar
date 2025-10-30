public class EnqueueKuyrukBagliListe {

    // Düğüm: değer ve sonraki işaretçi
    static class Node {
        int val;
        Node next;

        Node(int v) {
            val = v;
            next = null;
        }
    }

    // Kuyruk uçları: head (ön), tail (arka)
    static Node head = null;  // ilk eleman
    static Node tail = null;  // son eleman
    static int size = 0;      // eleman sayısı

    // Kuyruğa ekleme (enqueue): sona bağla
    static void enqueue(int x) {
        // yeni düğüm oluştur
        Node n = new Node(x);

        // kuyruk boşsa hem head hem tail bu düğüm olur
        if (head == null) {
            head = n;
            tail = n;
        } else {
            // boş değilse mevcut sonun next'ine bağla ve tail'i güncelle
            tail.next = n;
            tail = n;
        }

        // eleman sayısını artır
        size++;
    }

    // Kuyruğu ekrana yaz (head -> tail)
    static void print() {
        System.out.print("Kuyruk: [");
        Node cur = head;
        while (cur != null) {
            System.out.print(cur.val);
            if (cur.next != null) {
                System.out.print(", ");
            }
            cur = cur.next;
        }
        System.out.println("]  (size=" + size + ")");
    }

    public static void main(String[] args) {
        // Örnek: birkaç eleman ekleyip yazdıralım
        enqueue(5);
        enqueue(15);
        enqueue(25);
        print(); // Kuyruk: [5, 15, 25] ...

        enqueue(35);
        enqueue(45);
        print(); // Kuyruk: [5, 15, 25, 35, 45] ...
    }
}
