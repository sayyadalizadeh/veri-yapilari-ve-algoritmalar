public class MinHeapInsert {

    // ------------------ 1. HEAP TANIMI ------------------
    // Dizi tabanlı Min Heap: heap[0] kök (EN KÜÇÜK)
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
            elementsInLevel *= 2; // sonraki seviyede eleman sayısı 2 katına çıkar
        }

        System.out.println();
    }

    // ------------------ 3. HEAPIFY UP (YUKARI ÇIKARMA) ------------------
    /*
        Min Heap’te yeni eleman eklerken:

        1) Elemanı dizinin sonuna koyarız (heap[size])
        2) Parent’i ile karşılaştırırız.
        3) Eğer parent > child ise yer değiştiririz.
        4) Kök’e kadar devam ederiz.

        parent(i) = (i - 1) / 2
     */
    static void heapifyUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;

            // Min Heap kuralı: parent <= child olmalı
            // Eğer parent zaten küçük veya eşitse, dur.
            if (heap[parentIndex] <= heap[index]) {
                break;
            }

            // Değilse, yer değiştir (küçük olan yukarı çıkmalı)
            int temp = heap[parentIndex];
            heap[parentIndex] = heap[index];
            heap[index] = temp;

            // Şimdi yukarı çıktık, bir üst seviyeye devam et
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
        // 3) Yukarı çıkar (Min Heap kuralını sağla)
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
        insert(10);  // en küçük bu, köke çıkmalı
    }
}
