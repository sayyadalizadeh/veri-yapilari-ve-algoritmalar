import java.util.ArrayList;
import java.util.List;

public class HeightIkiliAramaAgaci {

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
            current.left = insert(current.left, x);      // sola git
        } else if (x > current.key) {
            current.right = insert(current.right, x);    // sağa git
        } else {
            System.out.println(x + " zaten agacta var, eklenmedi.");
        }

        return current;
    }

    static void insert(int x) {
        root = insert(root, x);
    }

    // -------------------- 3. AĞAÇ YÜKSEKLİĞİ --------------------
    /*
       Tanım:
       - Boş ağaç için yükseklik = 0
       - Tek köklü ağaç için yükseklik = 1
       - Genel olarak: 1 + max(sol yükseklik, sağ yükseklik)
     */
    static int height(Node current) {
        if (current == null) return 0; // boşsa 0
        int leftH = height(current.left);
        int rightH = height(current.right);
        return 1 + Math.max(leftH, rightH);
    }

    static int height() {
        return height(root);
    }

    // ---------------------------------------------------------
    // 4. AĞACI GÖRSEL GÖRÜNÜM İLE ÇİZME (KÖK YUKARIDA)
    //    (Sadece gösterim için)
    // ---------------------------------------------------------
    static void printSpaces(int n) {
        for (int i = 0; i < n; i++) System.out.print(" ");
    }

    static int heightForPretty(Node current) {
        if (current == null) return 0;
        int leftH = heightForPretty(current.left);
        int rightH = heightForPretty(current.right);
        return 1 + Math.max(leftH, rightH);
    }

    static void printTreePretty(Node root) {
        if (root == null) {
            System.out.println("(agac bos)");
            return;
        }

        int h = heightForPretty(root);
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
                    System.out.print("  "); // boş düğüm
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

    // -------------------- 5. MAIN --------------------
    public static void main(String[] args) {

        // 0) Başlangıçta ağaç boş
        System.out.println("0) Bos agacin yuksekligi: " + height());

        // 1) Birkaç eleman ekleyelim (daha küçük bir ağaç)
        insert(50);
        insert(30);
        insert(70);

        System.out.println("\n1) 50, 30, 70 eklendikten sonra:");
        printTreePretty(root);
        System.out.println("Agacin yuksekligi: " + height()); // 2 seviye (root + çocuklar)

        // 2) Ağacı biraz daha büyütelim
        insert(20);
        insert(40);
        insert(60);
        insert(80);

        System.out.println("\n2) 20, 40, 60, 80 de eklendi:");
        printTreePretty(root);
        System.out.println("Agacin yuksekligi: " + height()); // 3 seviye

        // 3) Bir tarafı derinleştirelim (yükseklik artsın)
        insert(10); // 20'nin soluna gelecek

        System.out.println("\n3) 10 eklendi (sol taraf derinlesti):");
        printTreePretty(root);
        System.out.println("Agacin yuksekligi: " + height()); // 4 seviye
    }
}
