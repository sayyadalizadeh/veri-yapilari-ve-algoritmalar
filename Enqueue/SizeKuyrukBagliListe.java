public class SizeKuyrukBagliListe {

    // Düğüm
    static class Node {
        int val;
        Node next;
        Node(int v) { val = v; next = null; }
    }

    // Uçlar ve sayaç
    static Node head = null;
    static Node tail = null;
    static int count = 0;   // eleman sayısı

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

    // Eleman sayısı
    static int size() {
        return count;
    }

    public static void main(String[] args) {
        // Başta 0
        System.out.println("size = " + size()); // 0
        // Ekle
        enqueue(10);
        enqueue(20);
        // Şimdi 2
        System.out.println("size = " + size()); // 2
    }
}
