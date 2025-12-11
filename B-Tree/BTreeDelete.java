import java.util.ArrayList;
import java.util.List;

public class BTreeDelete {

    // -------------------- 1. B-TREE PARAMETRESİ --------------------
    /*
        T = minimum degree (en küçük derece)

        - Her düğümde en fazla 2*T - 1 anahtar
        - En az T - 1 anahtar (kök hariç)
        - Çocuk sayısı = anahtar sayısı + 1  (en fazla 2*T)
     */
    static final int T = 2;   // T=2 için klasik 2-3-4 ağacı davranışı

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
    // ================   INSERT (ÖNCEKİYLE AYNI MANTIK) ===========
    // ============================================================

    static void splitChild(BTreeNode parent, int i) {
        BTreeNode y = parent.children[i];
        BTreeNode z = new BTreeNode(y.leaf);

        // z düğümüne y'nin son T-1 anahtarlarını taşı
        z.n = T - 1;
        for (int j = 0; j < T - 1; j++) {
            z.keys[j] = y.keys[j + T];
        }

        // Yaprak değilse çocuklarını da böl
        if (!y.leaf) {
            for (int j = 0; j < T; j++) {
                z.children[j] = y.children[j + T];
            }
        }

        // y'nin anahtar sayısını T-1'e düşür
        y.n = T - 1;

        // parent'in çocuklarını sağa kaydır
        for (int j = parent.n; j >= i + 1; j--) {
            parent.children[j + 1] = parent.children[j];
        }
        parent.children[i + 1] = z;

        // parent'in anahtarlarını sağa kaydır
        for (int j = parent.n - 1; j >= i; j--) {
            parent.keys[j + 1] = parent.keys[j];
        }

        // Ortadaki anahtarı parent'e çıkar
        parent.keys[i] = y.keys[T - 1];
        parent.n++;
    }

