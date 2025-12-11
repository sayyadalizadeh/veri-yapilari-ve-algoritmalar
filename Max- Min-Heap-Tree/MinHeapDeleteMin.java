public class MinHeapDeleteMin {

    // ------------------ 1. HEAP TANIMI ------------------
    static int[] heap = new int[100];
    static int size = 0;

    // ------------------ 2. YARDIMCI FONKSİYONLAR ------------------

    static void printHeapArray() {
        System.out.print("Heap (dizi): ");
        for (int i = 0; i < size; i++) {
            System.out.print(heap[i] + " ");
        }
        System.out.println();
    }

    static void printHeapAsTree() {
        System.out.println("Heap (ağaç şeklinde, kök yukarıda):");

        int index = 0;
        int level = 0;
        int elementsInLevel = 1;

        while (index < size) {
            System.out.print("Seviye " + level + ": ");
            int count = 0;

            while (index < size && count < elementsInLevel) {
                System.out.print(heap[index] + " ");
                index++;
                count++;
            }

            System.out.println();
            level++;
            elementsInLevel *= 2;
        }
        System.out.println();
    }

    // ------------------ 3. HEAPIFY DOWN (AŞAĞI İNDİRME) ------------------
    /*
        Min Heap deleteMin işleminde:

        1) Kökteki en küçük eleman silinir.
        2) Son eleman köke taşınır.
        3) heapifyDown ile aşağı doğru düzenlenir.

        Min Heap kuralı:
           parent <= child
     */
    static void heapifyDown(int index) {

        while (true) {
            int left  = 2 * index + 1;
            int right = 2 * index + 2;
            int smallest = index;

            // Sol çocuk küçükse
            if (left < size && heap[left] < heap[smallest]) {
                smallest = left;
            }

            // Sağ çocuk daha küçükse
            if (right < size && heap[right] < heap[smallest]) {
                smallest = right;
            }

            // Zaten doğru pozisyondaysa dur
            if (smallest == index) break;

            // Değilse yer değiştir
            int temp = heap[index];
            heap[index] = heap[smallest];
            heap[smallest] = temp;

            index = smallest; // aşağı inmeye devam
        }
    }

    // ------------------ 4. DELETE MIN ------------------
    static int deleteMin() {
        if (size == 0) {
            System.out.println("Heap boş, silme yapilamiyor.");
            return -1;
        }

        System.out.println("==> Min eleman (kök) olan " + heap[0] + " siliniyor...");

        int minValue = heap[0];

        // 1) Son elemanı köke al
        heap[0] = heap[size - 1];
        size--;

        // 2) Min Heap düzenini sağla
        heapifyDown(0);

        printHeapArray();
        printHeapAsTree();

        return minValue;
    }

    // ------------------ 5. INSERT (Min Heap Insert) ------------------
    static void heapifyUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;

            if (heap[parent] <= heap[index]) break;

            int temp = heap[parent];
            heap[parent] = heap[index];
            heap[index] = temp;

            index = parent;
        }
    }

    static void insert(int x) {
        heap[size] = x;
        size++;
        heapifyUp(size - 1);
    }

    // ------------------ 6. MAIN ------------------
    public static void main(String[] args) {

        // Önce Min Heap oluşturalım
        int[] sayilar = {50, 30, 70, 20, 40, 60, 10};

        for (int x : sayilar) {
            insert(x);
        }

        System.out.println("Başlangıç Min Heap:");
        printHeapArray();
        printHeapAsTree();

        // En küçük elemanı silelim
        deleteMin();
        deleteMin();
    }
}
