import java.util.ArrayList;
import java.util.List;

public class SplayVsAVLComparison {

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

    // İki ayrı ağaç: AVL ve Splay
    static Node avlRoot = null;
    static Node splayRoot = null;

    // -------------------- 2. ORTAK YARDIMCI (YÜKSEKLİK, ÇİZİM) --------------------

    static int height(Node n) {
        if (n == null) return 0;
        return 1 + Math.max(height(n.left), height(n.right));
    }

    static void printSpaces(int n) {
        for (int i = 0; i < n; i++) System.out.print(" ");
    }

    static void printTreePretty(Node root, String baslik) {
        System.out.println("=== " + baslik + " ===");
        if (root == null) {
            System.out.println("(bos agac)\n");
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

        System.out.println("Yukseklik: " + height(root));
        System.out.println();
    }

    // -------------------- 3. AVL AĞACI KISMI --------------------

    // Balance faktörü (AVL için)
    static int getBalance(Node n) {
        if (n == null) return 0;
        return height(n.left) - height(n.right);
    }

    // Rotasyonlar (AVL)
    static Node rightRotateAVL(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        return x;
    }

    static Node leftRotateAVL(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        return y;
    }

    // AVL insert
    static Node avlInsert(Node node, int key) {
        if (node == null) {
            return new Node(key);
        }

        if (key < node.key) {
            node.left = avlInsert(node.left, key);
        } else if (key > node.key) {
            node.right = avlInsert(node.right, key);
        } else {
            System.out.println("[AVL] " + key + " zaten var, eklenmedi.");
            return node;
        }

        int balance = getBalance(node);

        // LL
        if (balance > 1 && key < node.left.key)
            return rightRotateAVL(node);

        // RR
        if (balance < -1 && key > node.right.key)
            return leftRotateAVL(node);

        // LR
        if (balance > 1 && key > node.left.key) {
            node.left = leftRotateAVL(node.left);
            return rightRotateAVL(node);
        }

        // RL
        if (balance < -1 && key < node.right.key) {
            node.right = rightRotateAVL(node.right);
            return leftRotateAVL(node);
        }

        return node;
    }

    static void avlInsertToRoot(int key) {
        avlRoot = avlInsert(avlRoot, key);
    }

    // AVL arama: sadece BST gibi ilerler, döngü ile adım sayar
    static int avlSearchSteps(int key) {
        int steps = 0;
        Node current = avlRoot;

        System.out.println("[AVL] " + key + " degerini ariyoruz...");
        while (current != null) {
            steps++;
            System.out.println("Adim " + steps + " -> dugum: " + current.key);

            if (key == current.key) {
                System.out.println("[AVL] Bulundu! Toplam adim: " + steps + "\n");
                return steps;
            } else if (key < current.key) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        System.out.println("[AVL] Bulunamadi. Toplam adim: " + steps + "\n");
        return steps;
    }

    // -------------------- 4. SPLAY AĞACI KISMI --------------------

    // Splay rotasyonları
    static Node rightRotateSplay(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        return x;
    }

    static Node leftRotateSplay(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        return y;
    }

    // Splay adım sayacı (rekürsif çağrıları saymak için basit sayaç)
    static int splayStepCounter = 0;

    // Splay işlemi
    static Node splay(Node root, int key) {
        splayStepCounter++; // her çağrıda bir adım say

        if (root == null || root.key == key)
            return root;

        // SOL tarafta
        if (key < root.key) {

            if (root.left == null) return root;

            // Zig-Zig (sol-sol)
            if (key < root.left.key) {
                root.left.left = splay(root.left.left, key);
                root = rightRotateSplay(root);
            }
            // Zig-Zag (sol-sağ)
            else if (key > root.left.key) {
                root.left.right = splay(root.left.right, key);
                if (root.left.right != null)
                    root.left = leftRotateSplay(root.left);
            }

            return (root.left == null) ? root : rightRotateSplay(root);
        }
        // SAĞ tarafta
        else {

            if (root.right == null) return root;

            // Zig-Zig (sağ-sağ)
            if (key > root.right.key) {
                root.right.right = splay(root.right.right, key);
                root = leftRotateSplay(root);
            }
            // Zig-Zag (sağ-sol)
            else if (key < root.right.key) {
                root.right.left = splay(root.right.left, key);
                if (root.right.left != null)
                    root.right = rightRotateSplay(root.right);
            }

            return (root.right == null) ? root : leftRotateSplay(root);
        }
    }

    // Splay insert
    static Node splayInsert(Node root, int key) {
        if (root == null) {
            return new Node(key);
        }

        root = splay(root, key);

        if (root.key == key) {
            System.out.println("[SPLAY] " + key + " zaten var, eklenmedi.");
            return root;
        }

        Node yeni = new Node(key);

        if (key < root.key) {
            yeni.right = root;
            yeni.left = root.left;
            root.left = null;
        } else {
            yeni.left = root;
            yeni.right = root.right;
            root.right = null;
        }

        return yeni;
    }

    static void splayInsertToRoot(int key) {
        splayRoot = splayInsert(splayRoot, key);
    }

    // Splay arama: splay + adım sayacı
    static int splaySearchSteps(int key) {
        if (splayRoot == null) {
            System.out.println("[SPLAY] Agac bos, " + key + " bulunamadi.\n");
            return 0;
        }

        System.out.println("[SPLAY] " + key + " degerini ariyoruz (splay search)...");
        splayStepCounter = 0;
        splayRoot = splay(splayRoot, key);
        printTreePretty(splayRoot, "SPLAY agaci (arama sonrasi)");

        if (splayRoot.key == key) {
            System.out.println("[SPLAY] Bulundu ve köke tasindi. Splay adim sayisi: " + splayStepCounter + "\n");
        } else {
            System.out.println("[SPLAY] Agacta yok, en yakin dugum köke geldi: " + splayRoot.key +
                               " | Splay adim sayisi: " + splayStepCounter + "\n");
        }

        return splayStepCounter;
    }

    // -------------------- 5. MAIN: AVL vs SPLAY KARŞILAŞTIRMA --------------------

    public static void main(String[] args) {

        System.out.println("===== AVL vs SPLAY Comparison =====\n");

        // SIRALI DİZİ: AVL dengeli kalırken, normal BST çok uzar.
        // Splay ağacı ise erişime göre kendini ayarlıyor.
        int[] dizi = {10, 20, 30, 40, 50, 60, 70, 80, 90};

        // Aynı elemanları hem AVL hem Splay'e ekleyelim
        for (int x : dizi) {
            avlInsertToRoot(x);
            splayInsertToRoot(x);
        }

        // Başlangıçta iki ağacı çiz ve yüksekliklerini göster
        printTreePretty(avlRoot, "AVL AGACI (eklemelerden sonra)");
        printTreePretty(splayRoot, "SPLAY AGACI (eklemelerden sonra)");

        System.out.println("AVL yuksekligi  : " + height(avlRoot));
        System.out.println("SPLAY yuksekligi: " + height(splayRoot));
        System.out.println();

        // Şimdi SIK ERİŞİLEN bir elemanı (örneğin 80) karşılaştıralım
        int hotKey = 80;

        System.out.println("=== 1. ARAMA TURU (hot key = " + hotKey + ") ===");
        int avlSteps1 = avlSearchSteps(hotKey);
        int splaySteps1 = splaySearchSteps(hotKey);

        System.out.println("=== 2. ARAMA TURU (hot key = " + hotKey + ") ===");
        int avlSteps2 = avlSearchSteps(hotKey);   // AVL yapısı değişmez
        int splaySteps2 = splaySearchSteps(hotKey); // Splay'de ilk aramadan sonra 80 köke çok yakın

        // ÖZET
        System.out.println("===== OZET =====");
        System.out.println("AVL yuksekligi  : " + height(avlRoot));
        System.out.println("SPLAY yuksekligi: " + height(splayRoot));
        System.out.println();

        System.out.println(hotKey + " icin 1. aramada:");
        System.out.println("  AVL adim sayisi   = " + avlSteps1);
        System.out.println("  SPLAY adim sayisi = " + splaySteps1);
        System.out.println(hotKey + " icin 2. aramada:");
        System.out.println("  AVL adim sayisi   = " + avlSteps2);
        System.out.println("  SPLAY adim sayisi = " + splaySteps2);

        System.out.println("\nGozlem:");
        System.out.println("- AVL dengeli kalir, arama adim sayisi her seferinde benzer.");
        System.out.println("- Splay agaci ise, sik erisilen " + hotKey +
                           " degerini öne cekerek ikinci aramada genelde daha az is yapar.");
    }
}
