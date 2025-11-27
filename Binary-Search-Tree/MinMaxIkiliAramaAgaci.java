import java.util.ArrayList;
import java.util.List;

public class MinMaxIkiliAramaAgaci {

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

    // -------------------- 3. MIN ve MAX BULMA --------------------
    // En küçük değeri veren düğüm: en soldaki düğüme kadar git
    static Node findMin(Node current) {
        if (current == null) return null;

        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    // En büyük değeri veren düğüm: en sağdaki düğüme kadar git
    static Node findMax(Node current) {
        if (current == null) return null;

        while (current.right != null) {
            current = current.right;
        }
        return current;
    }

    // (İstersen ders için gösterebileceğin rekürsif versiyonlar)
    static Node findMinRecursive(Node current) {
        if (current == null) return null;
        if (current.left == null) return current;
        return findMinRecursive(current.left);
    }

    static Node findMaxRecursive(Node current) {
        if (current == null) return null;
        if (current.right == null) return current;
        return findMaxRecursive(current.right);
    }

    // ---------------------------------------------------------
    // 4. AĞACI GÖRSEL GÖRÜNÜM İLE ÇİZME (KÖK YUKARIDA)
    //    (Sadece gösterim için, istersen derste detayına girme)
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

    // -------------------- 5. MAIN --------------------
    public static void main(String[] args) {

        // Ağaç oluşturalım
        insert(50);
        insert(30);
        insert(70);
        insert(20);
        insert(40);
        insert(60);
        insert(80);

        printTreePretty(root);

        // MIN & MAX (iteratif fonksiyonlarla)
        Node minNode = findMin(root);
        Node maxNode = findMax(root);

        if (minNode != null) {
            System.out.println("En kucuk deger (min): " + minNode.key);
        }
        if (maxNode != null) {
            System.out.println("En buyuk deger (max): " + maxNode.key);
        }

        // Aynı sonuçları rekürsif fonksiyonlarla da gösterebiliriz
        Node minRec = findMinRecursive(root);
        Node maxRec = findMaxRecursive(root);
        System.out.println("Rekursif MIN: " + (minRec != null ? minRec.key : "yok"));
        System.out.println("Rekursif MAX: " + (maxRec != null ? maxRec.key : "yok"));
    }
}
