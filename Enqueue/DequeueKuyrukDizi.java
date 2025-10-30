public class DequeueKuyrukDizi {

    // Sabit kapasiteli dairesel kuyruk
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
        // rear'i ileri al (dairesel)
        rear = (rear + 1) % CAP;
        // değeri yaz
        q[rear] = x;
        // sayıyı artır
        size++;
    }

    // Kuyruktan çıkarma (dequeue): baştaki elemanı çıkarır ve döndürür
    static int dequeue() {
        // Boş mu kontrolü
        if (size == 0) {
            throw new RuntimeException("Kuyruk bos! (dequeue yapilamaz)");
        }
        // baştaki değeri al
        int val = q[front];
        // front'u bir adım ileri taşı (dairesel)
        front = (front + 1) % CAP;
        // eleman sayısını azalt
        size--;
        // değeri döndür
        return val;
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
        // Örnek akış: ekle → çıkar → yaz
        enqueue(10);
        enqueue(20);
        enqueue(30);
        print(); // Kuyruk: [10, 20, 30]

        // Baştan bir eleman çıkar
        int x = dequeue();
        System.out.println("dequeue -> " + x); // 10
        print(); // Kuyruk: [20, 30]

        // Dairesel davranışı görelim
        enqueue(40);
        enqueue(50);
        print(); // [20, 30, 40, 50]
        System.out.println("dequeue -> " + dequeue()); // 20
        print();
    }
}
