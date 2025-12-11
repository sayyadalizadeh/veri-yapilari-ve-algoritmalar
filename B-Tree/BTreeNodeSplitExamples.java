import java.util.ArrayList;
import java.util.List;

/**
 * BTreeNodeSplitExamples.java
 *
 * Amaç:
 *   - B-Tree'de "splitChild" (düğüm bölme) işlemini ÖĞRETİCİ örneklerle göstermek.
 *   - Her adımda ağacın önceki ve sonraki halini seviye seviye yazdırmak.
 *
 * Not:
 *   T = 2 (minimum degree) -> 2-3-4 tree davranışı.
 */
public class BTreeNodeSplitExamples {

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
            this.keys = new int[2 * T - 1];  // max anahtar sayısı
            this.children = new BTreeNode[2 * T]; // max çocuk sayısı
            this.n = 0;
        }
    }

    // Küçük yardımcı: bir düğümü [k1 | k2 | k3] şeklinde yaz
    static String formatNode(BTreeNode node) {
        if (node == null) return "null";
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < node.n; i++) {
            sb.append(node.keys[i]);
            if (i != node.n - 1) sb.append(" | ");
        }
        sb.append("]");
        return sb.toString();
    }

    // Ağacı seviye seviye yazdır
    static void printTree(BTreeNode root, String title) {
        System.out.println("----- " + title + " -----");
        if (root == null) {
            System.out.println("(agac bos)\n");
            return;
        }

        System.out.println("B-Tree gorunumu (kök yukarida):");
        int level = 0;
        var current = new ArrayList<BTreeNode>();
        current.add(root);

        while (!current.isEmpty()) {
            System.out.print("Seviye " + level + ": ");
            var next = new ArrayList<BTreeNode>();

            for (BTreeNode node : current) {
                System.out.print(formatNode(node) + "   ");
                if (!node.leaf) {
                    for (int i = 0; i <= node.n; i++) {
                        if (node.children[i] != null) {
                            next.add(node.children[i]);
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

    // -------------------- 3. SPLIT CHILD İŞLEMİ --------------------
    /*
        splitChild(parent, i)

        parent.children[i] = y düğümü DOLU (2*T-1 anahtar) ise:
          - Ortadaki anahtar parent.keys[i] konumuna çıkarılır.
          - y'nin sağ yarısı yeni düğüm z'ye konur.
          - parent içine yeni bir çocuk eklenmiş olur.

        T = 2 için:
          y: [a, b, c] (3 anahtar)
          orta = b
          parent'e b çıkarılır
          y  -> [a]
          z  -> [c]
     */
    static void splitChild(BTreeNode parent, int i) {
        BTreeNode y = parent.children[i];
        BTreeNode z = new BTreeNode(y.leaf); // sağ yarı

        // z düğümüne y'nin son T-1 anahtarlarını taşı
        z.n = T - 1;
        for (int j = 0; j < T - 1; j++) {
            z.keys[j] = y.keys[j + T];
        }

        // Yaprak değilse, çocuklarını da böl
        if (!y.leaf) {
            for (int j = 0; j < T; j++) {
                z.children[j] = y.children[j + T];
            }
        }

        // y'nin anahtar sayısını T-1'e düşür
        y.n = T - 1;

        // parent'in çocuk pointer'larını sağa kaydır (z için yer aç)
        for (int j = parent.n; j >= i + 1; j--) {
            parent.children[j + 1] = parent.children[j];
        }
        parent.children[i + 1] = z;

        // parent'in anahtarlarını sağa kaydır (orta anahtar için yer aç)
        for (int j = parent.n - 1; j >= i; j--) {
            parent.keys[j + 1] = parent.keys[j];
        }

        // y'nin ortadaki anahtarını parent'taki uygun yere koy
        parent.keys[i] = y.keys[T - 1];

        // parent'in anahtar sayısını artır
        parent.n++;
    }

    // -------------------- 4. ÖRNEK 1: KÖK DÜĞÜM SPLIT (YAPRAK) --------------------
    /*
        T = 2 için:
         Kök: [10, 20, 30]   (n = 3 = 2*T-1, DOLU)

         splitChild(rootParent, 0) yaparsak:

         Yeni kök (P): [20]
              /         \
           [10]        [30]
     */
    static void exampleRootLeafSplit() {
        System.out.println("=== ORNEK 1: Kök düğüm (yaprak) split ===");

        // 1) Önce tek düğümlü (yaprak) bir kök oluşturalım: [10, 20, 30]
        BTreeNode rootLeaf = new BTreeNode(true);
        rootLeaf.keys[0] = 10;
        rootLeaf.keys[1] = 20;
        rootLeaf.keys[2] = 30;
        rootLeaf.n = 3; // 2*T-1 = 3 anahtar ile dolu
        printTree(rootLeaf, "1. ADIM: Dolu kök yaprak (split oncesi)");

        // 2) Gerçekte B-Tree algoritması yeni bir kök (parent) oluşturup splitChild uygular
        BTreeNode newRoot = new BTreeNode(false); // yeni kök (iç düğüm)
        newRoot.children[0] = rootLeaf;          // eski kökü 0. çocuk yap

        System.out.println("2. ADIM: Yeni bir parent kök olusturuldu, splitChild uygulanacak.");
        printTree(newRoot, "splitChild oncesi (parent + child)");

        // 3) Artık splitChild(newRoot, 0) çağırabiliriz
        splitChild(newRoot, 0);

        printTree(newRoot, "3. ADIM: splitChild(newRoot, 0) SONRASI (kök bölündü)");
        System.out.println("Bu örnekte ortadaki anahtar 20 yukari cikti, agac dengelendi.");
        System.out.println("==========================================================\n");
    }

    // -------------------- 5. ÖRNEK 2: İÇ DÜĞÜM ÇOCUĞUNUN SPLIT EDİLMESİ --------------------
    /*
        Ağaç yapısı (T = 2):

               [20]
              /    \
          [5 |10|15]   [25 | 30]

        Sol çocuk [5,10,15] dolu bir yaprak.
        Burada splitChild(root, 0) uygularsak:

               [10 | 20]
               /   |   \
            [5]   [15]  [25 | 30]

        Yani, child[0] bölünüp ortadaki 10 yukarı çıktı.
     */
    static void exampleInternalChildSplit() {
        System.out.println("=== ORNEK 2: Ic dugumun dolu cocugunu split etme ===");

        // 1) Kök: [20]
        BTreeNode root = new BTreeNode(false);
        root.keys[0] = 20;
        root.n = 1;

        // 2) Sol çocuk: [5, 10, 15] (dolu yaprak)
        BTreeNode left = new BTreeNode(true);
        left.keys[0] = 5;
        left.keys[1] = 10;
        left.keys[2] = 15;
        left.n = 3;

        // 3) Sağ çocuk: [25, 30] (dolu olmayan yaprak)
        BTreeNode right = new BTreeNode(true);
        right.keys[0] = 25;
        right.keys[1] = 30;
        right.n = 2;

        root.children[0] = left;
        root.children[1] = right;

        printTree(root, "1. ADIM: Baslangic agaci (split oncesi)");

        // 4) splitChild(root, 0) : root.children[0] (yani left) bölünecek
        System.out.println("2. ADIM: splitChild(root, 0) cagirilacak (sol cocuk bolunuyor).");
        splitChild(root, 0);

        printTree(root, "3. ADIM: splitChild(root, 0) SONRASI");

        System.out.println("Goruldugu gibi [5 |10|15] dugumu ikiye bolundu, 10 yukari cikti.");
        System.out.println("==========================================================\n");
    }

    // -------------------- 6. ÖRNEK 3: SIRAYLA INSERT İLE OTOMATİK SPLIT GÖSTERİMİ --------------------
    /*
        Bu örnekte gerçek insert algoritmasını (kısa) kullanıp
        hangi insert adımında split olduğunu gözlemleriz.
     */

    // Kısa B-Tree (insert için root değişkeni kullanacağız)
    static BTreeNode autoRoot = null;

    static void splitChildAuto(BTreeNode parent, int i) {
        System.out.println("   [DEBUG] splitChild cagrildi, parent = " +
                formatNode(parent) + ", child index = " + i);
        splitChild(parent, i);
        System.out.println("   [DEBUG] splitChild SONRASI parent: " + formatNode(parent));
    }

    static void insertNonFullAuto(BTreeNode node, int key) {
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
                splitChildAuto(node, i);
                if (key > node.keys[i]) {
                    i++;
                }
            }
            insertNonFullAuto(node.children[i], key);
        }
    }

    static void insertAuto(int key) {
        if (autoRoot == null) {
            autoRoot = new BTreeNode(true);
            autoRoot.keys[0] = key;
            autoRoot.n = 1;
            return;
        }

        if (autoRoot.n == 2 * T - 1) {
            BTreeNode s = new BTreeNode(false);
            s.children[0] = autoRoot;
            splitChildAuto(s, 0);

            int i = (key > s.keys[0]) ? 1 : 0;
            insertNonFullAuto(s.children[i], key);
            autoRoot = s;
        } else {
            insertNonFullAuto(autoRoot, key);
        }
    }

    static void exampleAutoInsertSplits() {
        System.out.println("=== ORNEK 3: Sirali insert ile otomatik split ornekleri ===");
        int[] values = {10, 20, 5, 6, 12, 30, 7, 17};

        for (int v : values) {
            System.out.println("\n==> " + v + " EKLENIYOR...");
            insertAuto(v);
            printTree(autoRoot, "Ekleme sonrasi agac");
        }

        System.out.println("Bu örnekte, insert sırasında hangi adımlarda splitChild cagırıldığı debug satırlarında görülebilir.");
        System.out.println("==========================================================\n");
    }

    // -------------------- 7. MAIN --------------------
    public static void main(String[] args) {

        exampleRootLeafSplit();
        exampleInternalChildSplit();
        exampleAutoInsertSplits();
    }
}
