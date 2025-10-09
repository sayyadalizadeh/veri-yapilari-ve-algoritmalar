// DoublyLinkedListOperations.java
// Çift yönlü bağlı liste üzerinde temel işlemler: ekleme, silme, arama, toplam ve ortalama

class DNodeOp {
    int data;
    DNodeOp prev;
    DNodeOp next;

    DNodeOp(int data) {
        this.data = data;
        this.prev = null;
        this.next = null;
    }
}

public class DoublyLinkedListOperations {
    DNodeOp head;
    DNodeOp tail;

    // Ekleme (sondan)
    public void add(int data) {
        DNodeOp newNode = new DNodeOp(data);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }

    // Silme (belirli veri)
    public void delete(int data) {
        if (head == null) return;
        DNodeOp current = head;

        while (current != null && current.data != data) {
            current = current.next;
        }

        if (current == null) return; // Bulunamadı

        if (current.prev != null)
            current.prev.next = current.next;
        else
            head = current.next; // baş eleman silindi

        if (current.next != null)
            current.next.prev = current.prev;
        else
            tail = current.prev; // son eleman silindi
    }

    // Arama
    public boolean search(int data) {
        DNodeOp current = head;
        while (current != null) {
            if (current.data == data) return true;
            current = current.next;
        }
        return false;
    }

    // Toplam ve Ortalama
    public double average() {
        if (head == null) return 0;
        int sum = 0;
        int count = 0;
        DNodeOp current = head;
        while (current != null) {
            sum += current.data;
            count++;
            current = current.next;
        }
        return (double) sum / count;
    }

    // Listeyi baştan sona yazdırma
    public void printListForward() {
        DNodeOp current = head;
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
        DNodeOp current = tail;
        int index = 0;
        System.out.println("Liste (son -> baş):");
        while (current != null) {
            System.out.println("Indeks " + index + ": " + current.data);
            current = current.prev;
            index++;
        }
    }

    public static void main(String[] args) {
        DoublyLinkedListOperations list = new DoublyLinkedListOperations();

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
