public class IsEmptyKuyrukDizi {

    // Dairesel kuyruk (dizi ile)
    static int CAP = 5;           // kapasite
    static int[] q = new int[CAP];
    static int front = 0;         // ilk elemanın indisi
    static int size = 0;          // eleman sayısı
    static int rear = -1;         // son eklenenin indisi

    // Kuyruğa ekleme (enqueue)
    static void enqueue(int x) {
        if (size == CAP) {
            System.out.println("Kuyruk dolu!");
            return;
        }
        rear = (rear + 1) % CAP;
        q[rear] = x;
        size++;
    }

    // Kuyruk boş mu?
    static boolean isEmpty() {
        return size == 0;
    }

    public static void main(String[] args) {
        // Başta boş olmalı
        System.out.println("bos? " + isEmpty()); // true
        // Birkaç eleman ekle
        enqueue(10);
        enqueue(20);
        // Artık boş değil
        System.out.println("bos? " + isEmpty()); // false
    }
}
