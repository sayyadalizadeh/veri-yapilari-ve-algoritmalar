public class ClearKuyrukDizi {

    // Dairesel kuyruk (dizi)
    static int CAP = 5;
    static int[] q = new int[CAP];
    static int front = 0;
    static int size = 0;
    static int rear = -1;

    // Kuyruğa ekleme
    static void enqueue(int x) {
        if (size == CAP) {
            System.out.println("Kuyruk dolu!");
            return;
        }
        rear = (rear + 1) % CAP;
        q[rear] = x;
        size++;
    }

    // Temizle
    static void clear() {
        // Sadece sayacı ve indeksleri sıfırlamak yeterli
        front = 0;
        rear = -1;
        size = 0;
    }

    // Eleman sayısı (kontrol için)
    static int size() {
        return size;
    }

    public static void main(String[] args) {
        // Ekle
        enqueue(11);
        enqueue(22);
        enqueue(33);
        System.out.println("size (once)  = " + size()); // 3
        // Temizle
        clear();
        System.out.println("size (sonra) = " + size()); // 0
    }
}
