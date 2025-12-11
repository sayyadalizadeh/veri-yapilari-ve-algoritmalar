import java.util.ArrayList;
import java.util.List;

public class AVLRotationsOrnek {

    // -------------------- 1. DÜĞÜM SINIFI --------------------
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

    // -------------------- 2. YARDIMCI FONKSİYONLAR --------------------

    // Yükseklik
    static int height(Node n) {
        if (n == null) return 0;
        return 1 + Math.max(height(n.left), height(n.right));
    }

    // Konsola boşluk bas
    static void printSpaces(int n) {
        for (int i = 0; i < n; i++) System.out.print(" ");
    }

    // Ağacı kök yukarıda, seviyeler halinde çizer
    static void printTreePretty(Node root) {
        if (root == null) {
            System.out.println("(agac bos)");
            return;
        }

        int h = height(root);
        List<Node> currentLevel = new ArrayList<>();
        currentLevel.add(root);
        int level = 0;

        System.out.println("Agacin gorunumu (kök yukarida):");

        while (!currentLevel.isEmpty() && level < h) {
            int spacesBefore = (int) Math.pow(2, h - level) - 1;
            int spacesBetween = (int) Math.pow(2, h - level + 1) - 1;

            printSpaces(spacesBefore);

            List<Node> nextLevel = new ArrayList<>();

            for (int i = 0; i < currentLevel.size(); i++) {
                Node node = currentLevel.get(i);

                if (node != null) {
                    System.out.printf("%2d", node.key);
                    nextLevel.add(node.left);
                    nextLevel.add(node.right);
                } else {
                    System.out.print("  ");
                    nextLevel.add(null);
                    nextLevel.add(null);
                }

                if (i < currentLevel.size() - 1) {
                    printSpaces(spacesBetween);
                }
            }

            System.out.println();
            currentLevel = nextLevel;
            level++;
        }

        System.out.println();
    }

    // -------------------- 3. ROTASYON FONKSİYONLARI --------------------

    // Sağ rotasyon (Right Rotation)  – LL ve LR durumlarında
    static Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        // Rotasyon
        x.right = y;
        y.left = T2;

        return x; // yeni kök
    }

    // Sol rotasyon (Left Rotation) – RR ve RL durumlarında
    static Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        // Rotasyon
        y.left = x;
        x.right = T2;

        return y; // yeni kök
    }

    // -------------------- 4. ÖRNEKLER --------------------

    // LL: soldan ağır –> SAĞ ROTASYON
    static void ornekLL() {
        System.out.println("===== LL ORNEGI (Right Rotation) =====");

        // Ağaç:   30
        //        /
        //      20
        //     /
        //   10
        Node root = new Node(30);
        root.left = new Node(20);
        root.left.left = new Node(10);

        System.out.println("Rotasyondan ONCE:");
        printTreePretty(root);

        root = rightRotate(root);

        System.out.println("Right Rotation SONRASI:");
        printTreePretty(root);
    }

    // RR: sağdan ağır –> SOL ROTASYON
    static void ornekRR() {
        System.out.println("===== RR ORNEGI (Left Rotation) =====");

        // Ağaç:  30
        //          \
        //           40
        //             \
        //              50
        Node root = new Node(30);
        root.right = new Node(40);
        root.right.right = new Node(50);

        System.out.println("Rotasyondan ONCE:");
        printTreePretty(root);

        root = leftRotate(root);

        System.out.println("Left Rotation SONRASI:");
        printTreePretty(root);
    }

    // LR: önce SOL çocukta sağa eğrilik –> önce SOL ROT, sonra SAĞ ROT
    static void ornekLR() {
        System.out.println("===== LR ORNEGI (Left + Right Rotation) =====");

        // Ağaç:    30
        //         /
        //       10
        //         \
        //          20
        Node root = new Node(30);
        root.left = new Node(10);
        root.left.right = new Node(20);

        System.out.println("Rotasyondan ONCE:");
        printTreePretty(root);

        // LR adımları:
        System.out.println("1) SOL cocukta Left Rotation (10 pivot):");
        root.left = leftRotate(root.left);
        printTreePretty(root);

        System.out.println("2) Kökte Right Rotation (30 pivot):");
        root = rightRotate(root);
        printTreePretty(root);
    }

    // RL: önce SAĞ çocukta sola eğrilik –> önce SAĞ ROT, sonra SOL ROT
    static void ornekRL() {
        System.out.println("===== RL ORNEGI (Right + Left Rotation) =====");

        // Ağaç:   30
        //           \
        //            50
        //           /
        //         40
        Node root = new Node(30);
        root.right = new Node(50);
        root.right.left = new Node(40);

        System.out.println("Rotasyondan ONCE:");
        printTreePretty(root);

        // RL adımları:
        System.out.println("1) SAG cocukta Right Rotation (50 pivot):");
        root.right = rightRotate(root.right);
        printTreePretty(root);

        System.out.println("2) Kökte Left Rotation (30 pivot):");
        root = leftRotate(root);
        printTreePretty(root);
    }

    // -------------------- 5. MAIN --------------------
    public static void main(String[] args) {

        ornekLL();
        ornekRR();
        ornekLR();
        ornekRL();
    }
}
