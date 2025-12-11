import java.util.ArrayList;
import java.util.List;

public class BTreePrint {

    // -------------------- 1. B-TREE PARAMETRESİ --------------------
    static final int T = 2;   // minimum degree

    // -------------------- 2. DÜĞÜM SINIFI --------------------
    static class BTreeNode {
        int[] keys;
        BTreeNode[] children;
        int n;            // anahtar sayısı
        boolean leaf;     // yaprak mı?

        BTreeNode(boolean leaf) {
            this.leaf = leaf;
            this.keys = new int[2 * T - 1];
            this.children = new BTreeNode[2 * T];
            this.n = 0;
        }
    }

    // Kök düğüm
    static BTreeNode root = null;

    // -------------------- 3. INSERT (kısa versiyon – test için gerekli) --------------------

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

        if (root.n == 2 * T - 1) {
            BTreeNode s = new BTreeNode(false);
            s.children[0] = root;
            splitChild(s, 0);

            int i = (key > s.keys[0]) ? 1 : 0;
            insertNonFull(s.children[i], key);
            root = s;
        } else {
            insertNonFull(root, key);
        }
    }

    // -------------------- 4. NODE FORMATLAMA --------------------
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

    // -------------------- 5. AĞACI GÖRSEL OLARAK YAZDIRMA --------------------
    static void printBTree(BTreeNode node) {
        if (node == null) {
            System.out.println("(Agac bos)");
            return;
        }

        System.out.println("===== B-TREE GORUNUMU (Kök Yukarıda) =====");

        List<BTreeNode> current = new ArrayList<>();
        current.add(node);

        int level = 0;

        while (!current.isEmpty()) {
            System.out.print("Seviye " + level + ": ");

            List<BTreeNode> next = new ArrayList<>();

            for (BTreeNode n : current) {
                System.out.print(formatNode(n) + "   ");

                if (!n.leaf) {
                    for (int i = 0; i <= n.n; i++) {
                        if (n.children[i] != null) {
                            next.add(n.children[i]);
                        }
                    }
                }
            }

            System.out.println();
            current = next;
            level++;
        }

        System.out.println();
    }

    // -------------------- 6. MAIN --------------------
    public static void main(String[] args) {

        // Test için B-Tree oluşturalım
        int[] values = {10, 20, 5, 6, 12, 30, 7, 17};

        for (int v : values) {
            insert(v);
            printBTree(root);
        }
    }
}
