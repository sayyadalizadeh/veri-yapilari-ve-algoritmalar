// CircularDoublyLinkedList.java
// Çift yönlü dairesel bağlı liste örneği
// Basit ekleme, gösterme ve eleman sayısı işlemleri

class CDNode {
    int data;
    CDNode prev;
    CDNode next;

    CDNode(int data) {
        this.data = data;
        this.prev = null;
        this.next = null;
    }
}

public class CircularDoublyLinkedList {
    CDNode head;

    // Listeye eleman ekleme (sondan)
    public void add(int data) {
        CDNode newNode = new CDNode(data);
        if (head == null) {
            head = newNode;
            head.next = head;
            head.prev = head;
        } else {
            CDNode tail = head.prev;

            tail.next = newNode;
            newNode.prev = tail;
            newNode.next = head;
            head.prev = newNode;
        }
    }

    // Listeyi baştan sona yazdırma
    public void printListForward() {
        if (head == null) return;
        CDNode current = head;
        int index = 0;
        System.out.println("Liste (baş -> son):");
        do {
            System.out.println("Indeks " + index + ": " + current.data);
            current = current.next;
            index++;
        } while (current != head);
    }

    // Listeyi sondan başa yazdırma
    public void printListBackward() {
        if (head == null) return;
        CDNode current = head.prev;
        int index = 0;
        System.out.println("Liste (son -> baş):");
        do {
            System.out.println("Indeks " + index + ": " + current.data);
            current = current.prev;
            index++;
        } while (current != head.prev);
    }

    // Eleman sayısını hesaplama
    public int size() {
        if (head == null) return 0;
        int count = 0;
        CDNode current = head;
        do {
            count++;
            current = current.next;
        } while (current != head);
        return count;
    }

    // Main metodu ile örnek kullanım
    public static void main(String[] args) {
        CircularDoublyLinkedList list = new CircularDoublyLinkedList();

        // Listeye eleman ekleme
        list.add(10);
        list.add(20);
        list.add(30);
        list.add(40);
        list.add(50);

        // Listeyi yazdır
        list.printListForward();
        list.printListBackward();

        System.out.println("Liste boyutu: " + list.size());
    }
}
