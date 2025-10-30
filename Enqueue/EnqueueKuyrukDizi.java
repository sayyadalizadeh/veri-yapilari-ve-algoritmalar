public class EnqueueKuyrukDizi {

    // Sabit kapasiteli kuyruk (dizi ile)
    static int CAP = 5;          // kapasite
    static int[] q = new int[CAP];
    static int front = 0;        // ilk elemanın indisi
    static int size = 0;         // eleman sayısı
    static int rear = -1;        // son eklenenin indisi

    // Kuyruğa ekleme (enqueue): sona ekler
    static void enqueue(int x) {
        // Kuyruk dolu mu?
        if (size == CAP) {
            System.out.println("Kuyruk dolu! (enqueue iptal)");
            return;
        }
        // rear'i bir adım ileri taşı (dairesel mod)
        rear = (rear + 1) % CAP;
        // değeri yerleştir
        q[rear] = x;
        // eleman sayısını artır
        size++;
    }

    // Kuyruğu ekrana yaz (front -> rear sırası ile)
    static void print() {
        System.out.print("Kuyruk: [");
        for (int i = 0; i < size; i++) {
            int idx = (front + i) % CAP;
            System.out.print(q[idx]);
            if (i != size - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]  (front=" + front + ", rear=" + rear + ", size=" + size + ")");
    }

    public static void main(String[] args) {
        // Örnek: birkaç eleman ekleyip yazdıralım
        enqueue(10);
        enqueue(20);
        enqueue(30);
        print(); // Kuyruk: [10, 20, 30] ...

        enqueue(40);
        enqueue(50);
        print(); // Kuyruk: [10, 20, 30, 40, 50] ...

        // Dolu iken bir ekleme daha deneyelim
        enqueue(60); // "Kuyruk dolu!" uyarısı
        print();
    }
}
