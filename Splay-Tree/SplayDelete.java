import java.util.ArrayList;
import java.util.List;

public class SplayDelete {

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

        - key varsa: o düğüm kök olur.
        - yoksa : key’e en yakın düğüm kök olur.

        Zig      : root <-> child
        Zig-Zig  : aynı taraf (sol-sol, sağ-sağ)
        Zig-Zag  : çapraz (sol-sağ, sağ-sol)
     */
    static Node splay(Node root, int key) {
        if (root == null || root.key == key)
            return root;

        // -------- SOL TARAF --------
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

        // -------- SAĞ TARAF --------
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

    // ---------------- 4. INSERT (önceki sınıflarla aynı mantık) ----------------
    static Node insert(Node root, int key) {
        if (root == null) {
            return new Node(key);
        }

        // önce splay: root’u key’e yaklaştır
        root = splay(root, key);

        // zaten varsa ekleme
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

    static void insertToRoot(int key) {
        System.out.println(">>> " + key + " ekleniyor (insert + splay)...");
        root = insert(root, key);
        printTreePretty(root);
    }

    // ---------------- 5. SPLAY DELETE ----------------
    /*
        Adımlar (klasik splay silme algoritması):

        1) root = splay(root, key);
        2) Eğer root.key != key ise -> key yoktur, sadece root döner.
        3) Eğer key bulunduysa (root’ta):

           - Eğer sol çocuk yoksa:
                root = root.right

           - Eğer sol çocuk varsa:
                temp = root.right
                // Sol alt ağacın EN BÜYÜĞÜNÜ köke splay et
                root = splay(root.left, key);
                // (key sol alt ağaçta yok, dolayısıyla en büyük eleman köke gelir)
                root.right = temp
     */
    static Node delete(Node root, int key) {
        if (root == null) {
            System.out.println(key + " agac bosken silinmek istendi.");
            return null;
        }

        // Önce key’i (veya en yakını) köke taşı
        root = splay(root, key);

        // Kökteki değer aranan değilse: ağaçta yok
        if (root.key != key) {
            System.out.println(key + " agacta bulunamadi, silme yok.");
            return root;
        }

        System.out.println("Silinecek dugum (kök): " + root.key);

        // 1) Sol çocuk yoksa, sağ alt ağacı yeni root yap
        if (root.left == null) {
            root = root.right;
        }
        // 2) Sol çocuk varsa:
        else {
            Node temp = root.right;  // sağ tarafı sakla
            // Sol alt ağaçta en büyük elemanı köke splay et
            root = splay(root.left, key);
            // Artık root'un sağında hiç eleman yoktur, sağ tarafı bağla
            root.right = temp;
        }

        return root;
    }

    static void deleteFromRoot(int key) {
        System.out.println("==== " + key + " SILINIYOR (splay + delete) ====");
        root = delete(root, key);
        printTreePretty(root);
    }

    // ---------------- 6. AĞACI ŞEKİL OLARAK ÇİZME ----------------

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

    // ---------------- 7. MAIN ----------------
    public static void main(String[] args) {

        // Küçük bir splay ağacı oluşturalım
        insertToRoot(30);
        insertToRoot(20);
        insertToRoot(40);
        insertToRoot(10);
        insertToRoot(25);
        insertToRoot(35);
        insertToRoot(50);

        System.out.println("=== ILK AGAC TAM HALI ===");
        printTreePretty(root);

        // 1) YAPRAK silme (örneğin 10)
        deleteFromRoot(10);

        // 2) İÇ DÜĞÜM silme (örneğin 20)
        deleteFromRoot(20);

        // 3) KÖK silme (şu anki root.key neyse onu silelim)
        if (root != null) {
            int currentRootKey = root.key;
            deleteFromRoot(currentRootKey);
        }

        // 4) Ağaçta olmayan bir elemanı silmeyi deneyelim
        deleteFromRoot(999);
    }
}
