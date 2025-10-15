public class PushStackDizi {
    // Sabit boyutlu yığın verisi (dizi)
    static int[] stack = new int[5];
    // Yığının tepesini tutar; boşken -1
    static int top = -1;

    // Yığına eleman ekleme (push)
    static void push(int x) {
        // Dizi dolu mu kontrolü
        if (top == stack.length - 1) {
            System.out.println("Stack dolu! (push iptal)");
            return;
        }
        // Önce top'u artır, sonra değeri yaz
        stack[++top] = x;
    }

    // Yığını ekranda yukarıdan aşağıya yazdırma
    static void print() {
        System.out.print("[");
        for (int i = top; i >= 0; i--) {
            System.out.print(stack[i]);
            if (i != 0) System.out.print(", ");
        }
        System.out.println("] (top)");
    }

    public static void main(String[] args) {
        // Örnek kullanım
        push(10); 
        push(20); 
        push(30);
        print(); // [30, 20, 10] (top)
    }
}
