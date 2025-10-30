public class FrontKuyrukBagliListe {

    // Düğüm yapısı: değer ve sonraki işaretçi
    static class Node {
        int val;
        Node next;

        Node(int v) {
            val = v;
            next = null;
        }
    }

    // Kuyruk uçları: head (ön) ve tail (arka)
    static Node head = null;   // ilk eleman (front)
    static Node tail = null;   // son eleman (rear)
    static int size = 0;       // eleman sayısı

    // Kuyruğa ekleme (enqueue): sona ekle
    static void enqueue(int x) {
        // yeni düğüm oluştur
        Node n = new Node(x);

        // kuyruk boşsa head ve tail aynı düğüm olur
        if (head == null) {
            head = n;
            tail = n;
        } else {
            // boş değilse tail'in arkasına bağla
            tail.next = n;
            tail = n;
        }
        // sayaç artır
        size++;
    }

    // Baştaki elemanı gör (çıkarma yapmadan)
    static int frontValue() {
        // Boş mu kontrolü
        if (head == null) {
            throw new RuntimeException("Kuyruk bos! (front yapilamaz)");
        }
        // head üzerindeki değeri döndür
        return head.val;
    }

    // Kuyruğu yazdır (head -> tail)
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
        // Örnek: birkaç ekleme
        enqueue(5);
        enqueue(15);
        enqueue(25);
        print(); // [5, 15, 25]

        // Baştaki elemanı gör (çıkarma olmadan)
        int f = frontValue();
        System.out.println("front -> " + f); // 5

        // Birkaç ekleme daha
        enqueue(35);
        enqueue(45);
        print();
        System.out.println("front -> " + frontValue()); // 5 (hala en önde)
    }
}
