public class PushStackBagliListe {
    // Bağlı liste düğümü: değer ve bir sonraki düğüm
    static class Node { 
        int v; 
        Node next; 

        Node(int v, Node n){ 
            this.v=v; 
            this.next=n; 
        } 
    }
    
    // Yığının tepe düğümü (listenin başı)
    static Node top = null;

    // Yığına eleman ekleme: yeni düğüm oluştur, tepeyi ona taşı
    static void push(int x){ 
        top = new Node(x, top); 
    }

    // Yığını tepe'den aşağıya yazdır
    static void print(){
        System.out.print("[");
        for(Node c=top;c!=null;c=c.next){
            System.out.print(c.v + (c.next==null?"":", "));
        }
        System.out.println("] (top)");
    }

    public static void main(String[] args){
        // Örnek kullanım
        push(5); 
        push(15); 
        push(25);
        print(); // [25, 15, 5] (top)
    }
}
