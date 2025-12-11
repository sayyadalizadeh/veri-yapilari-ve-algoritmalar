public class MaxHeapInsert {

    // ------------------ 1. HEAP TANIMI ------------------
    // Dizi tabanlı Max Heap: heap[0] kök (en büyük)
    static int[] heap = new int[100]; // örnek: en fazla 100 eleman
    static int size = 0;              // şu anki eleman sayısı

    // ------------------ 2. YARDIMCI FONKSİYONLAR ------------------

    // Heap'in dizi halini yazdır
    static void printHeapArray() {
        System.out.print("Heap (dizi): ");
        for (int i = 0; i < size; i++) {
            System.out.print(heap[i] + " ");
        }
        System.out.println();
    }

    // Heap'i seviye seviye (ağaç gibi) yazdır
    static void printHeapAsTree() {
        System.out.println("Heap (agac seklinde, kök yukarida):");

        int index = 0;
        int level = 0;
        int elementsInLevel = 1; // 1, 2, 4, 8, ...

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
            elementsInLevel *= 2; // bir sonraki seviyede eleman sayısı 2 katına çıkar
        }

        System.out.println();
    }

    // ------------------ 3. HEAPIFY UP (YUKARI ÇIKARMA) ------------------
    /*
        Yeni eleman diziye en sona eklenir (size indexine).
        Sonra:
           - ebeveyni ile karşılaştırılır
           - büyükse yer değiştirilir
           - köke kadar bu işleme devam edilir

        parent(i) = (i - 1) / 2
     */
    static void heapifyUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;

            // Eğer parent daha büyük veya eşitse, heap kuralı sağlanmıştır
            if (heap[parentIndex] >= heap[index]) {
                break;
            }

            // Değilse, yer değiştir (Max Heap kuralı: parent >= çocuk)
            int temp = heap[parentIndex];
            heap[parentIndex] = heap[index];
            heap[index] = temp;

            // Şimdi parent konumuna çıktık, devam et
            index = parentIndex;
        }
    }

    // ------------------ 4. INSERT İŞLEMİ ------------------
    /*
        1) Yeni elemanı dizinin SONUNA koy (heap[size] = x)
        2) size++ (eleman sayısını artır)
        3) heapifyUp ile yukarı doğru düzelt
     */
    static void insert(int x) {
        if (size == heap.length) {
            System.out.println("Heap dolu, ekleme yapilamadi.");
            return;
        }

        System.out.println("==> " + x + " ekleniyor...");

        // 1) Sona koy
        heap[size] = x;
        // 2) Eleman sayısını artır
        size++;
        // 3) Yukarı çıkar
        heapifyUp(size - 1);

        // Sonuçları göster
        printHeapArray();
        printHeapAsTree();
    }

    // ------------------ 5. MAIN ------------------
    public static void main(String[] args) {

        // Örnek birkaç ekleme yapalım
        insert(50);
        insert(30);
        insert(70);
        insert(20);
        insert(40);
        insert(60);
        insert(80);
    }
}
