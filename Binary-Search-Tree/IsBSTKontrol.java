import java.util.ArrayList;
import java.util.List;

public class IsBSTKontrol {

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

    // -------------------- 2. EKLEME (BASİT BST INSERT) --------------------
    static Node insert(Node current, int x) {
        if (current == null) {
            return new Node(x);
        }

        if (x < current.key) {
            current.left = insert(current.left, x);
        } else if (x > current.key) {
            current.right = insert(current.right, x);
        } else {
            System.out.println(x + " zaten agacta var, eklenmedi.");
        }

        return current;
    }

    static void insert(int x) {
        root = insert(root, x);
    }

    // -------------------- 3. BST KONTROLÜ --------------------
    /*
        Bir ağacın BST olması için:
        - Her düğüm için:
            SOL   < current.key
            SAĞ   > current.key

        Kontrolü "min - max aralığı" yöntemiyle yapıyoruz.
    */
    static boolean isBST(Node current, int min, int max) {
        if (current == null) return true;

        // Eğer düğüm BST kurallarını bozuyorsa
        if (current.key <= min || current.key >= max) {
            return false;
        }

        // Sol çocuk: max sınırı current.key olur
        // Sağ çocuk: min sınırı current.key olur
        return isBST(current.left, min, current.key) &&
               isBST(current.right, current.key, max);
    }

    static boolean isBST() {
        return isBST(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    // ---------------------------------------------------------
    // 4. AĞACI GÖRSEL OLARAK ÇİZME (KÖK YUKARIDA)
    // ---------------------------------------------------------
    static int height(Node current) {
        if (current == null) return 0;
        return 1 + Math.max(height(current.left), height(current.right));
    }

    static void printSpaces(int n) {
        for (int i = 0; i < n; i++) System.out.print(" ");
    }

    static void printTreePretty(Node root) {
        if (root == null) {
            System.out.println("(agac bos)");
            return;
        }

        int h = height(root);

        List<Node> level = new ArrayList<>();
        level.add(root);
        int depth = 0;

        System.out.println("Agacin gorunumu (kök yukarida):");

        while (!level.isEmpty() && depth < h) {
            int spacesBefore = (int)Math.pow(2, h - depth) - 1;
            int spacesBetween = (int)Math.pow(2, h - depth + 1) - 1;

            printSpaces(spacesBefore);

            List<Node> next = new ArrayList<>();

            for (int i = 0; i < level.size(); i++) {
                Node node = level.get(i);

                if (node != null) {
                    System.out.printf("%2d", node.key);
                    next.add(node.left);
                    next.add(node.right);
                } else {
                    System.out.print("  ");
                    next.add(null);
                    next.add(null);
                }

                if (i < level.size() - 1) {
                    printSpaces(spacesBetween);
                }
            }

            System.out.println();

            level = next;
            depth++;
        }

        System.out.println();
    }

    // -------------------- 5. MAIN --------------------
    public static void main(String[] args) {

        // ------------------ DOĞRU BST ------------------
        insert(50);
        insert(30);
        insert(70);
        insert(20);
        insert(40);
        insert(60);
        insert(80);

        System.out.println("1) Dogru BST kontrolu:");
        printTreePretty(root);

        System.out.println("Bu agac BST midir? -> " + isBST());
        System.out.println();

        // ------------------ YANLIŞ BST OLUŞTURALIM ------------------
        Node wrong = new Node(10);
        wrong.left = new Node(5);
        wrong.right = new Node(20);
        wrong.left.right = new Node(15); // HATA! 15 > 10 fakat solda duruyor!

        root = wrong;

        System.out.println("2) Yanlis bir BST örneği:");
        printTreePretty(root);

        System.out.println("Bu agac BST midir? -> " + isBST());
    }
}
