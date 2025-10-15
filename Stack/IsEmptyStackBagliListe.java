public class IsEmptyStackBagliListe {
    // Düğüm ve tepe
    static class Node{ 
        int v; 
        Node next; 

        Node(int v, Node n){ 
            this.v=v; 
            this.next=n; 
        } 
    }
    static Node top=null;

    // Push: tepeye bağla
    static void push(int x){ 
        top=new Node(x, top); 
    }

    // Boş mu? tepe null ise boştur
    static boolean isEmpty(){ 
        return top==null; 
    }

    public static void main(String[] args){
        // Örnek kullanım
        System.out.println(isEmpty()); // true
        push(1);
        System.out.println(isEmpty()); // false
    }
}
