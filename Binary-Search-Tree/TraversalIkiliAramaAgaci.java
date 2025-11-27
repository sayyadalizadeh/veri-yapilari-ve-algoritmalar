import java.util.ArrayList;
import java.util.List;

public class TraversalIkiliAramaAgaci {

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

    // Kök düğüm
    static Node root = null;

    // -------------------- 2. EKLEME (BASİT) --------------------
    static Node insert(Node current, int x) {
        if (current == null) {
            return new Node(x); // boş yere geldik, yeni düğüm oluştur
        }

        if (x < current.key) {
            current.left = insert(current.left, x);      // sola
        } else if (x > current.key) {
            current.right = insert(current.right, x);    // sağa
        } else {
            System.out.println(x + " zaten agacta var, eklenmedi.");
        }

        return current;
    }

    static void insert(int x) {
        root = insert(root, x);
    }

    // -------------------- 3. DOLAŞMA (TRAVERSAL) --------------------
    // Preorder: KÖK - SOL - SAĞ
    static void preorder(Node current) {
        if (current == null) return;
        System.out.print(current.key + " ");
        preorder(current.left);
        preorder(current.right);
    }

    // Inorder: SOL - KÖK - SAĞ  (BST'de sıralı çıktı verir)
    static void inorder(Node current) {
        if (current == null) return;
        inorder(current.left);
        System.out.print(current.key + " ");
        inorder(current.right);
    }

    // Postorder: SOL - SAĞ - KÖK
    static void postorder(Node current) {
        if (current == null) return;
        postorder(current.left);
        postorder(current.right);
        System.out.print(current.key + " ");
    }

    // Seviye seviye dolaşma (Level Order) – bonus
    static void levelOrder(Node root) {
        int h = height(root);
        for (int level = 1; level <= h; level++) {
            printGivenLevel(root, level);
            System.out.println();
        }
    }

    static void printGivenLevel(Node current, int level) {
        if (current == null) return;
        if (level == 1) {
            System.out.print(current.key + " ");
        } else {
            printGivenLevel(current.left, level - 1);
            printGivenLevel(current.right, level - 1);
        }
    }

    // ---------------------------------------------------------
    // 4. AĞACI GÖRSEL GÖRÜNÜM İLE ÇİZME (KÖK YUKARIDA)
    // ---------------------------------------------------------
    static int height(Node current) {
        if (current == null) return 0;
        int leftH = height(current.left);
        int rightH = height(current.right);
        return 1 + Math.max(leftH, rightH);
    }

    static void printSpaces(int n) {
        for (int i = 0; i < n; i++) {
            System.out.print(" ");
        }
    }

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

    // -------------------- 5. MAIN --------------------
    public static void main(String[] args) {

        // Örnek ağaç
        insert(50);
        insert(30);
        insert(70);
        insert(20);
        insert(40);
        insert(60);
        insert(80);

        // Önce ağacı şekil olarak gösterelim
        printTreePretty(root);

        // Sonra dolaşma sıralarını yazdıralım
        System.out.print("Preorder  (KÖK-SOL-SAG):   ");
        preorder(root);
        System.out.println();

        System.out.print("Inorder   (SOL-KÖK-SAG):   ");
        inorder(root);
        System.out.println();

        System.out.print("Postorder (SOL-SAG-KÖK):   ");
        postorder(root);
        System.out.println();

        System.out.println("Level Order (seviye seviye):");
        levelOrder(root);
    }
}