    static void insertNonFull(BTreeNode node, int key) {
        int i = node.n - 1;

        if (node.leaf) {
            // Yaprak: doğru yere kaydırarak ekle
            while (i >= 0 && key < node.keys[i]) {
                node.keys[i + 1] = node.keys[i];
                i--;
            }
            node.keys[i + 1] = key;
            node.n++;
        } else {
            // İç düğüm: hangi çocuğa ineceğimizi bul
            while (i >= 0 && key < node.keys[i]) {
                i--;
            }
            i++;

            // Gideceğimiz çocuk doluysa önce split
            if (node.children[i].n == 2 * T - 1) {
                splitChild(node, i);
                // Split sonrası, eğer key ortaya çıkan anahtardan büyükse sağa kay
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
            // Kök dolu -> yeni kök oluştur, split et
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
    // ====================   DELETE ALGORİTMASI  =================
    // ============================================================

    // Ana delete fonksiyonu (root üzerinden çağrılır)
    static void delete(int key) {
        if (root == null) {
            System.out.println(key + " icin silme yapilamadi, agac bos.");
            return;
        }

        deleteFromNode(root, key);

        // Eğer kökte hiç anahtar kalmadıysa:
        if (root.n == 0) {
            // Kök yaprak ise ağacı tamamen boşalt
            if (root.leaf) {
                root = null;
            } else {
                // Kök yaprak değilse, tek çocuğu yeni kök yap
                root = root.children[0];
            }
        }
    }

    /*
        deleteFromNode(node, key):

        1) Bu düğümde key varsa:
             - Yaprak düğüm ise: direkt sil.
             - İç düğüm ise: üç duruma göre işlem yap.
        2) Bu düğümde key yoksa:
             - Eğer yaprak ise: yok (bulunamadı).
             - Yaprak değilse: uygun çocuğa gitmeden önce o çocuğun
               en az T anahtar içerdiğinden emin ol (gerekirse borrow/merge).
     */
    static void deleteFromNode(BTreeNode node, int key) {
        int idx = findKeyIndex(node, key);

        // 1) key, bu düğümde bulunmuşsa
        if (idx < node.n && node.keys[idx] == key) {
            if (node.leaf) {
                // 1.a) Yaprak düğümden silme
                removeFromLeaf(node, idx);
            } else {
                // 1.b) İç düğümden silme
                removeFromNonLeaf(node, idx);
            }
        } else {
            // 2) key, bu düğümde yok

            if (node.leaf) {
                // Yaprakta bulunamadı -> ağaçta yok
                System.out.println(key + " agacta bulunamadi (yapraga gelindi).");
                return;
            }

            // Bu noktada: node yaprak değil ve key bu düğümde yok.
            // Key'in gidebileceği çocuk "idx" olup olmayacağını belirleyelim:
            boolean sonCocukMu = (idx == node.n);

            // Çocuğa gitmeden önce: çocukta en az T anahtar olduğundan emin ol.
            if (node.children[idx].n < T) {
                fill(node, idx);
            }

            // Eğer son çocuk tarafına gitmiştik ve merge ile sola kayma olduysa
            if (sonCocukMu && idx > node.n) {
                deleteFromNode(node.children[idx - 1], key);
            } else {
                deleteFromNode(node.children[idx], key);
            }
        }
    }

    // Bu düğümde key'in hangi index'te olabileceğini bul
    static int findKeyIndex(BTreeNode node, int key) {
        int idx = 0;
        while (idx < node.n && node.keys[idx] < key) {
            idx++;
        }
        return idx;
    }

    // -------------------- 1) YAPRAKTAN SİLME --------------------
    static void removeFromLeaf(BTreeNode node, int idx) {
        // idx'ten sonraki anahtarları sola kaydır
        for (int i = idx + 1; i < node.n; i++) {
            node.keys[i - 1] = node.keys[i];
        }
        node.n--;
    }

    // -------------------- 2) İÇ DÜĞÜMDEN SİLME --------------------
    /*
        node iç düğümü ve key = node.keys[idx]

        Durumlar:
        a) Sol çocuk (child[idx]) en az T anahtar içeriyorsa:
              - key'in öncülünü (predecessor) bul, node.keys[idx] yerine koy
              - child[idx] alt ağacından predecessor'ü sil
        b) Sağ çocuk (child[idx+1]) en az T anahtar içeriyorsa:
              - key'in ardılını (successor) bul, node.keys[idx] yerine koy
              - child[idx+1] alt ağacından successor'u sil
        c) Her iki çocuk da T-1 anahtar içeriyorsa:
              - key'i ve sağ çocuğu sol çocuğa merge et
              - Sonra child[idx] alt ağacından key'i sil
     */
    static void removeFromNonLeaf(BTreeNode node, int idx) {
        int key = node.keys[idx];

        // a) Soldaki çocuk en az T anahtar içeriyor
        if (node.children[idx].n >= T) {
            int pred = getPredecessor(node, idx);
            node.keys[idx] = pred;
            deleteFromNode(node.children[idx], pred);
        }
        // b) Sağdaki çocuk en az T anahtar içeriyor
        else if (node.children[idx + 1].n >= T) {
            int succ = getSuccessor(node, idx);
            node.keys[idx] = succ;
            deleteFromNode(node.children[idx + 1], succ);
        }
        // c) Her iki çocuk da T-1 anahtar içeriyor
        else {
            merge(node, idx);
            deleteFromNode(node.children[idx], key);
        }
    }

    // En büyük öncül (predecessor): soldaki çocuğun en sağ yaprağı
    static int getPredecessor(BTreeNode node, int idx) {
        BTreeNode cur = node.children[idx];
        while (!cur.leaf) {
            cur = cur.children[cur.n]; // en sağ çocuk
        }
        return cur.keys[cur.n - 1];
    }

    // En küçük ardıl (successor): sağdaki çocuğun en sol yaprağı
    static int getSuccessor(BTreeNode node, int idx) {
        BTreeNode cur = node.children[idx + 1];
        while (!cur.leaf) {
            cur = cur.children[0]; // en sol çocuk
        }
        return cur.keys[0];
    }

    // -------------------- 3) BORROW / MERGE İŞLEMLERİ --------------------

    /*
        fill(node, idx):
        child[idx] düğümünde T-1 anahtar var, bir altına ineceğiz.
        Bu çocuğu "en az T anahtar" seviyesine getirmemiz lazım:

        - Eğer sol kardeş (idx-1) T veya daha fazla anahtara sahipse: borrowFromPrev
        - Yoksa, sağ kardeş (idx+1) T veya daha fazla anahtara sahipse: borrowFromNext
        - Aksi halde, merge ile child[idx] ve bir kardeş birleştirilir.
     */
    static void fill(BTreeNode node, int idx) {
        if (idx > 0 && node.children[idx - 1].n >= T) {
            borrowFromPrev(node, idx);
        } else if (idx < node.n && node.children[idx + 1].n >= T) {
            borrowFromNext(node, idx);
        } else {
            if (idx < node.n) {
                merge(node, idx);
            } else {
                merge(node, idx - 1);
            }
        }
    }

    // Sol kardeşten ödünç alma
    static void borrowFromPrev(BTreeNode node, int idx) {
        BTreeNode child = node.children[idx];
        BTreeNode sibling = node.children[idx - 1];

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

        // node'daki anahtarı child'in ilk anahtarı yap
        child.keys[0] = node.keys[idx - 1];

        // Eğer sibling yaprak değilse, son çocuğunu child'in ilk çocuğu yap
        if (!sibling.leaf) {
            child.children[0] = sibling.children[sibling.n];
        }

        // sibling'in son anahtarını node'a kaydır
        node.keys[idx - 1] = sibling.keys[sibling.n - 1];

        child.n++;
        sibling.n--;
    }

    // Sağ kardeşten ödünç alma
    static void borrowFromNext(BTreeNode node, int idx) {
        BTreeNode child = node.children[idx];
        BTreeNode sibling = node.children[idx + 1];

        // node'daki anahtarı child'ın sonuna ekle
        child.keys[child.n] = node.keys[idx];

        // Eğer child yaprak değilse, sibling'in ilk çocuğunu child'ın son çocuğu yap
        if (!child.leaf) {
            child.children[child.n + 1] = sibling.children[0];
        }

        // sibling'in ilk anahtarını node'a çek
        node.keys[idx] = sibling.keys[0];

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

    /*
        merge(node, idx):

        node.keys[idx] ve node.children[idx+1] içindeki tüm anahtarlar
        node.children[idx] içine merge edilir.
        node.children[idx+1] serbest kalır (görmezden gelinir).
     */
    static void merge(BTreeNode node, int idx) {
        BTreeNode child = node.children[idx];
        BTreeNode sibling = node.children[idx + 1];

        // node.keys[idx]'i child'in ortasına koy
        child.keys[T - 1] = node.keys[idx];

        // sibling'in anahtarlarını child'e taşı
        for (int i = 0; i < sibling.n; i++) {
            child.keys[i + T] = sibling.keys[i];
        }

        // Çocukları da taşı
        if (!child.leaf) {
            for (int i = 0; i <= sibling.n; i++) {
                child.children[i + T] = sibling.children[i];
            }
        }

        // child'in anahtar sayısını güncelle
        child.n += sibling.n + 1;

        // node'daki anahtarları sola kaydır
        for (int i = idx + 1; i < node.n; i++) {
            node.keys[i - 1] = node.keys[i];
        }

        // node'daki çocuk pointer'larını sola kaydır
        for (int i = idx + 2; i <= node.n; i++) {
            node.children[i - 1] = node.children[i];
        }

        node.n--;
    }

    // ============================================================
    // ==================   YAZDIRMA / YARDIMCI   ==================
    // ============================================================

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

    static void insertAndShow(int key) {
        System.out.println("==> " + key + " EKLENIYOR...");
        insert(key);
        printBTree(root);
    }

    static void deleteAndShow(int key) {
        System.out.println("==> " + key + " SILINIYOR...");
        delete(key);
        printBTree(root);
    }

    // ============================================================
    // =========================   MAIN   =========================
    // ============================================================

    public static void main(String[] args) {

        // Klasik B-Tree örneği (T=2)
        int[] keys = {10, 20, 5, 6, 12, 30, 7, 17};

        System.out.println("=== B-Tree olusturuluyor (INSERT) ===");
        for (int k : keys) {
            insertAndShow(k);
        }

        System.out.println("=== B-Tree DELETE ORNEKLERI ===");

        // 1) Yapraktan silme
        deleteAndShow(6);

        // 2) Yine yaprak/kenardaki bir eleman
        deleteAndShow(7);

        // 3) İç düğümden silme (öncül/ardıl kullanarak)
        deleteAndShow(12);

        // 4) Ağaçta olmayan bir eleman
        deleteAndShow(100);

        // 5) Kökten silme (duruma göre merge/borrow örneği)
        deleteAndShow(10);
    }
}
