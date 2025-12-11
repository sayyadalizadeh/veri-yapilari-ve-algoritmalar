import java.util.ArrayList;
import java.util.List;

public class BTreeTraversal {

    // -------------------- 1. B-TREE PARAMETRESİ --------------------
    /*
        T = minimum degree (en küçük derece)

        - Her düğümde en fazla 2*T - 1 anahtar
        - En az T - 1 anahtar (kök hariç)
        - Çocuk sayısı = anahtar sayısı + 1  (en fazla 2*T)
     */
    static final int T = 2;   // T=2 için klasik 2-3-4 ağacı gibi düşünülebilir

    // -------------------- 2. DÜĞÜM SINIFI --------------------
    static class BTreeNode {
        int[] keys;               // Anahtarlar
        BTreeNode[] children;     // Çocuk işaretçileri
        int n;                    // Şu anki anahtar sayısı
        boolean leaf;             // Yaprak mı?

        BTreeNode(boolean leaf) {
            this.leaf = leaf;
            this.keys = new int[2 * T - 1];
            this.children = new BTreeNode[2 * T];
            this.n = 0;
        }
    }

    // Kök düğüm
    static BTreeNode root = null;

    // ============================================================
    // ==================   INSERT KISMI (KISA)   ==================
    // ============================================================

    static void splitChild(BTreeNode parent, int i) {
        BTreeNode y = parent.children[i];
        BTreeNode z = new BTreeNode(y.leaf);

        z.n = T - 1;
        for (int j = 0; j < T - 1; j++) {
            z.keys[j] = y.keys[j + T];
        }

        if (!y.leaf) {
            for (int j = 0; j < T; j++) {
                z.children[j] = y.children[j + T];
            }
        }

        y.n = T - 1;

        for (int j = parent.n; j >= i + 1; j--) {
            parent.children[j + 1] = parent.children[j];
        }
        parent.children[i + 1] = z;

        for (int j = parent.n - 1; j >= i; j--) {
            parent.keys[j + 1] = parent.keys[j];
        }

        parent.keys[i] = y.keys[T - 1];
        parent.n++;
    }

    static void insertNonFull(BTreeNode node, int key) {
        int i = node.n - 1;

        if (node.leaf) {
            while (i >= 0 && key < node.keys[i]) {
                node.keys[i + 1] = node.keys[i];
                i--;
            }
            node.keys[i + 1] = key;
            node.n++;
        } else {
            while (i >= 0 && key < node.keys[i]) {
                i--;
            }
            i++;

            if (node.children[i].n == 2 * T - 1) {
                splitChild(node, i);
                if (key > node.keys[i]) {
                    i++;
                }
            }
            insertNonFull(node.children[i], key);
        }
    }

    static void insert(int key) {
        if (root == null) {
            root = new BTreeNode(true);
            root.keys[0] = key;
            root.n = 1;
            return;
        }

        BTreeNode r = root;
        if (r.n == 2 * T - 1) {
            BTreeNode s = new BTreeNode(false);
            s.children[0] = r;
            splitChild(s, 0);

            int i = 0;
            if (key > s.keys[0]) i = 1;
            insertNonFull(s.children[i], key);

            root = s;
        } else {
            insertNonFull(r, key);
        }
    }

    // ============================================================
    // ==================   TRAVERSAL (DOLAŞMA)   ==================
    // ============================================================

    // Inorder: child[0], key[0], child[1], key[1], ..., child[n], (BST mantığına en yakın)
    static void inorder(BTreeNode node) {
        if (node == null) return;

        int i;
        for (i = 0; i < node.n; i++) {
            if (!node.leaf) {
                inorder(node.children[i]);
            }
            System.out.print(node.keys[i] + " ");
        }
        if (!node.leaf) {
            inorder(node.children[i]); // son child (n. çocuk)
        }
    }

    // Preorder: önce düğümdeki tüm anahtarlar, sonra çocuklar
    static void preorder(BTreeNode node) {
        if (node == null) return;

        // Anahtarları yaz
        for (int i = 0; i < node.n; i++) {
            System.out.print(node.keys[i] + " ");
        }

        // Çocuklara git
        if (!node.leaf) {
            for (int i = 0; i <= node.n; i++) {
                preorder(node.children[i]);
            }
        }
    }

    // Postorder: önce tüm çocuklar, sonra anahtarlar
    static void postorder(BTreeNode node) {
        if (node == null) return;

        if (!node.leaf) {
            for (int i = 0; i <= node.n; i++) {
                postorder(node.children[i]);
            }
        }

        for (int i = 0; i < node.n; i++) {
            System.out.print(node.keys[i] + " ");
        }
    }

    // Level-order (seviye seviye) – BFS
    static void levelOrder(BTreeNode root) {
        if (root == null) return;

        List<BTreeNode> queue = new ArrayList<>();
        queue.add(root);

        int level = 0;

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            System.out.print("Seviye " + level + ": ");

            List<BTreeNode> next = new ArrayList<>();

            for (int i = 0; i < levelSize; i++) {
                BTreeNode node = queue.get(i);
                System.out.print(formatNode(node) + "  ");

                if (!node.leaf) {
                    for (int j = 0; j <= node.n; j++) {
                        if (node.children[j] != null) {
                            next.add(node.children[j]);
                        }
                    }
                }
            }

            System.out.println();
            queue = next;
            level++;
        }
    }

    // ============================================================
    // ==================   YARDIMCI FONKSİYONLAR   ===============
    // ============================================================

    static String formatNode(BTreeNode n) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < n.n; i++) {
            sb.append(n.keys[i]);
            if (i != n.n - 1) sb.append(" | ");
        }
        sb.append("]");
        return sb.toString();
    }

    static void printBTree(BTreeNode node) {
        if (node == null) {
            System.out.println("(agac bos)");
            return;
        }

        System.out.println("B-Tree gorunumu (kök yukarida):");

        List<BTreeNode> currentLevel = new ArrayList<>();
        currentLevel.add(node);
        int level = 0;

        while (!currentLevel.isEmpty()) {
            System.out.print("Seviye " + level + ": ");

            List<BTreeNode> nextLevel = new ArrayList<>();

            for (BTreeNode n : currentLevel) {
                System.out.print(formatNode(n) + "   ");

                if (!n.leaf) {
                    for (int i = 0; i <= n.n; i++) {
                        if (n.children[i] != null) {
                            nextLevel.add(n.children[i]);
                        }
                    }
                }
            }

            System.out.println();
            currentLevel = nextLevel;
            level++;
        }

        System.out.println();
    }

    static void insertAndShow(int key) {
        System.out.println("==> " + key + " EKLENIYOR...");
        insert(key);
        printBTree(root);
    }

    // ============================================================
    // =========================   MAIN   =========================
    // ============================================================

    public static void main(String[] args) {

        // Örnek aynı kalsın (diğer B-Tree dosyalarındaki gibi)
        int[] keys = {10, 20, 5, 6, 12, 30, 7, 17};

        for (int k : keys) {
            insertAndShow(k);
        }

        System.out.println("=== INORDER (SOL - ANAHTARLAR - SAG) ===");
        inorder(root);
        System.out.println("\n");

        System.out.println("=== PREORDER (DUGUM - COCUKLAR) ===");
        preorder(root);
        System.out.println("\n");

        System.out.println("=== POSTORDER (COCUKLAR - DUGUM) ===");
        postorder(root);
        System.out.println("\n");

        System.out.println("=== LEVEL ORDER (SEVIYE SEVIYE) ===");
        levelOrder(root);
    }
}
