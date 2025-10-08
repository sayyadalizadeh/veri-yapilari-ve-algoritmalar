// CircularSinglyLinkedListOperations.java
// Tek yönlü dairesel bağlı liste üzerinde temel işlemler: ekleme, silme, arama, toplam ve ortalama

class CSNodeOp {
    int data;
    CSNodeOp next;

    CSNodeOp(int data) {
        this.data = data;
        this.next = null;
    }
}

public class CircularSinglyLinkedListOperations {
    CSNodeOp head;

    // Ekleme (sondan)
    public void add(int data) {
        CSNodeOp newNode = new CSNodeOp(data);
        if (head == null) {
            head = newNode;
            head.next = head;
        } else {
            CSNodeOp current = head;
            while (current.next != head) {
                current = current.next;
            }
            current.next = newNode;
            newNode.next = head;
        }
    }

    // Silme (belirli veri)
    public void delete(int data) {
        if (head == null) return;

        CSNodeOp current = head;
        CSNodeOp prev = null;

        // Silinecek eleman başta ise
        if (head.data == data) {
            // Tek eleman varsa
            if (head.next == head) {
                head = null;
                return;
            }
            // Birden fazla eleman varsa
            CSNodeOp tail = head;
            while (tail.next != head) {
                tail = tail.next;
            }
            head = head.next;
            tail.next = head;
            return;
        }

        prev = head;
        current = head.next;
        while (current != head && current.data != data) {
            prev = current;
            current = current.next;
        }

        if (current.data == data) {
            prev.next = current.next;
        }
    }

    // Arama
    public boolean search(int data) {
        if (head == null) return false;
        CSNodeOp current = head;
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
        CSNodeOp current = head;
        do {
            sum += current.data;
            count++;
            current = current.next;
        } while (current != head);
        return (double) sum / count;
    }

    // Listeyi yazdırma
    public void printList() {
        if (head == null) return;
        CSNodeOp current = head;
        int index = 0;
        System.out.println("Liste elemanları (dairesel, baş -> son):");
        do {
            System.out.println("Indeks " + index + ": " + current.data);
            current = current.next;
            index++;
        } while (current != head);
    }

    public static void main(String[] args) {
        CircularSinglyLinkedListOperations list = new CircularSinglyLinkedListOperations();

        // Eleman ekleme
        list.add(10);
        list.add(20);
        list.add(30);
        list.add(40);
        list.add(50);

        // Listeyi yazdır
        list.printList();

        // Eleman silme
        list.delete(30);
        System.out.println("30 silindikten sonra:");
        list.printList();

        // Arama
        System.out.println("20 listede var mı? " + list.search(20));
        System.out.println("30 listede var mı? " + list.search(30));

        // Toplam ve Ortalama
        System.out.println("Liste ortalaması: " + list.average());
    }
}
