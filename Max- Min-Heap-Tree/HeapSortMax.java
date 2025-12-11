public class HeapSortMax {

    // ------------------ 1. DİZİ YAZDIRMA YARDIMCISI ------------------
    static void printArray(int[] arr, String msg) {
        System.out.print(msg + " ");
        for (int x : arr) {
            System.out.print(x + " ");
        }
        System.out.println();
    }

    // ------------------ 2. HEAPIFY DOWN (MAX HEAP İÇİN) ------------------
    /*
        heapifyDown(arr, heapSize, i):

        - i: ebeveyn indisi
        - left  = 2*i + 1
        - right = 2*i + 2
        - parent, left, right arasından EN BÜYÜĞÜ bul
        - en büyük parent değilse yer değiştir, o çocuk için devam et
     */
    static void heapifyDown(int[] arr, int heapSize, int i) {
        while (true) {
            int left  = 2 * i + 1;
            int right = 2 * i + 2;
            int largest = i;

            // Sol çocuk daha büyükse
            if (left < heapSize && arr[left] > arr[largest]) {
                largest = left;
            }
            // Sağ çocuk daha büyükse
            if (right < heapSize && arr[right] > arr[largest]) {
                largest = right;
            }

            // Parent zaten en büyükse dur
            if (largest == i) break;

            // Değilse parent ile en büyük çocuğu yer değiştir
            int temp = arr[i];
            arr[i] = arr[largest];
            arr[largest] = temp;

            // Aşağı ilerlemeye devam et
            i = largest;
        }
    }

    // ------------------ 3. MAX HEAP KURMA (buildMaxHeap) ------------------
    /*
        Diziyi O(n) zamanda Max Heap’e çevirir:

            - Yapraklar zaten heap’tir
            - Son ebeveynden (n/2 - 1) başlar, 0'a kadar heapifyDown uygularız
     */
    static void buildMaxHeap(int[] arr) {
        int n = arr.length;

        for (int i = n / 2 - 1; i >= 0; i--) {
            heapifyDown(arr, n, i);
        }
    }

    // ------------------ 4. HEAP SORT (MAX HEAP İLE) ------------------
    /*
        HEAP SORT (Max Heap ile) – SIRALAMA (KÜÇÜKTEN BÜYÜĞE):

        1) Dizinin tamamını Max Heap’e çevir (buildMaxHeap)
        2) En büyük eleman hep kökte (arr[0]) durur.
        3) arr[0] ile arr[heapSize-1] yer değiştir:
               - sondaki yer artık "doğru" konumda (en büyük)
               - heapSize-- (heap küçülür)
               - kökten itibaren heapifyDown ile düzenle
        4) heapSize 1 olana kadar devam et.
     */
    static void heapSort(int[] arr) {
        int n = arr.length;

        // 1) Önce Max Heap oluştur
        buildMaxHeap(arr);

        // 2) En büyük elemanı sona at, heapSize'i azalt, düzelt
        for (int heapSize = n; heapSize > 1; heapSize--) {
            // Kökteki (en büyük) ile son elemanı yer değiştir
            int temp = arr[0];
            arr[0] = arr[heapSize - 1];
            arr[heapSize - 1] = temp;

            // Kalan kısım için heapifyDown (0..heapSize-2 arası heap)
            heapifyDown(arr, heapSize - 1, 0);
        }
    }

    // ------------------ 5. MAIN ------------------
    public static void main(String[] args) {

        int[] dizi = {20, 5, 30, 15, 10, 40, 25};

        printArray(dizi, "Baslangic dizisi:");

        // İstersek önce heap haline dönmüş halini gösterebiliriz:
        int[] kopya = dizi.clone();
        buildMaxHeap(kopya);
        printArray(kopya, "Max Heap (buildMaxHeap sonrasi):");

        // Asıl diziyi Heap Sort ile sırala
        heapSort(dizi);
        printArray(dizi, "Heap Sort (kucukten buyuge siralanmis):");
    }
}
