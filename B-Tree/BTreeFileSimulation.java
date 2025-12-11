import java.util.ArrayList;
import java.util.List;

/**
 * BTreeFileSimulation.java
 *
 * Amaç:
 *   - B-Tree düğümlerini DISK SAYFASI gibi düşünmek
 *   - Her düğüm için bir "sayfa numarası (pageId)" vermek
 *   - Arama (search) işlemi sırasında kaç tane "disk erişimi" (sayfa okuma) olduğunu saymak
 *
 * Not:
 *   T = 2 (minimum degree) -> 2-3-4 ağacı davranışı
 */
public class BTreeFileSimulation {

    // -------------------- 1. B-TREE PARAMETRESİ --------------------
    static final int T = 2;   // minimum degree

    // -------------------- 2. DÜĞÜM SINIFI --------------------
    static class BTreeNode {
        int[] keys;
        BTreeNode[] children;
        int n;            // anahtar sayısı
        boolean leaf;     // yaprak mı?

        int pageId;       // bu düğümün disk sayfa numarası

        BTreeNode(boolean leaf, int pageId) {
            this.leaf = leaf;
            this.keys = new int[2 * T - 1];
            this.children = new BTreeNode[2 * T];
            this.n = 0;
            this.pageId = pageId;
        }
    }

    // Kök düğüm
    static BTreeNode root = null;

    // Sayfa numarası için sayaç (her yeni düğüme artan pageId verilecek)
    static int nextPageId = 1;

    // Simüle edilen disk erişim sayısı
    static int diskAccessCount = 0;

    // -------------------- 3. "DISK OKUMA" SİMÜLASYONU --------------------
    /*
        Gerçekte B-Tree disk üzerinde tutulur.
        Her düğüm bir "disk sayfası" gibi düşünülür.
        readPage(node) çağırdığımızda:
          - diskAccessCount++ yapılır
          - hangi sayfanın okunduğu ekrana yazılır
     */
    static void readPage(BTreeNode node) {
        if (node == null) return;
        diskAccessCount++;
        System.out.println("Disk erisimi #" + diskAccessCount +
                           " -> Sayfa " + node.pageId + " okundu. Icerik: " + formatNode(node));
    }

    // -------------------- 4. YENİ SAYFA (DÜĞÜM) OLUŞTURMA --------------------
    static BTreeNode newNode(boolean leaf) {
        int pid = nextPageId++;
        System.out.println("Yeni sayfa olusturuldu: pageId = " + pid + " (leaf = " + leaf + ")");
        return new BTreeNode(leaf, pid);
    }

    // -------------------- 5. INSERT (B-Tree standart) --------------------

    static void splitChild(BTreeNode parent, int i) {
        BTreeNode y = parent.children[i];
        BTreeNode z = newNode(y.leaf);  // sağ yarı için yeni sayfa

        // z düğümüne y'nin son T-1 anahtarlarını taşı
        z.n = T - 1;
        for (int j = 0; j < T - 1; j++) {
            z.keys[j] = y.keys[j + T];
        }

        // Yaprak değilse, çocuklarını da taşı
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

        System.out.println("splitChild: parent pageId=" + parent.pageId +
                           ", child pageId=" + y.pageId +
                           ", new right child pageId=" + z.pageId);
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
            root = newNode(true);
            root.keys[0] = key;
            root.n = 1;
            System.out.println("Kok sayfaya " + key + " eklendi. (pageId=" + root.pageId + ")");
            return;
        }

        BTreeNode r = root;
        if (r.n == 2 * T - 1) {
            // Kök dolu -> yeni kök oluştur, split et
            BTreeNode s = newNode(false);
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

    // -------------------- 6. SEARCH (DISK ERİŞİMLİ) --------------------
    /*
        Arama algoritması (disk simülasyonu):

        search(node, key):

          1) readPage(node) → bu düğümü diskte okuduk say.
          2) Anahtarlar arasında sırayla ara:
               - key > keys[i] oldukça i++
               - ilk kez key <= keys[i] olduğunda dur.
          3) Eğer keys[i] == key ise bulundu.
          4) Eğer leaf ise ve bulunamadıysa, yok.
          5) Değilse, uygun child[i] altına inip search(child[i], key) çağır.
     */

    static BTreeNode search(BTreeNode node, int key) {
        if (node == null) {
            System.out.println("Agac bos, " + key + " bulunamadi.");
            return null;
        }

        // Bu düğümü "diskten okuduk"
        readPage(node);

        int i = 0;

        // Anahtarlar arasında ilerle
        while (i < node.n && key > node.keys[i]) {
            i++;
        }

        // Bulduysak
        if (i < node.n && key == node.keys[i]) {
            System.out.println("   -> " + key + " bulundu. (pageId=" + node.pageId + ", index=" + i + ")");
            return node;
        }

        // Yaprak ve hala bulamadıysak: yok
        if (node.leaf) {
            System.out.println("   -> Yaprak sayfaya gelindi, " + key + " bulunamadi. (pageId=" + node.pageId + ")");
            return null;
        }

        // İç düğüm: hangi çocuğa ineceğimizi ekrana yaz
        System.out.println("   -> " + key + " icin uygun aralik " + i +
                           ". cocukta, bu cocuga iniliyor. (parent pageId=" + node.pageId + ")");
        return search(node.children[i], key);
    }

    static BTreeNode search(int key) {
        System.out.println("\n=== " + key + " DEGERINI ARAMA BASLADI ===");
        diskAccessCount = 0;  // her aramada sayaç sıfırlansın
        BTreeNode result = search(root, key);
        System.out.println("=== Arama bitti. Toplam DISK ERISIMI (sayfa okuma): " + diskAccessCount + " ===\n");
        return result;
    }

    // -------------------- 7. AĞACI SAYFALARLA BİRLİKTE YAZDIRMA --------------------

    static String formatNode(BTreeNode n) {
        StringBuilder sb = new StringBuilder();
        sb.append("p").append(n.pageId).append(": [");
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

        System.out.println("B-Tree gorunumu (sayfa ID'leri ile, kök yukarida):");

        List<BTreeNode> currentLevel = new ArrayList<BTreeNode>();
        currentLevel.add(node);
        int level = 0;

        while (!currentLevel.isEmpty()) {
            System.out.print("Seviye " + level + ": ");

            List<BTreeNode> nextLevel = new ArrayList<BTreeNode>();

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
        System.out.println("\n==> " + key + " EKLENIYOR...");
        insert(key);
        printBTree(root);
    }

    // -------------------- 8. MAIN --------------------
    public static void main(String[] args) {

        // Örnek B-Tree (diğer dosyalardaki ile aynı dizi)
        int[] values = {10, 20, 5, 6, 12, 30, 7, 17};

        System.out.println("=== B-Tree DISK SAYFALAMA SIMULASYONU ===");
        for (int v : values) {
            insertAndShow(v);
        }

        // Bazı aramalar yapalım ve kaç disk erişimi gerektiğine bakalım
        search(6);    // olan bir değer
        search(17);   // olan bir değer
        search(25);   // olmayan bir değer
    }
}
