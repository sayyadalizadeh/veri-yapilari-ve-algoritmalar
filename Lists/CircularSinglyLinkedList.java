// CircularSinglyLinkedList.java
// Tek yönlü dairesel bağlı liste örneği
// Basit ekleme, gösterme ve eleman sayısı işlemleri

class CNode {
    int data;
    CNode next;

    CNode(int data) {
        this.data = data;
        this.next = null;
    }
}

public class CircularSinglyLinkedList {
    CNode head;

    // Listeye eleman ekleme (sondan)
    public void add(int data) {
        CNode newNode = new CNode(data);
        if (head == null) {
            head = newNode;
            head.next = head; // Dairesel bağlantı
        } else {
            CNode current = head;
            while (current.next != head) {
                current = current.next;
            }
            current.next = newNode;
            newNode.next = head;
        }
    }

    // Listeyi yazdırma
    public void printList() {
        if (head == null) return;
        CNode current = head;
        int index = 0;
        System.out.println("Dairesel Liste elemanları:");
        do {
            System.out.println("Indeks " + index + ": " + current.data);
            current = current.next;
            index++;
        } while (current != head);
    }

    // Eleman sayısını hesaplama
    public int size() {
        if (head == null) return 0;
        int count = 0;
        CNode current = head;
        do {
            count++;
            current = current.next;
        } while (current != head);
        return count;
    }

    // Main metodu ile örnek kullanım
    public static void main(String[] args) {
        CircularSinglyLinkedList list = new CircularSinglyLinkedList();

        // Listeye eleman ekleme
        list.add(10);
        list.add(20);
        list.add(30);
        list.add(40);
        list.add(50);

        // Listeyi ekrana yazdır
        list.printList();

        System.out.println("Liste boyutu: " + list.size());
    }
}
