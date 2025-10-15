public class PopStackBagliListe {
    // Düğüm tanımı
    static class Node { 
        int v; 
        Node next;

        Node(int v, Node n){ 
            this.v=v; 
            this.next=n; 
        } 
    }
    
    // Tepe düğüm
    static Node top = null;

    // Push: tepeye yeni düğüm bağla
    static void push(int x){ 
        top=new Node(x, top); 
    }

    // Pop: tepedeki düğümü al ve tepeyi bir sonrakine kaydır
    static int pop(){
        // Boş kontrolü
        if(top==null) throw new RuntimeException("Stack bos");
        int x=top.v;
        top=top.next;
        return x;
    }

    public static void main(String[] args){
        // Örnek kullanım
        push(7); 
        push(14); 
        push(21);
        System.out.println(pop()); // 21
        System.out.println(pop()); // 14
    }
}
