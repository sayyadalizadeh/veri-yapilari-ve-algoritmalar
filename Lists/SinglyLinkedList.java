// SinglyLinkedList.java
// Tek yönlü bağlı liste örneği
// Basit ekleme, gösterme ve eleman sayısı işlemleri

class Node {
    int data;
    Node next;

    Node(int data) {
        this.data = data;
        this.next = null;
    }
}

public class SinglyLinkedList {
    Node head;

    // Listeye eleman ekleme (sondan)
    public void add(int data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    // Listeyi ekrana yazdırma
    public void printList() {
        Node current = head;
        int index = 0;
        while (current != null) {
            System.out.println("Indeks " + index + ": " + current.data);
            current = current.next;
            index++;
        }
    }

    // Eleman sayısını hesaplama
    public int size() {
        int count = 0;
        Node current = head;
        while (current != null) {
            count++;
            current = current.next;
        }
        return count;
    }

    // Main metodu ile örnek kullanım
    public static void main(String[] args) {
        SinglyLinkedList list = new SinglyLinkedList();

        // Listeye eleman ekleme
        list.add(10);
        list.add(20);
        list.add(30);
        list.add(40);
        list.add(50);

        System.out.println("Liste elemanları:");
        list.printList();

        System.out.println("Liste boyutu: " + list.size());
    }
}
