import java.util.ArrayList;
import java.util.List;

public class SplayInsert {

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

    // Kök düğüm
    static Node root = null;

    // ---------------- 2. ROTASYONLAR ----------------

    // Sağ rotasyon (Right Rotation)
    static Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        // Rotasyon
        x.right = y;
        y.left = T2;

        return x; // yeni kök
    }

    // Sol rotasyon (Left Rotation)
    static Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        // Rotasyon
        y.left = x;
        x.right = T2;

        return y; // yeni kök
    }

    // ---------------- 3. SPLAY İŞLEMİ ----------------
    /*
        Amaç: verilen key'i köke taşımak.

        Zig      : tek rotasyon (root ile çocuğu)
        Zig-Zig  : aynı tarafta iki adım (sol-sol, sağ-sağ)
        Zig-Zag  : çapraz (sol-sağ, sağ-sol)
     */
    static Node splay(Node root, int key) {
        if (root == null || root.key == key)
            return root;

        // --- Key, root.key'den küçük -> SOL tarafta aranacak ---
        if (key < root.key) {

            if (root.left == null) return root; // key yok

            // Zig-Zig (Sol-Sol)
            if (key < root.left.key) {
                root.left.left = splay(root.left.left, key);
                root = rightRotate(root);
            }
            // Zig-Zag (Sol-Sağ)
            else if (key > root.left.key) {
                root.left.right = splay(root.left.right, key);
                if (root.left.right != null)
                    root.left = leftRotate(root.left);
            }

            // Zig
            return (root.left == null) ? root : rightRotate(root);
        }

        // --- Key, root.key'den büyük -> SAĞ tarafta aranacak ---
        else {

            if (root.right == null) return root; // key yok

            // Zig-Zig (Sağ-Sağ)
            if (key > root.right.key) {
                root.right.right = splay(root.right.right, key);
                root = leftRotate(root);
            }
            // Zig-Zag (Sağ-Sol)
            else if (key < root.right.key) {
                root.right.left = splay(root.right.left, key);
                if (root.right.left != null)
                    root.right = rightRotate(root.right);
            }

            // Zig
            return (root.right == null) ? root : leftRotate(root);
        }
    }

    // ---------------- 4. INSERT + SPLAY ----------------
    /*
        1) Normal BST mantığıyla key'i ekle
        2) Sonra o key'i splay() ile köke getir
     */
    static Node insert(Node root, int key) {
        // Ağaç boşsa direkt yeni düğüm
        if (root == null) {
            return new Node(key);
        }

        // Önce splay yap: root'u key'e yaklaştır
        root = splay(root, key);

        // Eğer zaten aynı key varsa tekrar ekleme
        if (root.key == key) {
            System.out.println(key + " zaten agacta var, eklenmedi.");
            return root;
        }

        Node yeni = new Node(key);

        // Yeni key, root.key'den küçük
        if (key < root.key) {
            yeni.right = root;
            yeni.left = root.left;
            root.left = null;
        }
        // Yeni key, root.key'den büyük
        else {
            yeni.left = root;
            yeni.right = root.right;
            root.right = null;
        }

        return yeni; // yeni kök
    }

    // Kök üzerinden çağırmak için kısa fonksiyon
    static void insertToRoot(int key) {
        System.out.println("==== " + key + " EKLENIYOR ve SPLAY YAPILIYOR ====");
        root = insert(root, key);
        printTreePretty(root);
    }

    // ---------------- 5. AĞACI ŞEKİL OLARAK ÇİZME ----------------

    static int height(Node n) {
        if (n == null) return 0;
        return 1 + Math.max(height(n.left), height(n.right));
    }

    static void printSpaces(int n) {
        for (int i = 0; i < n; i++) System.out.print(" ");
    }

    static void printTreePretty(Node root) {
        if (root == null) {
            System.out.println("(agac bos)");
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

                if (i < currentLevel.size() - 1)
                    printSpaces(spacesBetween);
            }

            System.out.println();
            currentLevel = nextLevel;
            level++;
        }

        System.out.println();
    }

    // ---------------- 6. MAIN ----------------
    public static void main(String[] args) {

        // Bazı örnek eklemeler (her seferinde yeni kök = eklenen eleman)
        insertToRoot(30);
        insertToRoot(20);
        insertToRoot(40);
        insertToRoot(10);
        insertToRoot(25);
        insertToRoot(35);
        insertToRoot(50);

        // Aynı değeri tekrar eklemeyi deneyelim
        insertToRoot(25);
    }
}
