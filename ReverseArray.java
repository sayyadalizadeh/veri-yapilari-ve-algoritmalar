public class ReverseArray {
    public static void main(String[] args) {
        // Bir dizi oluşturuyoruz
        int[] numbers = {10, 20, 30, 40, 50};

        System.out.println("Original array:");
        for (int n : numbers) {
            System.out.print(n + " ");
        }
        System.out.println();

        // Diziyi ters çeviriyoruz
        int start = 0;
        int end = numbers.length - 1;
        while (start < end) {
            int temp = numbers[start];
            numbers[start] = numbers[end];
            numbers[end] = temp;
            start++;
            end--;
        }

        System.out.println("Reversed array:");
        for (int n : numbers) {
            System.out.print(n + " ");
        }
    }
}
