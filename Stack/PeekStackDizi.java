public class PeekStackDizi {
    // Yığın verisi ve tepe
    static int[] stack = new int[5];
    static int top = -1;

    // Basit push
    static void push(int x){ 
        if(top<stack.length-1) 
        stack[++top]=x; 
    }

    // Peek: tepede hangi değer var (çıkarmadan göster)
    static int peek(){
        if(top==-1) throw new RuntimeException("Stack bos");
        return stack[top];
    }

    public static void main(String[] args){
        // Örnek kullanım
        push(3); 
        push(6); 
        push(9);
        System.out.println(peek());      // 9
        System.out.println(peek());      // 9 (değişmez)
    }
}
