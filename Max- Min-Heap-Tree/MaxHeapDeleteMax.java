public class MaxHeapDeleteMax {

    // ------------------ 1. HEAP TANIMI ------------------
    static int[] heap = new int[100]; // Max 100 elemanlık heap
    static int size = 0;              // Güncel eleman sayısı

    // ------------------ 2. YARDIMCI FONKSİYONLAR ------------------

    // Heap dizisini yazdır
    static void printHeapArray() {
        System.out.print("Heap (dizi): ");
        for (int i = 0; i < size; i++) {
            System.out.print(heap[i] + " ");
        }
        System.out.println();
    }

    // Heap'i ağaç şeklinde yazdır
    static void printHeapAsTree() {
        System.out.println("Heap (agac seklinde, kök yukarida):");

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
         Max Heap deleteMax işleminde:
         1) Kök silinir (heap[0])
         2) Son eleman köke taşınır
         3) heapifyDown ile aşağı doğru düzeltilir

         Sol çocuk = 2*i + 1
         Sağ çocuk = 2*i + 2
    */
    static void heapifyDown(int index) {
        while (true) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int largest = index;

            // Sol çocuk daha büyükse
            if (left < size && heap[left] > heap[largest]) {
                largest = left;
            }

            // Sağ çocuk daha büyükse
            if (right < size && heap[right] > heap[largest]) {
                largest = right;
            }

            // Eğer ebeveyn zaten en büyük ise işlem biter
            if (largest == index) break;

            // Değilse yer değiştir
            int temp = heap[index];
            heap[index] = heap[largest];
            heap[largest] = temp;

            // Aşağı doğru ilerle
            index = largest;
        }
    }

    // ------------------ 4. DELETE MAX ------------------
    static int deleteMax() {
        if (size == 0) {
            System.out.println("Heap bos, silme yapilamaz.");
            return -1;
        }

        System.out.println("==> Maksimum olan " + heap[0] + " siliniyor...");

        int maxValue = heap[0];

        // 1) Son elemanı köke koy
        heap[0] = heap[size - 1];
        size--;

        // 2) Aşağı doğru düzelt
        heapifyDown(0);

        printHeapArray();
        printHeapAsTree();

        return maxValue;
    }

    // ------------------ 5. INSERT (ÖNCEKİ DOSYA İLE AYNI) ------------------
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

    // ------------------ 6. MAIN ------------------
    public static void main(String[] args) {

        // Önce bir heap oluşturalım
        int[] sayilar = {50, 30, 70, 20, 40, 60, 80};
        for (int x : sayilar) insert(x);

        System.out.println("Ilk Heap:");
        printHeapArray();
        printHeapAsTree();

        // En büyük elemanı silelim
        deleteMax();
        deleteMax();
    }
}
