public class PeekStackBagliListe {
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

    // Push: yeni düğüm tepeye
    static void push(int x){ 
        top=new Node(x, top); 
    }

    // Peek: tepedeki değeri göster
    static int peek(){
        if(top==null) throw new RuntimeException("Stack bos");
        return top.v;
    }

    public static void main(String[] args){
        // Örnek kullanım
        push(4);
        push(8); 
        push(12);
        System.out.println(peek()); // 12
    }
}
