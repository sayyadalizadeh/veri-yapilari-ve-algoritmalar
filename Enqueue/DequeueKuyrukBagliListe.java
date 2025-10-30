public class DequeueKuyrukBagliListe {

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
    static Node head = null;   // ilk eleman
    static Node tail = null;   // son eleman
    static int size = 0;       // eleman sayısı

    // Kuyruğa ekleme (enqueue): sona bağla
    static void enqueue(int x) {
        // yeni düğüm oluştur
        Node n = new Node(x);

        // boşsa hem head hem tail bu düğüm
        if (head == null) {
            head = n;
            tail = n;
        } else {
            // mevcut tail'in arkasına yeni düğümü bağla
            tail.next = n;
            tail = n;
        }
        // sayaç artır
        size++;
    }

    // Kuyruktan çıkarma (dequeue): baştaki düğümü çıkar ve değeri döndür
    static int dequeue() {
        // Boş mu kontrolü
        if (head == null) {
            throw new RuntimeException("Kuyruk bos! (dequeue yapilamaz)");
        }
        // baştaki değeri al
        int val = head.val;
        // head'i bir sonraki düğüme kaydır
        head = head.next;
        // eğer boş kaldıysa tail'i de sıfırla
        if (head == null) {
            tail = null;
        }
        // sayaç azalt
        size--;
        // değeri döndür
        return val;
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
        // Örnek akış: birkaç ekleme ve çıkarma
        enqueue(5);
        enqueue(15);
        enqueue(25);
        print(); // [5, 15, 25]

        // Baştan bir eleman çıkar
        int x = dequeue();
        System.out.println("dequeue -> " + x); // 5
        print(); // [15, 25]

        // İki eleman daha ekle
        enqueue(35);
        enqueue(45);
        print(); // [15, 25, 35, 45]

        // Hepsini sırayla boşaltalım
        System.out.println("dequeue -> " + dequeue()); // 15
        System.out.println("dequeue -> " + dequeue()); // 25
        System.out.println("dequeue -> " + dequeue()); // 35
        System.out.println("dequeue -> " + dequeue()); // 45
        print(); // []
    }
}
