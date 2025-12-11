import java.util.ArrayList;
import java.util.List;

public class SplayTreeOperationsVisualizer {

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

    // ---------------- 3. SPLAY İŞLEMİ ----------------
    /*
        Amaç: key’e en yakın düğümü köke taşımak.

        - Eğer key ağaçta varsa: o düğüm köke gelir.
        - Eğer key yoksa: key’e en yakın düğüm köke gelir.

        Zig      : root ile çocuğu arasında tek rotasyon
        Zig-Zig  : aynı tarafta iki adım (sol-sol, sağ-sağ)
        Zig-Zag  : çapraz (sol-sağ, sağ-sol)
     */
    static Node splay(Node root, int key) {
        if (root == null || root.key == key)
            return root;

        // -------- SOL TARAF --------
        if (key < root.key) {

            // Sol çocuk yoksa: key yok, root en yakın düğüm
            if (root.left == null) return root;

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

        // -------- SAĞ TARAF --------
        else {

            // Sağ çocuk yoksa: key yok, root en yakın düğüm
            if (root.right == null) return root;

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

    // ---------------- 4. INSERT (SPLAY + EKLEME) ----------------
    /*
        1) root = splay(root, key)
        2) Eğer root.key == key -> zaten var, ekleme
        3) Yeni düğümü oluştur, sola/sağa uygun şekilde yerleştir
           ve yeni kök olarak döndür.
     */
    static Node insert(Node root, int key) {
        if (root == null) {
            return new Node(key);
        }

        // Önce key’e (veya en yakın düğüme) splay et
        root = splay(root, key);

        // Zaten varsa ekleme
        if (root.key == key) {
            System.out.println(key + " zaten agacta var, eklenmedi.");
            return root;
        }

        Node yeni = new Node(key);

        if (key < root.key) {
            yeni.right = root;
            yeni.left = root.left;
            root.left = null;
        } else {
            yeni.left = root;
            yeni.right = root.right;
            root.right = null;
        }

        return yeni; // yeni kök
    }

    static void insertAndShow(int key) {
        System.out.println("=== INSERT: " + key + " (insert + splay) ===");
        root = insert(root, key);
        printTreePretty(root);
    }

    // ---------------- 5. SEARCH (SPLAY SEARCH) ----------------
    /*
        Arama:

        root = splay(root, key);

        - Eğer root.key == key → bulundu ve köke taşındı.
        - Değilse → ağaçta yok, ama en yakın düğüm kökte.
     */
    static Node search(int key) {
        System.out.println("=== SEARCH: " + key + " (splay search) ===");

        if (root == null) {
            System.out.println("Agac bos, " + key + " bulunamadi.\n");
            return null;
        }

        root = splay(root, key);
        printTreePretty(root);

        if (root.key == key) {
            System.out.println(key + " bulundu! (Simdi kökte)\n");
            return root;
        } else {
            System.out.println(key + " agacta yok. Koke tasinan en yakin dugum: " + root.key + "\n");
            return null;
        }
    }

    // ---------------- 6. DELETE (SPLAY DELETE) ----------------
    /*
        1) root = splay(root, key)
        2) Eğer root.key != key -> yok, silme
        3) Eğer key bulunduysa:

           a) root.left == null  -> root = root.right
           b) root.left != null:
              - temp = root.right
              - root = splay(root.left, key)   // sol alt ağaçta en büyüğü köke getirir
              - root.right = temp
     */
    static Node delete(Node root, int key) {
        if (root == null) {
            System.out.println(key + " agac bosken silinmek istendi.");
            return null;
        }

        // Silinecek/daha yakın düğümü köke taşı
        root = splay(root, key);

        if (root.key != key) {
            System.out.println(key + " agacta bulunamadi, silme yapilmadi.");
            return root;
        }

        System.out.println("Silinecek dugum (kök): " + root.key);

        // Sol çocuk yoksa, sadece sağ alt ağaca geç
        if (root.left == null) {
            root = root.right;
        } else {
            Node temp = root.right;
            // Sol alt ağaçta en büyüğü köke getir
            root = splay(root.left, key);
            // Sağ tarafı bağla
            root.right = temp;
        }

        return root;
    }

    static void deleteAndShow(int key) {
        System.out.println("=== DELETE: " + key + " (splay + delete) ===");
        root = delete(root, key);
        printTreePretty(root);
    }

    // ---------------- 7. AĞACI ŞEKİL OLARAK ÇİZME ----------------

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

    // ---------------- 8. MAIN ----------------
    public static void main(String[] args) {

        System.out.println("===== SPLAY TREE OPERATIONS VISUALIZER =====\n");

        // 1) INSERT örnekleri
        insertAndShow(30);
        insertAndShow(20);
        insertAndShow(40);
        insertAndShow(10);
        insertAndShow(25);
        insertAndShow(35);
        insertAndShow(50);

        // 2) SEARCH örnekleri
        search(25);   // var, köke gelir
        search(28);   // yok, en yakın düğüm köke gelir

        // 3) DELETE örnekleri
        deleteAndShow(10);  // yaprak
        deleteAndShow(25);  // iç düğüm
        deleteAndShow(30);  // kök (o anki root olmayabilir, splay ile köke gelir)
        deleteAndShow(999); // olmayan eleman

        System.out.println("===== PROGRAM BITTI =====");
    }
}
