// SinglyLinkedListOperations.java
// Tek yönlü bağlı liste üzerinde temel işlemler: ekleme, silme, arama, toplam ve ortalama

class SNodeOp {
    int data;
    SNodeOp next;

    SNodeOp(int data) {
        this.data = data;
        this.next = null;
    }
}

public class SinglyLinkedListOperations {
    SNodeOp head;

    // Ekleme (sondan)
    public void add(int data) {
        SNodeOp newNode = new SNodeOp(data);
        if (head == null) {
            head = newNode;
        } else {
            SNodeOp current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    // Silme (belirli veri)
    public void delete(int data) {
        if (head == null) return;
        if (head.data == data) {
            head = head.next;
            return;
        }
        SNodeOp current = head;
        while (current.next != null && current.next.data != data) {
            current = current.next;
        }
        if (current.next != null) {
            current.next = current.next.next;
        }
    }

    // Arama
    public boolean search(int data) {
        SNodeOp current = head;
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
        SNodeOp current = head;
        while (current != null) {
            sum += current.data;
            count++;
            current = current.next;
        }
        return (double) sum / count;
    }

    // Listeyi yazdırma
    public void printList() {
        SNodeOp current = head;
        int index = 0;
        System.out.println("Liste elemanları:");
        while (current != null) {
            System.out.println("Indeks " + index + ": " + current.data);
            current = current.next;
            index++;
        }
    }

    public static void main(String[] args) {
        SinglyLinkedListOperations list = new SinglyLinkedListOperations();

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
