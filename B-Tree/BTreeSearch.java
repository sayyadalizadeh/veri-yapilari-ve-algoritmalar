import java.util.ArrayList;
import java.util.List;

public class BTreeSearch {

    // -------------------- 1. B-TREE PARAMETRESİ --------------------
    /*
        T = minimum degree (en küçük derece)

        - Her düğümde en fazla 2*T - 1 anahtar
        - En az T - 1 anahtar (kök hariç)
        - Çocuk sayısı = anahtar sayısı + 1  (en fazla 2*T)
     */
    static final int T = 2;   // B-Tree derecesi (T=2 için 2-3-4 ağacı gibi düşünülebilir)

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

    // -------------------- 3. INSERT KISMI (BTreeInsert ile AYNI MANTIK) --------------------

    static void splitChild(BTreeNode parent, int i) {
        BTreeNode y = parent.children[i];
        BTreeNode z = new BTreeNode(y.leaf);

        // z düğümüne y'nin son T - 1 anahtarını taşı
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

        // y'nin anahtar sayısını T - 1'e düşür
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

    // -------------------- 4. B-TREE SEARCH (ARAMA) --------------------
    /*
        Arama algoritması:

        BTreeSearch(node, key):

        1) i = 0
        2) Anahtarları soldan sağa gezerken:
             - key > keys[i] ise i++
             - ilk defa key <= keys[i] olduğunda dur.
        3) Eğer keys[i] == key ise bulundu.
        4) Eğer leaf ise ve bulamadıysak, ağaçta yok.
        5) Değilse, uygun child[i] ya da child[i+1] altına in.
     */

    static BTreeNode search(BTreeNode node, int key) {
        if (node == null) {
            System.out.println("Agac bos, " + key + " bulunamadi.");
            return null;
        }

        int i = 0;

        // Anahtarlar arasında ilerle
        while (i < node.n && key > node.keys[i]) {
            i++;
        }

        // i < n ve anahtarı bulduysak
        if (i < node.n && key == node.keys[i]) {
            System.out.println("-> Dugumde bulundu: " + formatNode(node) + "  (key index = " + i + ")");
            return node;
        }

        // Yaprak ise ve hala bulunamadıysa, yok
        if (node.leaf) {
            System.out.println("-> Yaprak dugume gelindi, " + key + " bulunamadi. Dugum: " + formatNode(node));
            return null;
        }

        // İç düğüm: hangi çocuğa ineceğimizi göster
        System.out.println("-> " + key + " degeri " + formatNode(node) + " dugumunde bulunamadi, " +
                           (i) + ". cocuga iniliyor.");
        return search(node.children[i], key);
    }

    // Kullanımı kolaylaştırmak için root üzerinden çağıran fonksiyon
    static BTreeNode search(int key) {
        System.out.println("\n=== " + key + " DEGERINI ARIYORUZ ===");
        return search(root, key);
    }

    // -------------------- 5. B-TREE'Yİ KATMAN KATMAN YAZDIRMA --------------------
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

    // Bir düğümü yazdırmak için yardımcı
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

    // Hem ekleyen hem gösteren yardımcı
    static void insertAndShow(int key) {
        System.out.println("==> " + key + " EKLENIYOR...");
        insert(key);
        printBTree(root);
    }

    // -------------------- 6. MAIN --------------------
    public static void main(String[] args) {

        // Önce B-Tree'yi oluşturalım (BTreeInsert örneğindekiyle aynı dizi)
        int[] keys = {10, 20, 5, 6, 12, 30, 7, 17};

        for (int k : keys) {
            insertAndShow(k);
        }

        // Şimdi arama örnekleri yapalım
        search(6);    // olan bir değer
        search(17);   // olan bir değer
        search(15);   // olmayan bir değer
    }
}
