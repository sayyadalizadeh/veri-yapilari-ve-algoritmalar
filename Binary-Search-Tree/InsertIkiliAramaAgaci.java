import java.util.ArrayList;
import java.util.List;

public class InsertIkiliAramaAgaci {

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
    // Sadece ikili arama ağacına eleman ekler.
    static Node insert(Node current, int x) {
        if (current == null) {
            return new Node(x);  // boş yere geldik, yeni düğüm
        }

        if (x < current.key) {
            current.left = insert(current.left, x);     // sola
        } else if (x > current.key) {
            current.right = insert(current.right, x);   // sağa
        } else {
            System.out.println(x + " zaten agacta var, eklenmedi.");
        }

        return current;  // bu alt ağacın kökü
    }

    // Kök üzerinden çağırmak için kısa versiyon
    static void insert(int x) {
        root = insert(root, x);
    }

    // ---------------------------------------------------------
    // 3. AĞACI GÜZEL GÖRÜNÜM İLE ÇİZME (SADECE GÖRSEL AMAÇLI)
    //    Bu kısmı istersen derste detaylı anlatmayabilirsin.
    // ---------------------------------------------------------

    // Ağacın yüksekliği (kaç seviye)
    static int height(Node current) {
        if (current == null) return 0;
        int leftH = height(current.left);
        int rightH = height(current.right);
        return 1 + Math.max(leftH, rightH);
    }

    // Belirli sayıda boşluk bas
    static void printSpaces(int n) {
        for (int i = 0; i < n; i++) {
            System.out.print(" ");
        }
    }

    // Ağacı kök yukarıda olacak şekilde, seviyeler halinde yazdır
    static void printTreePretty(Node root) {
        if (root == null) {
            System.out.println("(agac bos)");
            return;
        }

        int h = height(root); // seviye sayısı

        List<Node> currentLevel = new ArrayList<>();
        currentLevel.add(root);
        int level = 0;

        System.out.println("Agacin gorunumu (kök yukarida):");

        while (!currentLevel.isEmpty() && level < h) {

            // Seviyeye göre baştaki ve aradaki boşluklar
            int spacesBefore = (int) Math.pow(2, h - level) - 1;
            int spacesBetween = (int) Math.pow(2, h - level + 1) - 1;

            printSpaces(spacesBefore);

            List<Node> nextLevel = new ArrayList<>();

            for (int i = 0; i < currentLevel.size(); i++) {
                Node node = currentLevel.get(i);

                if (node != null) {
                    // Her sayı için 2 karakterlik alan ayır (tek/çift haneli için)
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

            System.out.println();  // satır sonu
            currentLevel = nextLevel;
            level++;
        }

        System.out.println();
    }

    // -------------------- 4. MAIN --------------------
    public static void main(String[] args) {

        // Başlangıç ağacı: 80 YOK
        insert(50);
        insert(30);
        insert(70);
        insert(20);
        insert(40);
        insert(60);

        System.out.println("1) 80 EKLENMEDEN ÖNCE agacin hali:");
        printTreePretty(root);   // önceki görüntü

        // Şimdi 80’i ekleyelim
        System.out.println("2) 80 degerini ekliyoruz...");
        insert(80);

        System.out.println("3) 80 EKLENDİKTEN SONRA agacin hali:");
        printTreePretty(root);   // sonraki görüntü
    }
}
