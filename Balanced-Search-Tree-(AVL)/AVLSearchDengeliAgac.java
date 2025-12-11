import java.util.ArrayList;
import java.util.List;

public class AVLSearchDengeliAgac {

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

    // -------------------- 2. YARDIMCI FONKSİYONLAR (YÜKSEKLİK, BALANS, ROTASYON) --------------------

    // Yükseklik
    static int height(Node n) {
        if (n == null) return 0;
        return 1 + Math.max(height(n.left), height(n.right));
    }

    // Balans faktörü = sol yükseklik - sağ yükseklik
    static int getBalance(Node n) {
        if (n == null) return 0;
        return height(n.left) - height(n.right);
    }

    // Sağ rotasyon (Right Rotation)
    static Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        return x; // yeni kök
    }

    // Sol rotasyon (Left Rotation)
    static Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        return y; // yeni kök
    }

    // -------------------- 3. AVL INSERT (ÖRNEK AĞAÇ OLUŞTURMAK İÇİN) --------------------

    static Node insert(Node node, int key) {
        // Normal BST ekleme
        if (node == null) {
            return new Node(key);
        }

        if (key < node.key) {
            node.left = insert(node.left, key);
        } else if (key > node.key) {
            node.right = insert(node.right, key);
        } else {
            System.out.println(key + " zaten var, eklenmedi.");
            return node;
        }

        // Balance kontrolü
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

    static void insertToRoot(int key) {
        root = insert(root, key);
    }

    // -------------------- 4. AVL İÇİN ARAMA (SEARCH) --------------------

    // Rekürsif arama – adım adım ekrana yazdırır
    static Node searchRecursive(Node node, int key) {
        if (node == null) {
            System.out.println("null dugume geldik, " + key + " bulunamadi.");
            return null;
        }

        System.out.println("Dugumdeyiz: " + node.key);

        if (key == node.key) {
            System.out.println(key + " bulundu!");
            return node;
        }

        if (key < node.key) {
            System.out.println(key + " < " + node.key + " -> SOLA git");
            return searchRecursive(node.left, key);
        } else {
            System.out.println(key + " > " + node.key + " -> SAGA git");
            return searchRecursive(node.right, key);
        }
    }

    // İteratif arama – aynı işi döngü ile yapar
    static Node searchIterative(int key) {
        Node current = root;

        while (current != null) {
            System.out.println("Dugumdeyiz: " + current.key);

            if (key == current.key) {
                System.out.println(key + " bulundu!");
                return current;
            } else if (key < current.key) {
                System.out.println(key + " < " + current.key + " -> SOLA git");
                current = current.left;
            } else {
                System.out.println(key + " > " + current.key + " -> SAGA git");
                current = current.right;
            }
        }

        System.out.println("null dugume geldik, " + key + " bulunamadi.");
        return null;
    }

    // -------------------- 5. AĞACI GÖRSEL OLARAK ÇİZME --------------------

    static void printSpaces(int n) {
        for (int i = 0; i < n; i++) System.out.print(" ");
    }

    static void printTreePretty(Node root) {
        if (root == null) {
            System.out.println("(bos agac)");
            System.out.println();
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

    // -------------------- 6. MAIN --------------------
    public static void main(String[] args) {

        // AVL ağacını oluşturalım
        int[] dizi = {50, 30, 70, 20, 40, 60, 80, 10, 35, 45};
        for (int x : dizi) {
            insertToRoot(x);
        }

        // Ağacı şekil olarak göster
        printTreePretty(root);

        // 1) Ağaçta OLAN bir değeri rekürsif arayalım
        int aranan1 = 60;
        System.out.println("=== " + aranan1 + " degerini (REKURSIF) ariyoruz ===");
        searchRecursive(root, aranan1);
        System.out.println();

        // 2) Ağaçta OLMAYAN bir değeri iteratif arayalım
        int aranan2 = 25;
        System.out.println("=== " + aranan2 + " degerini (ITERATIF) ariyoruz ===");
        searchIterative(aranan2);
        System.out.println();
    }
}
