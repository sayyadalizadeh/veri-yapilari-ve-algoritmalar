import java.util.ArrayList;
import java.util.List;

public class BTreeInsert {

    // -------------------- 1. B-TREE PARAMETRESİ --------------------
    /*
        T = minimum degree (en küçük derece)

        - Her düğümde en fazla 2*T - 1 anahtar
        - En az T - 1 anahtar (kök hariç)
        - Çocuk sayısı = anahtar sayısı + 1  (en fazla 2*T)
     */
    static final int T = 2;   // İstersen 3 yaparak daha büyük bloklar da deneyebilirsin

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

    // -------------------- 3. ÇOCUK BÖLME (SPLIT CHILD) --------------------
    /*
        parent düğümünde, full (dolu) olan child[i] düğümünü ikiye böler.

        Adımlar (standart B-Tree split):
        - child:  [k0, k1, k2]  (T=2 için 3 anahtar)
        - Ortadaki anahtar (k1) parent'a çıkar.
        - Sol kısım (k0) eski child'da kalır.
        - Sağ kısım (k2) yeni düğüm "z" içine alınır.
     */
    static void splitChild(BTreeNode parent, int i) {
        BTreeNode y = parent.children[i];      // dolu olan çocuk
        BTreeNode z = new BTreeNode(y.leaf);   // yeni düğüm (sağ yarı)

        // z düğümüne y'nin son T-1 anahtarlarını taşı
        z.n = T - 1;
        for (int j = 0; j < T - 1; j++) {
            z.keys[j] = y.keys[j + T];
        }

        // Eğer yaprak değilse, çocuklarını da böl
        if (!y.leaf) {
            for (int j = 0; j < T; j++) {
                z.children[j] = y.children[j + T];
            }
        }

        // y'nin anahtar sayısını T-1'e düşür
        y.n = T - 1;

        // parent'in çocuklarını sağa kaydır (y'nin sağ tarafına z için yer aç)
        for (int j = parent.n; j >= i + 1; j--) {
            parent.children[j + 1] = parent.children[j];
        }
        parent.children[i + 1] = z;

        // parent'in anahtarlarını sağa kaydır
        for (int j = parent.n - 1; j >= i; j--) {
            parent.keys[j + 1] = parent.keys[j];
        }

        // y'nin ortadaki anahtarını parent'a koy
        parent.keys[i] = y.keys[T - 1];

        // parent'in anahtar sayısını artır
        parent.n++;
    }

    // -------------------- 4. DOLU OLMAYAN DÜĞÜME EKLEME --------------------
    /*
        Bu fonksiyon, verilen düğümün DOLU OLMADIĞINI (n < 2*T-1) varsayar.
        Bu düğüme (ve altına) uygun şekilde key ekler.
     */
    static void insertNonFull(BTreeNode node, int key) {
        int i = node.n - 1;

        if (node.leaf) {
            // Yaprak düğüm: doğru pozisyonu bulup kaydırarak ekle
            while (i >= 0 && key < node.keys[i]) {
                node.keys[i + 1] = node.keys[i];
                i--;
            }
            node.keys[i + 1] = key;
            node.n++;
        } else {
            // İç düğüm: uygun çocuk altına git
            while (i >= 0 && key < node.keys[i]) {
                i--;
            }
            i++; // çocuk index

            // Eğer gideceğimiz çocuk doluysa önce onu böl
            if (node.children[i].n == 2 * T - 1) {
                splitChild(node, i);

                // Split sonrası, araya giren anahtarı kontrol et
                if (key > node.keys[i]) {
                    i++;
                }
            }
            insertNonFull(node.children[i], key);
        }
    }

    // -------------------- 5. B-TREE'YE EKLEME (KÖK ÜZERİNDEN) --------------------
    /*
        1) Kök boşsa yeni düğüm oluştur ve anahtar ekle.
        2) Kök doluysa:
           - Yeni bir kök düğüm "s" oluştur (leaf = false)
           - s.children[0] = eski root
           - splitChild(s, 0)
           - Sonra insertNonFull(s, key)
           - root = s
     */
    static void insert(int key) {
        if (root == null) {
            root = new BTreeNode(true);
            root.keys[0] = key;
            root.n = 1;
            System.out.println("Kok olusturuldu, " + key + " eklendi.");
            return;
        }

        BTreeNode r = root;

        // Kök doluysa (max anahtar sayısına ulaştıysa)
        if (r.n == 2 * T - 1) {
            BTreeNode s = new BTreeNode(false);  // yeni kök
            s.children[0] = r;
            splitChild(s, 0);

            // Yeni köke göre hangi çocuğa gideceğimize karar ver
            int i = 0;
            if (key > s.keys[0]) {
                i = 1;
            }
            insertNonFull(s.children[i], key);

            root = s;  // yeni kök
        } else {
            // Kök dolu değilse direkt buradan devam
            insertNonFull(r, key);
        }
    }

    // -------------------- 6. B-TREE'Yİ KATMAN KATMAN YAZDIRMA --------------------
    /*
        Her seviyeyi ayrı satırda yazdırır.
        Düğümler şu şekilde gösterilir: [k1 | k2 | k3]
     */
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
                // Düğümün anahtarlarını yaz
                System.out.print("[");
                for (int i = 0; i < n.n; i++) {
                    System.out.print(n.keys[i]);
                    if (i != n.n - 1) System.out.print(" | ");
                }
                System.out.print("]   ");

                // Yaprak değilse çocukları bir sonraki seviyeye ekle
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

    // Küçük yardımcı: hem ekle hem görselleştir
    static void insertAndShow(int key) {
        System.out.println("==> " + key + " EKLENIYOR...");
        insert(key);
        printBTree(root);
    }

    // -------------------- 7. MAIN --------------------
    public static void main(String[] args) {

        // Klasik B-Tree örneği (T=2 için):
        int[] keys = {10, 20, 5, 6, 12, 30, 7, 17};

        for (int k : keys) {
            insertAndShow(k);
        }
    }
}
