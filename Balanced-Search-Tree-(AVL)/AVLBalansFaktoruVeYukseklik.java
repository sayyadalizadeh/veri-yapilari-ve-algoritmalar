import java.util.ArrayList;
import java.util.List;

public class AVLBalansFaktoruVeYukseklik {

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

    // -------------------- 2. YÜKSEKLİK ve BALANS --------------------

    // Yükseklik (AVL içinde sık kullanılıyor)
    static int height(Node n) {
        if (n == null) return 0;
        return 1 + Math.max(height(n.left), height(n.right));
    }

    // Balans faktörü = sol yüksekliği – sağ yüksekliği
    static int getBalance(Node n) {
        if (n == null) return 0;
        return height(n.left) - height(n.right);
    }

    // Bir düğümün bilgilerini ekrana yaz
    static void printNodeInfo(Node n) {
        if (n == null) return;
        int h = height(n);
        int bf = getBalance(n);
        System.out.println("Dugum = " + n.key + " | Yükseklik = " + h + " | Balans faktörü = " + bf);
    }

    // Tüm düğümleri (Inorder) dolaşıp yazdır
    static void printAllNodeInfo(Node current) {
        if (current == null) return;
        printAllNodeInfo(current.left);
        printNodeInfo(current);
        printAllNodeInfo(current.right);
    }

    // -------------------- 3. AVL INSERT --------------------

    static Node insert(Node node, int key) {
        if (node == null)
            return new Node(key);

        if (key < node.key)
            node.left = insert(node.left, key);
        else if (key > node.key)
            node.right = insert(node.right, key);
        else {
            System.out.println(key + " zaten var, eklenmedi.");
            return node;
        }

        int balance = getBalance(node);

        // LL
        if (balance > 1 && key < node.left.key)
            return rightRotate(node);

        // RR
        if (balance < -1 && key > node.right.key)
            return leftRotate(node);

        // LR
        if (balance > 1 && key > node.left.key) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // RL
        if (balance < -1 && key < node.right.key) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

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

    // -------------------- 4. AĞACI ŞEKİL OLARAK ÇİZME --------------------

    static void printSpaces(int n) {
        for (int i = 0; i < n; i++) System.out.print(" ");
    }

    static void printTreePretty(Node root) {
        if (root == null) {
            System.out.println("(bos agac)");
            return;
        }

        int h = height(root);

        List<Node> currentLevel = new ArrayList<>();
        currentLevel.add(root);

        int level = 0;

        System.out.println("Agacin gorunumu (kök yukarida):");

        while (!currentLevel.isEmpty() && level < h) {

            int spacesBefore = (int)Math.pow(2, h - level) - 1;
            int spacesBetween = (int)Math.pow(2, h - level + 1) - 1;

            printSpaces(spacesBefore);

            List<Node> nextLevel = new ArrayList<>();

            for (int i = 0; i < currentLevel.size(); i++) {

                Node n = currentLevel.get(i);

                if (n != null) {
                    System.out.printf("%2d", n.key);
                    nextLevel.add(n.left);
                    nextLevel.add(n.right);
                } else {
                    System.out.print("  ");
                    nextLevel.add(null);
                    nextLevel.add(null);
                }

                if (i < currentLevel.size() - 1)
                    printSpaces(spacesBetween);
            }

            System.out.println();
            currentLevel = nextLevel;
            level++;
        }

        System.out.println();
    }

    // -------------------- 5. MAIN --------------------
    public static void main(String[] args) {

        // ÖRNEK AVL AĞACI OLUŞTURALIM
        int[] dizi = {50, 30, 70, 20, 40, 60, 80, 10, 35, 45};

        for (int x : dizi) {
            root = insert(root, x);
        }

        // Ağacı çiz
        printTreePretty(root);

        // Her düğümün balans ve yükseklik değerlerini göster
        System.out.println("=== DÜĞÜM YÜKSEKLİK ve BALANS FAKTÖRLERİ ===");
        printAllNodeInfo(root);
    }
}
