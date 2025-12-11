import java.util.ArrayList;
import java.util.List;

public class AVLInsertDengeliAgac {

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

    // -------------------- 2. YARDIMCI FONKSİYONLAR --------------------

    // Yükseklik: boş ağaç için 0, aksi halde 1 + max(sol, sağ)
    static int height(Node n) {
        if (n == null) return 0;
        int leftH = height(n.left);
        int rightH = height(n.right);
        return 1 + Math.max(leftH, rightH);
    }

    // Denge (balance) faktörü = sol yükseklik - sağ yükseklik
    static int getBalance(Node n) {
        if (n == null) return 0;
        return height(n.left) - height(n.right);
    }

    // Sağ dönüş (Right Rotation) – LL ve LR durumlarında kullanılır
    static Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        // Rotasyon
        x.right = y;
        y.left = T2;

        return x; // yeni kök
    }

    // Sol dönüş (Left Rotation) – RR ve RL durumlarında kullanılır
    static Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        // Rotasyon
        y.left = x;
        x.right = T2;

        return y; // yeni kök
    }

    // -------------------- 3. AVL INSERT --------------------
    /*
        Normal BST insert + her adımda balance kontrolü:

        balance >  1  ve  key < left.key   -> LL   (Right Rotate)
        balance >  1  ve  key > left.key   -> LR   (Left + Right)
        balance < -1  ve  key > right.key  -> RR   (Left Rotate)
        balance < -1  ve  key < right.key  -> RL   (Right + Left)
     */
    static Node insert(Node node, int key) {
        // 1) Normal BST ekleme
        if (node == null) {
            return new Node(key);
        }

        if (key < node.key) {
            node.left = insert(node.left, key);
        } else if (key > node.key) {
            node.right = insert(node.right, key);
        } else {
            System.out.println(key + " zaten agacta var, eklenmedi.");
            return node; // aynı değeri eklemiyoruz
        }

        // 2) Bu düğümün balance faktörünü hesapla
        int balance = getBalance(node);

        // --------- 3) DÖRT DENGESİZLİK DURUMU ---------

        // LL durumu
        if (balance > 1 && key < node.left.key) {
            System.out.println("LL dengesizligi -> RIGHT ROTATION (pivot: " + node.key + ")");
            return rightRotate(node);
        }

        // RR durumu
        if (balance < -1 && key > node.right.key) {
            System.out.println("RR dengesizligi -> LEFT ROTATION (pivot: " + node.key + ")");
            return leftRotate(node);
        }

        // LR durumu
        if (balance > 1 && key > node.left.key) {
            System.out.println("LR dengesizligi -> LEFT sonra RIGHT ROTATION (pivot: " + node.key + ")");
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // RL durumu
        if (balance < -1 && key < node.right.key) {
            System.out.println("RL dengesizligi -> RIGHT sonra LEFT ROTATION (pivot: " + node.key + ")");
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        // Dengeli ise bu düğüm aynen kalır
        return node;
    }

    // Kök üzerinden çağırmak için kısa fonksiyon
    static void insertToRoot(int key) {
        root = insert(root, key);
    }

    // -------------------- 4. AĞACI GÖRSEL GÖSTERME --------------------
    static void printSpaces(int n) {
        for (int i = 0; i < n; i++) System.out.print(" ");
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

    // Her eklemeden sonra ağacı göstermek için yardımcı fonksiyon
    static void insertAndShow(int key) {
        System.out.println("==== " + key + " ekleniyor ====");
        insertToRoot(key);
        printTreePretty(root);
    }

    // -------------------- 5. MAIN --------------------
    public static void main(String[] args) {

        // Her örnek için ağacı sıfırlayıp dört dengesizlik durumunu gösterelim

        // 1) LL dengesizliği örneği: 30, 20, 10
        System.out.println("=== LL ORNEGI (Right Rotation) ===");
        root = null;
        insertAndShow(30);
        insertAndShow(20);
        insertAndShow(10);  // burada LL rotasyonu olacak

        // 2) RR dengesizliği örneği: 30, 40, 50
        System.out.println("=== RR ORNEGI (Left Rotation) ===");
        root = null;
        insertAndShow(30);
        insertAndShow(40);
        insertAndShow(50);  // burada RR rotasyonu olacak

        // 3) LR dengesizliği örneği: 30, 10, 20
        System.out.println("=== LR ORNEGI (Left + Right Rotation) ===");
        root = null;
        insertAndShow(30);
        insertAndShow(10);
        insertAndShow(20);  // burada LR rotasyonu olacak

        // 4) RL dengesizliği örneği: 30, 50, 40
        System.out.println("=== RL ORNEGI (Right + Left Rotation) ===");
        root = null;
        insertAndShow(30);
        insertAndShow(50);
        insertAndShow(40);  // burada RL rotasyonu olacak
    }
}
