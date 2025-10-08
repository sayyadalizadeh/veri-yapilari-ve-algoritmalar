// CircularDoublyLinkedListOperations.java
// Çift yönlü dairesel bağlı liste üzerinde temel işlemler: ekleme, silme, arama, toplam ve ortalama

class CDNodeOp {
    int data;
    CDNodeOp prev;
    CDNodeOp next;

    CDNodeOp(int data) {
        this.data = data;
        this.prev = this.next = null;
    }
}

public class CircularDoublyLinkedListOperations {
    CDNodeOp head;

    // Ekleme (sondan)
    public void add(int data) {
        CDNodeOp newNode = new CDNodeOp(data);
        if (head == null) {
            head = newNode;
            head.next = head;
            head.prev = head;
        } else {
            CDNodeOp tail = head.prev;
            tail.next = newNode;
            newNode.prev = tail;
            newNode.next = head;
            head.prev = newNode;
        }
    }

    // Silme (belirli veri)
    public void delete(int data) {
        if (head == null) return;

        CDNodeOp current = head;

        do {
            if (current.data == data) {
                // Tek eleman
                if (current.next == current) {
                    head = null;
                    return;
                }

                current.prev.next = current.next;
                current.next.prev = current.prev;

                if (current == head) head = current.next;

                return;
            }
            current = current.next;
        } while (current != head);
    }

    // Arama
    public boolean search(int data) {
        if (head == null) return false;
        CDNodeOp current = head;
        do {
            if (current.data == data) return true;
            current = current.next;
        } while (current != head);
        return false;
    }

    // Toplam ve Ortalama
    public double average() {
        if (head == null) return 0;
        int sum = 0;
        int count = 0;
        CDNodeOp current = head;
        do {
            sum += current.data;
            count++;
            current = current.next;
        } while (current != head);
        return (double) sum / count;
    }

    // Listeyi baştan sona yazdırma
    public void printListForward() {
        if (head == null) return;
        CDNodeOp current = head;
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
        CDNodeOp current = head.prev; // tail
        int index = 0;
        System.out.println("Liste (son -> baş):");
        do {
            System.out.println("Indeks " + index + ": " + current.data);
            current = current.prev;
            index++;
        } while (current != head.prev);
    }

    public static void main(String[] args) {
        CircularDoublyLinkedListOperations list = new CircularDoublyLinkedListOperations();

        // Eleman ekleme
        list.add(10);
        list.add(20);
        list.add(30);
        list.add(40);
        list.add(50);

        // Listeyi yazdır
        list.printListForward();
        list.printListBackward();

        // Eleman silme
        list.delete(30);
        System.out.println("30 silindikten sonra:");
        list.printListForward();

        // Arama
        System.out.println("20 listede var mı? " + list.search(20));
        System.out.println("30 listede var mı? " + list.search(30));

        // Toplam ve Ortalama
        System.out.println("Liste ortalaması: " + list.average());
    }
}
