public class SizeKuyrukDizi {

    // Dairesel kuyruk (dizi ile)
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

    // Eleman sayısı
    static int size() {
        return size;
    }

    public static void main(String[] args) {
        // Başta 0
        System.out.println("size = " + size()); // 0
        // Ekle
        enqueue(1);
        enqueue(2);
        enqueue(3);
        // Şimdi 3
        System.out.println("size = " + size()); // 3
    }
}
