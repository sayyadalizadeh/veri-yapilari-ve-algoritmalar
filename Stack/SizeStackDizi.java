public class SizeStackDizi {
    // Yığın verisi ve tepe
    static int[] stack=new int[5];
    static int top=-1;

    // Basit push
    static void push(int x){ 
        if(top<stack.length-1) 
        stack[++top]=x; 
    }

    // Eleman sayısı: top + 1
    static int size(){ 
        return top+1; 
    }

    public static void main(String[] args){
        // Örnek kullanım
        System.out.println(size()); // Yiğit Boyutu 0
        push(7); 
        push(8); 
        push(9);
        System.out.println(size()); // Yiğit Boyutu  3
    }
}
