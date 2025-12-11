import java.util.ArrayList;
import java.util.List;

/**
 * BTreeDeleteFixExamples.java
 *
 * Amaç:
 *   - B-Tree silme sonrasındaki DÜZELTME (fix) adımlarını örneklerle göstermek:
 *       1) Sol kardeşten ödünç alma (borrowFromPrev)
 *       2) Sağ kardeşten ödünç alma (borrowFromNext)
 *       3) Kardeşlerle birleştirme (merge)
 *
 * Not:
 *   T = 2 (minimum degree) -> 2-3-4 tree davranışı.
 */
public class BTreeDeleteFixExamples {

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

    // -------------------- 3. YARDIMCI: NODE FORMAT / AĞAÇ YAZDIRMA --------------------

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

    static void printTree(BTreeNode root, String title) {
        System.out.println("----- " + title + " -----");
        if (root == null) {
            System.out.println("(agac bos)\n");
            return;
        }

        System.out.println("B-Tree gorunumu (kök yukarida):");
        List<BTreeNode> current = new ArrayList<>();
        current.add(root);
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

    // -------------------- 4. BORROW / MERGE İŞLEMLERİ --------------------
    /*
        Aşağıdaki fonksiyonlar BTreeDelete.java içindeki ile AYNI mantıktadır:
        - borrowFromPrev(parent, idx)
        - borrowFromNext(parent, idx)
        - merge(parent, idx)
     */

    // Sol kardeşten ödünç alma
    static void borrowFromPrev(BTreeNode parent, int idx) {
        BTreeNode child = parent.children[idx];
        BTreeNode sibling = parent.children[idx - 1];

        // child'in anahtarlarını sağa kaydır
        for (int i = child.n - 1; i >= 0; i--) {
            child.keys[i + 1] = child.keys[i];
        }

        // child'in çocuklarını da sağa kaydır
        if (!child.leaf) {
            for (int i = child.n; i >= 0; i--) {
                child.children[i + 1] = child.children[i];
            }
        }

        // parent'taki anahtar child'in ilk anahtarı olur
        child.keys[0] = parent.keys[idx - 1];

        // sibling'in son çocuğunu child'e taşı
        if (!sibling.leaf) {
            child.children[0] = sibling.children[sibling.n];
        }

        // sibling'in son anahtarı parent'e çıkar
        parent.keys[idx - 1] = sibling.keys[sibling.n - 1];

        child.n++;
        sibling.n--;
    }

    // Sağ kardeşten ödünç alma
    static void borrowFromNext(BTreeNode parent, int idx) {
        BTreeNode child = parent.children[idx];
        BTreeNode sibling = parent.children[idx + 1];

        // parent'taki anahtar child'in sonuna eklenir
        child.keys[child.n] = parent.keys[idx];

        // sibling'in ilk çocuğu child'in son çocuğu olur
        if (!child.leaf) {
            child.children[child.n + 1] = sibling.children[0];
        }

        // sibling'in ilk anahtarını parent'e çıkar
        parent.keys[idx] = sibling.keys[0];

        // sibling'in anahtarlarını sola kaydır
        for (int i = 1; i < sibling.n; i++) {
            sibling.keys[i - 1] = sibling.keys[i];
        }

        // sibling'in çocuklarını sola kaydır
        if (!sibling.leaf) {
            for (int i = 1; i <= sibling.n; i++) {
                sibling.children[i - 1] = sibling.children[i];
            }
        }

        child.n++;
        sibling.n--;
    }

    // Merge: parent.keys[idx] + child[idx+1] içeriği child[idx] içine alınır.
    static void merge(BTreeNode parent, int idx) {
        BTreeNode child = parent.children[idx];
        BTreeNode sibling = parent.children[idx + 1];

        // parent.keys[idx] ortadaki anahtar olarak child'a eklenir
        child.keys[T - 1] = parent.keys[idx];

        // sibling'in anahtarlarını child'e taşı
        for (int i = 0; i < sibling.n; i++) {
            child.keys[i + T] = sibling.keys[i];
        }

        // sibling'in çocuklarını da child'e taşı
        if (!child.leaf) {
            for (int i = 0; i <= sibling.n; i++) {
                child.children[i + T] = sibling.children[i];
            }
        }

        // child'in yeni anahtar sayısı
        child.n += sibling.n + 1;

        // parent'taki anahtarları sola kaydır
        for (int i = idx + 1; i < parent.n; i++) {
            parent.keys[i - 1] = parent.keys[i];
        }

        // parent'taki çocuk pointer'larını sola kaydır
        for (int i = idx + 2; i <= parent.n; i++) {
            parent.children[i - 1] = parent.children[i];
        }

        parent.n--;
    }

    // -------------------- 5. ÖRNEKLER --------------------

    /*
        ÖRNEK 1: Sol kardeşten ödünç alma (borrowFromPrev)

        Başlangıç (T=2):

              [20 | 40]
             /    |    \
       [5 | 10]  [25]  [50 | 60]

        child[1] = [25] (sadece T-1 = 1 anahtar)
        left sibling = child[0] = [5 | 10] (2 anahtar, yani >= T)

        fill() içinde: borrowFromPrev(parent, 1) çağrıldığını varsayalım.
     */
    static void exampleBorrowFromPrev() {
        System.out.println("=== ORNEK 1: Sol kardesten odunc alma (borrowFromPrev) ===");

        // Parent
        BTreeNode parent = new BTreeNode(false);
        parent.keys[0] = 20;
        parent.keys[1] = 40;
        parent.n = 2;

        // Sol kardeş (child[0]) : [5 | 10]
        BTreeNode left = new BTreeNode(true);
        left.keys[0] = 5;
        left.keys[1] = 10;
        left.n = 2;

        // Ortadaki child (child[1]) : [25]
        BTreeNode mid = new BTreeNode(true);
        mid.keys[0] = 25;
        mid.n = 1;

        // Sağ kardeş (child[2]) : [50 | 60]
        BTreeNode right = new BTreeNode(true);
        right.keys[0] = 50;
        right.keys[1] = 60;
        right.n = 2;

        parent.children[0] = left;
        parent.children[1] = mid;
        parent.children[2] = right;

        printTree(parent, "Baslangic agaci (borrowFromPrev oncesi)");

        System.out.println(">> borrowFromPrev(parent, 1) cagirilacak (ortadaki child, sol kardesten anahtar alacak).");
        borrowFromPrev(parent, 1);

        printTree(parent, "borrowFromPrev SONRASI agac");
        System.out.println("Bu örnekte sol kardesten bir anahtar yukarı kayip, parent'tan child[1]'e aktarildi.\n");
    }

    /*
        ÖRNEK 2: Sağ kardeşten ödünç alma (borrowFromNext)

        Başlangıç:

              [20 | 40]
             /    |    \
        [5 | 10]  [25]  [50 | 60]

        Şimdi child[1] = [25] yine T-1 anahtar içeriyor,
        ama bu sefer düzelmeyi sağ kardesten yapalım:

            borrowFromNext(parent, 1)
     */
    static void exampleBorrowFromNext() {
        System.out.println("=== ORNEK 2: Sag kardesten odunc alma (borrowFromNext) ===");

        // Parent
        BTreeNode parent = new BTreeNode(false);
        parent.keys[0] = 20;
        parent.keys[1] = 40;
        parent.n = 2;

        // Sol çocuk (child[0]) : [5 | 10]
        BTreeNode left = new BTreeNode(true);
        left.keys[0] = 5;
        left.keys[1] = 10;
        left.n = 2;

        // Ortadaki child (child[1]) : [25]
        BTreeNode mid = new BTreeNode(true);
        mid.keys[0] = 25;
        mid.n = 1;

        // Sağ kardeş (child[2]) : [50 | 60]
        BTreeNode right = new BTreeNode(true);
        right.keys[0] = 50;
        right.keys[1] = 60;
        right.n = 2;

        parent.children[0] = left;
        parent.children[1] = mid;
        parent.children[2] = right;

        printTree(parent, "Baslangic agaci (borrowFromNext oncesi)");

        System.out.println(">> borrowFromNext(parent, 1) cagirilacak (ortadaki child, sag kardesten anahtar alacak).");
        borrowFromNext(parent, 1);

        printTree(parent, "borrowFromNext SONRASI agac");
        System.out.println("Bu örnekte sag kardesten bir anahtar yukarı kayip, parent'tan child[1]'e aktarildi.\n");
    }

    /*
        ÖRNEK 3: Merge (iki kardeşi birleştirme)

        Başlangıç:

              [20 | 40]
             /    |    \
          [5]    [25]  [50]

        child[0] = [5]   (T-1 anahtar)
        child[1] = [25]  (T-1 anahtar)

        fill(parent, 0) içinde, sol tarafta kardes >=T yok, sag kardes de T-1,
        o zaman merge(parent, 0) yapılır:

           merge(parent, 0):

               [20 | 40]
              /    |    \
           [5]   [25]  [50]

         -> child[0] = [5 | 20 | 25]
            parent    = [40]
            children: child[0], child[2] (= [50])
     */
    static void exampleMerge() {
        System.out.println("=== ORNEK 3: Merge (iki kardesi birlestirme) ===");

        // Parent
        BTreeNode parent = new BTreeNode(false);
        parent.keys[0] = 20;
        parent.keys[1] = 40;
        parent.n = 2;

        // child[0] : [5]
        BTreeNode left = new BTreeNode(true);
        left.keys[0] = 5;
        left.n = 1;

        // child[1] : [25]
        BTreeNode mid = new BTreeNode(true);
        mid.keys[0] = 25;
        mid.n = 1;

        // child[2] : [50]
        BTreeNode right = new BTreeNode(true);
        right.keys[0] = 50;
        right.n = 1;

        parent.children[0] = left;
        parent.children[1] = mid;
        parent.children[2] = right;

        printTree(parent, "Baslangic agaci (merge oncesi)");

        System.out.println(">> merge(parent, 0) cagirilacak (child[0] ve child[1] birlestirilip 20 asagi inecek).");
        merge(parent, 0);

        printTree(parent, "merge(parent, 0) SONRASI agac");
        System.out.println("Goruldugu gibi [5] + 20 + [25] -> [5 | 20 | 25] oldu, parent'ta sadece [40] kaldi.\n");
    }

    // -------------------- 6. MAIN --------------------
    public static void main(String[] args) {

        exampleBorrowFromPrev();
        exampleBorrowFromNext();
        exampleMerge();
    }
}
