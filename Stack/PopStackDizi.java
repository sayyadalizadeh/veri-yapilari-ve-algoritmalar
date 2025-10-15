public class PopStackDizi {
    // Yığın verisi ve tepe göstergesi
    static int[] stack = new int[5];
    static int top = -1;

    // Basit push (kontrollü)
    static void push(int x){ 
        if(top<stack.length-1) 
        stack[++top]=x; 
    }

    // Yığından eleman çıkarma (pop): tepeden bir eleman al
    static int pop(){
        // Boş kontrolü
        if(top==-1) throw new RuntimeException("Stack bos");
        // Önce değeri al, sonra top'u bir azalt
        return stack[top--];
    }

    public static void main(String[] args){
        // Örnek kullanım
        push(1); 
        push(2); 
        push(3);
        System.out.println(pop()); // 3
        System.out.println(pop()); // 2
    }
}
