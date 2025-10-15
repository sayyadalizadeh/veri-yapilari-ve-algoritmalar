public class SizeStackBagliListe {
    // Düğüm, tepe ve sayaç
    static class Node{ 
        int v; 
        Node next; 

        Node(int v, Node n){ 
            this.v=v; 
            this.next=n; 
        } 
    }
    static Node top=null;
    static int count=0;

    // Push: tepeye bağla ve sayaç artır
    static void push(int x){ 
        top=new Node(x, top); 
        count++; 
    }

    // Eleman sayısı: sayaç
    static int size(){ 
        return count; 
    }

    public static void main(String[] args){
        // Örnek kullanım
        System.out.println(size()); // Yiğit Boyutu 0
        push(10); 
        push(20);
        System.out.println(size()); // Yiğit Boyutu 2
    }
}
