import java.util.ArrayList;
import java.util.List;

public class AVLDeleteDengeliAgac {

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

    // -------------------- 2. YARDIMCI FONKSİYONLAR --------------------

    // Yükseklik
    static int height(Node n) {
        if (n == null) return 0;
        return 1 + Math.max(height(n.left), height(n.right));
    }

    // Balance (denge) faktörü = sol yükseklik - sağ yükseklik
    static int getBalance(Node n) {
        if (n == null) return 0;
        return height(n.left) - height(n.right);
    }

    // Sağ rotasyon (Right Rotation)
    static Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        return x; // yeni kök
    }

    // Sol rotasyon (Left Rotation)
    static Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        return y; // yeni kök
    }

    // -------------------- 3. AVL INSERT (örnek ağaç oluşturmak için) --------------------
    static Node insert(Node node, int key) {
        if (node == null) {
            return new Node(key);       // normal BST ekleme
        }

        if (key < node.key) {
            node.left = insert(node.left, key);
        } else if (key > node.key) {
            node.right = insert(node.right, key);
        } else {
            System.out.println(key + " zaten agacta var, eklenmedi.");
            return node;
        }

        int balance = getBalance(node);

        // LL
        if (balance > 1 && key < node.left.key) {
            return rightRotate(node);
        }
        // RR
        if (balance < -1 && key > node.right.key) {
            return leftRotate(node);
        }
        // LR
        if (balance > 1 && key > node.left.key) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        // RL
        if (balance < -1 && key < node.right.key) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    static void insertToRoot(int key) {
        root = insert(root, key);
    }

    // -------------------- 4. AVL DELETE --------------------

    // Sağ alt ağaçtaki en küçük elemanı bul (BST silmede klasik)
    static Node findMin(Node node) {
        if (node == null) return null;
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    /*
        AVL silme adımları:

        1) Normal BST'ye göre sil:
           - Yaprak
           - Tek çocuklu
           - İki çocuklu (sağdan en küçük ile değiştir)
        2) Geri dönerken her düğüm için balance hesabı yap.
        3) Dengesizse 4 durum:

           balance >  1  ve  sol cocugun balance >= 0  -> LL  (Right Rotate)
           balance >  1  ve  sol cocugun balance <  0  -> LR  (Left + Right)

           balance < -1  ve  sag cocugun balance <= 0  -> RR  (Left Rotate)
           balance < -1  ve  sag cocugun balance >  0  -> RL  (Right + Left)
     */
    static Node delete(Node node, int key) {
        // 1) Normal BST silme
        if (node == null) {
            System.out.println(key + " agacta bulunamadi.");
            return null;
        }

        if (key < node.key) {
            node.left = delete(node.left, key);
        } else if (key > node.key) {
            node.right = delete(node.right, key);
        } else {
            // Silinecek düğüm bulundu
            System.out.println("Silinecek dugum: " + node.key);

            // a) Çocuğu yok
            if (node.left == null && node.right == null) {
                return null;
            }
            // b) Tek çocuk
            else if (node.left == null) {        // sadece sağ çocuk
                return node.right;
            } else if (node.right == null) {     // sadece sol çocuk
                return node.left;
            }
            // c) İki çocuk
            else {
                Node minRight = findMin(node.right);   // sağdan en küçük
                node.key = minRight.key;               // değeri buraya kopyala
                node.right = delete(node.right, minRight.key); // kopyalanan yeri sil
            }
        }

        // Buraya gelindiyse node boş değil (null değil)
        // 2) Balance hesapla
        int balance = getBalance(node);

        // 3) Dengesiz durumlardan hangisi olduğuna bak

        // SOL ağır
        if (balance > 1) {
            int leftBalance = getBalance(node.left);

            // LL
            if (leftBalance >= 0) {
                System.out.println("LL dengesizligi (delete sonrasi) -> RIGHT ROTATION (pivot: " + node.key + ")");
                return rightRotate(node);
            }
            // LR
            else {
                System.out.println("LR dengesizligi (delete sonrasi) -> LEFT + RIGHT ROTATION (pivot: " + node.key + ")");
                node.left = leftRotate(node.left);
                return rightRotate(node);
            }
        }

        // SAĞ ağır
        if (balance < -1) {
            int rightBalance = getBalance(node.right);

            // RR
            if (rightBalance <= 0) {
                System.out.println("RR dengesizligi (delete sonrasi) -> LEFT ROTATION (pivot: " + node.key + ")");
                return leftRotate(node);
            }
            // RL
            else {
                System.out.println("RL dengesizligi (delete sonrasi) -> RIGHT + LEFT ROTATION (pivot: " + node.key + ")");
                node.right = rightRotate(node.right);
                return leftRotate(node);
            }
        }

        // Dengeli ise olduğu gibi dön
        return node;
    }

    static void deleteFromRoot(int key) {
        System.out.println("==== " + key + " DEGERI SILINIYOR ====");
        root = delete(root, key);
        printTreePretty(root);
    }

    // -------------------- 5. AĞACI GÖRSEL GÖSTERME --------------------
    static void printSpaces(int n) {
        for (int i = 0; i < n; i++) System.out.print(" ");
    }

    static void printTreePretty(Node root) {
        if (root == null) {
            System.out.println("(agac bos)");
            System.out.println();
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

        // Bir AVL ağacı oluşturalım
        int[] dizi = {50, 30, 70, 20, 40, 60, 80, 10, 25, 35, 45};
        for (int x : dizi) {
            root = insert(root, x);
        }

        System.out.println("ILK AVL AGACI:");
        printTreePretty(root);

        // 1) Yaprak silme örneği
        deleteFromRoot(10);

        // 2) Tek çocuklu silme örneği (örneğin 25 veya 80 duruma göre)
        deleteFromRoot(25);

        // 3) İki çocuklu silme örneği (örneğin 30 veya 50)
        deleteFromRoot(30);

        // İstersen daha fazla silme örneği ekleyebilirsin
        // deleteFromRoot(50);
    }
}
