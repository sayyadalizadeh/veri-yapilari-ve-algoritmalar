// DoublyLinkedList.java
// Çift yönlü bağlı liste örneği
// Basit ekleme, gösterme ve eleman sayısı işlemleri

class DNode {
    int data;
    DNode prev;
    DNode next;

    DNode(int data) {
        this.data = data;
        this.prev = null;
        this.next = null;
    }
}

public class DoublyLinkedList {
    DNode head;
    DNode tail;

    // Listeye eleman ekleme (sondan)
    public void add(int data) {
        DNode newNode = new DNode(data);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }

    // Listeyi baştan sona yazdırma
    public void printListForward() {
        DNode current = head;
        int index = 0;
        System.out.println("Liste (baş -> son):");
        while (current != null) {
            System.out.println("Indeks " + index + ": " + current.data);
            current = current.next;
            index++;
        }
    }

    // Listeyi sondan başa yazdırma
    public void printListBackward() {
        DNode current = tail;
        int index = 0;
        System.out.println("Liste (son -> baş):");
        while (current != null) {
            System.out.println("Indeks " + index + ": " + current.data);
            current = current.prev;
            index++;
        }
    }

    // Eleman sayısını hesaplama
    public int size() {
        int count = 0;
        DNode current = head;
        while (current != null) {
            count++;
            current = current.next;
        }
        return count;
    }

    // Main metodu ile örnek kullanım
    public static void main(String[] args) {
        DoublyLinkedList list = new DoublyLinkedList();

        // Listeye eleman ekleme
        list.add(10);
        list.add(20);
        list.add(30);
        list.add(40);
        list.add(50);

        // Listeyi ekrana yazdır
        list.printListForward();
        list.printListBackward();

        System.out.println("Liste boyutu: " + list.size());
    }
}
