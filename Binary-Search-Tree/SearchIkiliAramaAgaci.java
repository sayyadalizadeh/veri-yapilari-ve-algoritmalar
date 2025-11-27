import java.util.ArrayList;
import java.util.List;

public class SearchIkiliAramaAgaci {

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

    // -------------------- 2. EKLEME (AYNI MANTIK) --------------------
    static Node insert(Node current, int x) {
        if (current == null) {
            return new Node(x);
        }

        if (x < current.key) {
            current.left = insert(current.left, x);
        } else if (x > current.key) {
            current.right = insert(current.right, x);
        } else {
            // aynı değeri tekrar eklemiyoruz
            System.out.println(x + " zaten agacta var, eklenmedi.");
        }

        return current;
    }

    static void insert(int x) {
        root = insert(root, x);
    }

    // -------------------- 3. ARAMA (REKÜRSİF) --------------------
    // Bulursa düğümü, bulamazsa null döner.
    static Node search(Node current, int x) {
        if (current == null) {
            System.out.println(x + " bulunamadi (null dugume geldik).");
            return null;
        }

        System.out.println("Dugumdeyiz: " + current.key);

        if (x == current.key) {
            System.out.println(x + " bulundu!");
            return current;
        }

        if (x < current.key) {
            System.out.println(x + " < " + current.key + "  -> SOLA git");
            return search(current.left, x);
        } else {
            System.out.println(x + " > " + current.key + "  -> SAGA git");
            return search(current.right, x);
        }
    }

    // -------------------- 4. (İSTEĞE BAĞLI) ARAMA (İTERATİF) --------------------
    // Aynı işi döngü ile de yapabiliriz.
    static Node searchIterative(int x) {
        Node current = root;

        while (current != null) {
            System.out.println("Dugumdeyiz: " + current.key);

            if (x == current.key) {
                System.out.println(x + " bulundu!");
                return current;
            } else if (x < current.key) {
                System.out.println(x + " < " + current.key + "  -> SOLA git");
                current = current.left;
            } else {
                System.out.println(x + " > " + current.key + "  -> SAGA git");
                current = current.right;
            }
        }

        System.out.println(x + " bulunamadi (null dugume geldik).");
        return null;
    }

    // ---------------------------------------------------------
    // 5. AĞACI GÖRSEL GÖRÜNÜM İLE ÇİZME (SADECE GÖRSEL AMAÇLI)
    //    Derste istersen bu kısmı "hazır çizim fonksiyonu" diye
    //    kullanıp detayına girmeyebilirsin.
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

        // Örnek bir ağaç oluşturalım
        insert(50);
        insert(30);
        insert(70);
        insert(20);
        insert(40);
        insert(60);
        insert(80);

        // Ağacı çizelim
        printTreePretty(root);

        // 1) Ağaçta olan bir değeri arayalım
        int aranan1 = 60;
        System.out.println("=== " + aranan1 + " degerini (REKÜRSİF) ariyoruz ===");
        search(root, aranan1);
        System.out.println();

        // 2) Ağaçta olmayan bir değeri arayalım
        int aranan2 = 25;
        System.out.println("=== " + aranan2 + " degerini (ITERATIF) ariyoruz ===");
        searchIterative(aranan2);
        System.out.println();
    }
}
