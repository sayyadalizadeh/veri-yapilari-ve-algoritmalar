public class IsEmptyStackDizi {
    // Yığın verisi ve tepe
    static int[] stack = new int[2];
    static int top = -1;

    // Basit push
    static void push(int x){ 
        if(top<stack.length-1) 
        stack[++top]=x; 
    }

    // Yığın boş mu? top == -1 ise boştur
    static boolean isEmpty(){ 
        return top==-1; 
    }

    public static void main(String[] args){
        // Örnek kullanım
        System.out.println(isEmpty()); // true
        push(42);
        System.out.println(isEmpty()); // false
    }
}
