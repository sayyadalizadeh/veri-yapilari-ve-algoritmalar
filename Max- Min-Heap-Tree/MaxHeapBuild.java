public class MaxHeapBuild {

    // ------------------ 1. HEAP TANIMI ------------------
    static int[] heap = new int[100];
    static int size = 0;

    // ------------------ 2. YARDIMCI FONKSİYONLAR ------------------

    // Heap dizisini yazdır
    static void printHeapArray(String msg) {
        System.out.print(msg + " ");
        for (int i = 0; i < size; i++) {
            System.out.print(heap[i] + " ");
        }
        System.out.println();
    }

    // Heap'i ağaç biçiminde yazdır
    static void printHeapAsTree(String title) {
        System.out.println(title);

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

    // ------------------ 3. HEAPIFY DOWN ------------------
    static void heapifyDown(int index) {
        while (true) {

            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int largest = index;

            if (left < size && heap[left] > heap[largest]) {
                largest = left;
            }

            if (right < size && heap[right] > heap[largest]) {
                largest = right;
            }

            if (largest == index) break;

            int temp = heap[index];
            heap[index] = heap[largest];
            heap[largest] = temp;

            index = largest;
        }
    }

    // ------------------ 4. TEK TEK INSERT İLE HEAP KURMA ------------------
    static void heapifyUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;

            if (heap[parentIndex] >= heap[index]) break;

            int temp = heap[parentIndex];
            heap[parentIndex] = heap[index];
            heap[index] = temp;

            index = parentIndex;
        }
    }

    static void insert(int x) {
        heap[size] = x;
        size++;
        heapifyUp(size - 1);
    }

    // ------------------ 5. BOTTOM-UP İLE HEAP KURMA ------------------
    /*
        buildMaxHeap işlemi:

            - Yapraklar zaten heap'tir
            - Son ebeveynden (size/2 - 1) başlayarak
              her düğüme heapifyDown uygulanır.

        Bu yöntem: O(n)
        Tek tek insert yöntemi: O(n log n)
     */
    static void buildMaxHeap() {
        for (int i = size / 2 - 1; i >= 0; i--) {
            heapifyDown(i);
        }
    }

    // ------------------ 6. MAIN ------------------
    public static void main(String[] args) {

        int[] dizi = {20, 5, 30, 15, 10, 40, 25};

        // --------------------------------------------------------------
        // 1) TEK TEK INSERT YÖNTEMİ İLE HEAP KURMA
        // --------------------------------------------------------------
        System.out.println("=== 1) Tek tek insert ile Max Heap kurma ===");

        size = 0; // sıfırdan başlat
        for (int x : dizi) {
            insert(x);
            printHeapArray("Eklendi " + x + " -> ");
        }

        printHeapAsTree("Tek tek insert sonrası Max Heap ağacı:");

        // --------------------------------------------------------------
        // 2) BOTTOM-UP HEAP BUILD (DAHA HIZLI YÖNTEM)
        // --------------------------------------------------------------
        System.out.println("=== 2) Bottom-Up buildMaxHeap() ile Max Heap kurma ===");

        // Diziyi doğrudan heap'e kopyala ve size ayarla
        heap = new int[100];
        size = dizi.length;
        for (int i = 0; i < dizi.length; i++) {
            heap[i] = dizi[i];
        }

        System.out.println("Heapify'dan önce (dizi hali):");
        printHeapArray("Dizi:");

        buildMaxHeap();

        System.out.println("\nHeapify'dan sonra:");
        printHeapArray("Max Heap:");
        printHeapAsTree("Bottom-Up yöntemi ile oluşan Max Heap ağacı:");
    }
}
