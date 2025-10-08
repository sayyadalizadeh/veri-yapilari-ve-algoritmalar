public class BubbleSort {
    public static void main(String[] args) {
        // Bir dizi oluşturuyoruz
        int[] numbers = {64, 34, 25, 12, 22, 11, 90};

        System.out.println("Original array:");
        for (int n : numbers) {
            System.out.print(n + " ");
        }
        System.out.println();

        // Bubble Sort algoritması
        int n = numbers.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (numbers[j] > numbers[j + 1]) {
                    // Elemanları takas ediyoruz (swap)
                    int temp = numbers[j];
                    numbers[j] = numbers[j + 1];
                    numbers[j + 1] = temp;
                }
            }
        }

        System.out.println("Sorted array (ascending order):");
        for (int num : numbers) {
            System.out.print(num + " ");
        }
    }
}
