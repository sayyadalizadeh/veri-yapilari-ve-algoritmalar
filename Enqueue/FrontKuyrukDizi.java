public class FrontKuyrukDizi {

    // Sabit kapasiteli dairesel kuyruk (dizi ile)
    static int CAP = 5;           // kapasite
    static int[] q = new int[CAP];
    static int front = 0;         // ilk elemanın indisi
    static int size = 0;          // eleman sayısı
    static int rear = -1;         // son eklenenin indisi

    // Kuyruğa ekleme (enqueue): sona ekler
    static void enqueue(int x) {
        // Dolu mu kontrolü
        if (size == CAP) {
            System.out.println("Kuyruk dolu! (enqueue iptal)");
            return;
        }
        // rear'i dairesel olarak bir adım ileri taşı
        rear = (rear + 1) % CAP;
        // değeri yerleştir
        q[rear] = x;
        // eleman sayısını artır
        size++;
    }

    // Baştaki elemanı gör (çıkarma yapmadan) - "front/peek"
    static int frontValue() {
        // Boş mu? boşsa hata ver
        if (size == 0) {
            throw new RuntimeException("Kuyruk bos! (front yapilamaz)");
        }
        // front indeksindeki değeri döndür
        return q[front];
    }

    // Kuyruğu yazdır (front -> rear)
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
        // Örnek: birkaç eleman ekle
        enqueue(10);
        enqueue(20);
        enqueue(30);
        print(); // Kuyruk: [10, 20, 30]

        // Baştaki elemanı gör (çıkarma olmadan)
        int f = frontValue();
        System.out.println("front -> " + f); // 10

        // Birkaç ekleme daha
        enqueue(40);
        enqueue(50);
        print();
        System.out.println("front -> " + frontValue()); // 10 (hala en öndeki)
    }
}
