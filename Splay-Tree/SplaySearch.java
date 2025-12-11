import java.util.ArrayList;
import java.util.List;

public class SplaySearch {

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
        Amaç: key'e en yakın düğümü köke taşımak.

        - Eğer key ağaçta varsa: o düğüm kök olur.
        - Eğer key yoksa: son erişilen düğüm kök olur.

        Zig      : root ile çocuk arasında tek rotasyon
        Zig-Zig  : aynı tarafta iki adım (sol-sol, sağ-sağ)
        Zig-Zag  : çapraz (sol-sağ, sağ-sol)
     */
    static Node splay(Node root, int key) {
        if (root == null || root.key == key)
            return root;

        // --- Key, root.key'den küçük -> SOL tarafta arıyoruz ---
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

            // Zig (root ile sol çocuk)
            return (root.left == null) ? root : rightRotate(root);
        }

        // --- Key, root.key'den büyük -> SAĞ tarafta arıyoruz ---
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

            // Zig (root ile sağ çocuk)
            return (root.right == null) ? root : leftRotate(root);
        }
    }

    // ---------------- 4. INSERT (Basit, önceki sınıfa benzer) ----------------
    static Node insert(Node root, int key) {
        // Ağaç boşsa direkt yeni düğüm
        if (root == null) {
            return new Node(key);
        }

        // Önce splay yap: root'u key'e yaklaştır
        root = splay(root, key);

        // Zaten varsa tekrar ekleme
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

    // ---------------- 5. SPLAY SEARCH ----------------
    /*
        Arama mantığı:

        - splay(root, key) çağırılır.
        - Eğer key varsa: splay sonucu kök = aranan düğüm.
        - Eğer key yoksa: splay sonucu kök = key'e en yakın düğüm.

        Dönen kökü global root olarak güncelliyoruz.
     */
    static Node search(int key) {
        if (root == null) {
            System.out.println("Agac bos, " + key + " bulunamadi.");
            return null;
        }

        System.out.println("==== " + key + " degerini ariyoruz (SPLAY SEARCH) ====");
        root = splay(root, key);  // root güncellenir
        printTreePretty(root);

        if (root.key == key) {
            System.out.println(key + " bulundu! (Simdi kökte)");
            System.out.println();
            return root;
        } else {
            System.out.println(key + " agacta yok. Koke tasinan en yakin dugum: " + root.key);
            System.out.println();
            return null;
        }
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

        // Önce küçük bir splay ağacı oluşturalım
        insertToRoot(30);
        insertToRoot(20);
        insertToRoot(40);
        insertToRoot(10);
        insertToRoot(25);
        insertToRoot(35);
        insertToRoot(50);

        // 1) Ağaçta OLAN bir değeri ara
        search(25);   // 25 köke gelecektir

        // 2) Ağaçta OLMAYAN bir değeri ara
        search(28);   // 28 yok, ama en yakın düğüm köke gelecektir

        // 3) Sık erişilen değeri tekrar tekrar arayınca hep kökte kalır
        search(25);
        search(25);
    }
}
