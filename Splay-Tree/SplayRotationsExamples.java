import java.util.ArrayList;
import java.util.List;

public class SplayRotationsExamples {

    // ---------------- 1. DÜĞÜM SINIFI ----------------
    static class Node {
        int key;
        Node left;
        Node right;

        Node(int k) {
            key = k;
            left = null;
            right = null;
        }
    }

    // ---------------- 2. ROTASYONLAR ----------------

    static Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        return x;
    }

    static Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        return y;
    }

    // ---------------- 3. AĞAÇ ÇİZİMİ ----------------
    static int height(Node n) {
        if (n == null) return 0;
        return 1 + Math.max(height(n.left), height(n.right));
    }

    static void printSpaces(int n) {
        for (int i = 0; i < n; i++) System.out.print(" ");
    }

    static void printTreePretty(Node root) {
        if (root == null) {
            System.out.println("(bos)");
            return;
        }

        int h = height(root);
        List<Node> level = new ArrayList<>();
        level.add(root);
        int depth = 0;

        System.out.println("Agac (kök yukarıda):");

        while (!level.isEmpty() && depth < h) {
            int spacesBefore = (int) Math.pow(2, h - depth) - 1;
            int spacesBetween = (int) Math.pow(2, h - depth + 1) - 1;

            printSpaces(spacesBefore);

            List<Node> next = new ArrayList<>();

            for (int i = 0; i < level.size(); i++) {
                Node n = level.get(i);

                if (n != null) {
                    System.out.printf("%2d", n.key);
                    next.add(n.left);
                    next.add(n.right);
                } else {
                    System.out.print("  ");
                    next.add(null);
                    next.add(null);
                }

                if (i < level.size() - 1)
                    printSpaces(spacesBetween);
            }

            System.out.println();
            level = next;
            depth++;
        }

        System.out.println();
    }

    // ---------------- 4. ROTASYON ÖRNEKLERİ ----------------

    // ------ 4.1 ZIG (Tek rotasyon: root <-> child) ------
    static void exampleZig() {
        System.out.println("====== ZIG ÖRNEĞİ (Right Rotation) ======");

        /*
               30
              /
            20
           /
         10
        Root = 30
        Key = 20 → Zig yapılır (Right Rotation)
        */

        Node root = new Node(30);
        root.left = new Node(20);

        System.out.println("ÖNCE:");
        printTreePretty(root);

        root = rightRotate(root);

        System.out.println("SONRA (Zig):");
        printTreePretty(root);
    }

    // ------ 4.2 ZIG-ZIG (aynı tarafta iki adım: sol-sol) ------
    static void exampleZigZigLeft() {
        System.out.println("====== ZIG-ZIG SOL-SOL ÖRNEĞİ ======");

        /*
                50
               /
             40
            /
          30
        Zig-Zig: önce üstteki rotate, sonra tekrar rotate
        */

        Node root = new Node(50);
        root.left = new Node(40);
        root.left.left = new Node(30);

        System.out.println("ÖNCE:");
        printTreePretty(root);

        System.out.println("1) İlk Right Rotation (50 pivot):");
        root = rightRotate(root);
        printTreePretty(root);

        System.out.println("2) İkinci Right Rotation (40 pivot):");
        root = rightRotate(root);
        printTreePretty(root);
    }

    // ------ 4.3 ZIG-ZIG (sağ-sağ) ------
    static void exampleZigZigRight() {
        System.out.println("====== ZIG-ZIG SAG-SAG ÖRNEĞİ ======");

        /*
        10
          \
           20
             \
              30
        */

        Node root = new Node(10);
        root.right = new Node(20);
        root.right.right = new Node(30);

        System.out.println("ÖNCE:");
        printTreePretty(root);

        System.out.println("1) İlk Left Rotation (10 pivot):");
        root = leftRotate(root);
        printTreePretty(root);

        System.out.println("2) İkinci Left Rotation (20 pivot):");
        root = leftRotate(root);
        printTreePretty(root);
    }

    // ------ 4.4 ZIG-ZAG (sol-sağ) ------
    static void exampleZigZagLeftRight() {
        System.out.println("====== ZIG-ZAG SOL-SAG ÖRNEĞİ ======");

        /*
                40
               /
             20
               \
                30
         Zig-Zag: önce sol child üzerine rotate, sonra root üzerine rotate
        */

        Node root = new Node(40);
        root.left = new Node(20);
        root.left.right = new Node(30);

        System.out.println("ÖNCE:");
        printTreePretty(root);

        System.out.println("1) Left Rotation (20 pivot):");
        root.left = leftRotate(root.left);
        printTreePretty(root);

        System.out.println("2) Right Rotation (40 pivot):");
        root = rightRotate(root);
        printTreePretty(root);
    }

    // ------ 4.5 ZIG-ZAG (sağ-sol) ------
    static void exampleZigZagRightLeft() {
        System.out.println("====== ZIG-ZAG SAG-SOL ÖRNEĞİ ======");

        /*
        20
          \
           40
          /
        30
        */

        Node root = new Node(20);
        root.right = new Node(40);
        root.right.left = new Node(30);

        System.out.println("ÖNCE:");
        printTreePretty(root);

        System.out.println("1) Right Rotation (40 pivot):");
        root.right = rightRotate(root.right);
        printTreePretty(root);

        System.out.println("2) Left Rotation (20 pivot):");
        root = leftRotate(root);
        printTreePretty(root);
    }

    // ---------------- 5. MAIN ----------------
    public static void main(String[] args) {

        exampleZig();
        exampleZigZigLeft();
        exampleZigZigRight();
        exampleZigZagLeftRight();
        exampleZigZagRightLeft();
    }
}
