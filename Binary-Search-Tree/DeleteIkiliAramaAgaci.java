import java.util.ArrayList;
import java.util.List;

public class DeleteIkiliAramaAgaci {

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

    // -------------------- 2. EKLEME (BASİT) --------------------
    static Node insert(Node current, int x) {
        if (current == null) {
            return new Node(x); // boş yere geldik, yeni düğüm
        }

        if (x < current.key) {
            current.left = insert(current.left, x);      // sola
        } else if (x > current.key) {
            current.right = insert(current.right, x);    // sağa
        } else {
            System.out.println(x + " zaten agacta var, eklenmedi.");
        }

        return current;
    }

    static void insert(int x) {
        root = insert(root, x);
    }

    // -------------------- 3. MIN BULMA (SAĞ ALT AĞAÇ İÇİN) --------------------
    // Silme işleminde, iki çocuklu düğüm için sağdaki en küçük değeri kullanacağız.
    static Node findMin(Node current) {
        if (current == null) return null;

        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    // -------------------- 4. SİLME İŞLEMİ --------------------
    /*
        3 temel durum:

        1) Silinecek düğüm yaprak (çocuğu yok)        -> direkt null döndür.
        2) Silinecek düğümün tek çocuğu var          -> o çocuğu yukarı taşı.
        3) Silinecek düğümün iki çocuğu var          ->
             - sağ alt ağaçtaki en küçük değeri bul
             - bu değeri bu düğüme kopyala
             - o en küçük düğümü sağ alt ağaçtan sil
     */
    static Node delete(Node current, int x) {
        if (current == null) {
            System.out.println(x + " agacta bulunamadi (null dugume geldik).");
            return null;
        }

        if (x < current.key) {
            current.left = delete(current.left, x);        // sola git
        } else if (x > current.key) {
            current.right = delete(current.right, x);      // sağa git
        } else {
            // x == current.key -> BU DÜĞÜM SİLİNECEK
            System.out.println("Silinecek dugum bulundu: " + current.key);

            // 1) Çocuğu yok (yaprak)
            if (current.left == null && current.right == null) {
                return null;
            }

            // 2) Tek çocuk
            if (current.left == null) {        // sadece sağ çocuk var
                return current.right;
            } else if (current.right == null) { // sadece sol çocuk var
                return current.left;
            }

            // 3) İki çocuk
            // Sağ alt ağaçtaki en küçük değeri bul
            Node minRight = findMin(current.right);
            // Değeri buraya kopyala
            current.key = minRight.key;
            // Kopyaladığımız değeri sağ alt ağaçtan sil
            current.right = delete(current.right, minRight.key);
        }

        return current;  // değişmiş olabilecek alt ağacın kökü
    }

    static void delete(int x) {
        root = delete(root, x);
    }

    // ---------------------------------------------------------
    // 5. AĞACI GÖRSEL GÖRÜNÜM İLE ÇİZME (KÖK YUKARIDA)
    // ---------------------------------------------------------

    static int height(Node current) {
        if (current == null) return 0;
        int leftH = height(current.left);
        int rightH = height(current.right);
        return 1 + Math.max(leftH, rightH);
    }

    static void printSpaces(int n) {
        for (int i = 0; i < n; i++) {
            System.out.print(" ");
        }
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

    // -------------------- 6. MAIN --------------------
    public static void main(String[] args) {

        // Başlangıç ağacı
        insert(50);
        insert(30);
        insert(70);
        insert(20);  // yaprak
        insert(40);
        insert(60);
        insert(80);

        System.out.println("1) ILK AGAC:");
        printTreePretty(root);

        // 1. örnek: YAPRAK düğüm silme (20)
        System.out.println("2) 20 (YAPRAK) degerini siliyoruz...");
        delete(20);
        printTreePretty(root);

        // 2. örnek: TEK ÇOCUKLU düğüm silme
        // Şu anda 30'un tek çocuğu 40 (çünkü 20 silindi).
        System.out.println("3) 30 (TEK COCUKLU) degerini siliyoruz...");
        delete(30);
        printTreePretty(root);

        // 3. örnek: IKI ÇOCUKLU düğüm silme (kök 50)
        System.out.println("4) 50 (IKI COCUKLU ROOT) degerini siliyoruz...");
        delete(50);
        printTreePretty(root);
    }
}
