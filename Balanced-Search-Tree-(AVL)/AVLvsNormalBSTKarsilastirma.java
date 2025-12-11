import java.util.ArrayList;
import java.util.List;

public class AVLvsNormalBSTKarsilastirma {

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

    // Normal BST ve AVL için ayrı kökler
    static Node bstRoot = null;
    static Node avlRoot = null;

    // -------------------- 2. YÜKSEKLİK ve BALANS --------------------

    static int height(Node n) {
        if (n == null) return 0;
        return 1 + Math.max(height(n.left), height(n.right));
    }

    static int getBalance(Node n) {
        if (n == null) return 0;
        return height(n.left) - height(n.right);
    }

    // -------------------- 3. NORMAL BST INSERT --------------------
    static Node bstInsert(Node node, int key) {
        if (node == null) return new Node(key);

        if (key < node.key)
            node.left = bstInsert(node.left, key);
        else if (key > node.key)
            node.right = bstInsert(node.right, key);
        else
            System.out.println("[BST] " + key + " zaten var, eklenmedi.");

        return node;
    }

    static void bstInsertToRoot(int key) {
        bstRoot = bstInsert(bstRoot, key);
    }

    // -------------------- 4. AVL INSERT --------------------
    static Node avlInsert(Node node, int key) {
        if (node == null) return new Node(key);

        if (key < node.key)
            node.left = avlInsert(node.left, key);
        else if (key > node.key)
            node.right = avlInsert(node.right, key);
        else {
            System.out.println("[AVL] " + key + " zaten var, eklenmedi.");
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

    static void avlInsertToRoot(int key) {
        avlRoot = avlInsert(avlRoot, key);
    }

    // Rotasyonlar
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

    // -------------------- 5. AĞAÇLARI ÇİZME --------------------
    static void printSpaces(int n) {
        for (int i = 0; i < n; i++) System.out.print(" ");
    }

    static void printTreePretty(Node root, String baslik) {
        System.out.println("=== " + baslik + " ===");
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

        System.out.println("Yukseklik: " + height(root));
        System.out.println();
    }

    // -------------------- 6. ARAMA ve ADIM SAYISI --------------------
    static int searchSteps(Node root, int key, String label) {
        int steps = 0;
        Node current = root;

        System.out.println("[" + label + "] " + key + " degerini ariyoruz...");
        while (current != null) {
            steps++;
            System.out.println("Adim " + steps + " -> dugum: " + current.key);

            if (key == current.key) {
                System.out.println("Bulundu! Toplam adim: " + steps);
                System.out.println();
                return steps;
            } else if (key < current.key) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        System.out.println("Bulunamadi. Toplam adim: " + steps);
        System.out.println();
        return steps;
    }

    // -------------------- 7. MAIN --------------------
    public static void main(String[] args) {

        // Diziyi özellikle "kötü" seçtik: SIRALI ekleme
        // Normal BST için yüksek, AVL için dengeli bir ağaç oluşacak.
        int[] dizi = {10, 20, 30, 40, 50, 60, 70};

        for (int x : dizi) {
            bstInsertToRoot(x);
            avlInsertToRoot(x);
        }

        // İki ağacı çiz ve yükseklikleri karşılaştır
        printTreePretty(bstRoot, "NORMAL BST");
        printTreePretty(avlRoot, "AVL AGACI");

        // Aynı elemanı iki ağaçta da ara ve adım sayılarını karşılaştır
        int aranan = 60;
        int bstSteps = searchSteps(bstRoot, aranan, "BST");
        int avlSteps = searchSteps(avlRoot, aranan, "AVL");

        System.out.println("Ozet:");
        System.out.println("BST yuksekligi  = " + height(bstRoot));
        System.out.println("AVL yuksekligi  = " + height(avlRoot));
        System.out.println(aranan + " icin BST adim sayisi = " + bstSteps);
        System.out.println(aranan + " icin AVL adim sayisi = " + avlSteps);
    }
}
