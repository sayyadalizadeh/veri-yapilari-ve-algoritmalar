public class HanoiRecursive {

    // n diski A çubuğundan C çubuğuna, B yardımcı çubuk ile taşı
    static void hanoi(int n, char from, char aux, char to) {
        // Taban durum: 1 disk varsa doğrudan taşı
        if (n == 1) {
            System.out.println(from + " -> " + to);
            return;
        }
        // 1) n-1 diski from'dan aux'a taşı (to yardımcı)
        hanoi(n - 1, from, to, aux);
        // 2) en büyük diski from'dan to'ya taşı
        System.out.println(from + " -> " + to);
        // 3) n-1 diski aux'tan to'ya taşı (from yardımcı)
        hanoi(n - 1, aux, from, to);
    }

    public static void main(String[] args) {
        int n = 3; // disk sayısı
        hanoi(n, 'A', 'B', 'C');
        // Örnek çıktı (n=3):
        // A -> C
        // A -> B
        // C -> B
        // A -> C
        // B -> A
        // B -> C
        // A -> C
    }
}
