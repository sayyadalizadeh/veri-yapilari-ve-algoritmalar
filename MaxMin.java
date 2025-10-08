public class MaxMin {
    public static void main(String[] args) {
        // Bir dizi oluşturuyoruz
        int[] numbers = {45, 12, 78, 34, 90, 23};

        int max = numbers[0];
        int min = numbers[0];

        // Dizideki maksimum ve minimum değerleri buluyoruz
        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i] > max) {
                max = numbers[i];
            }
            if (numbers[i] < min) {
                min = numbers[i];
            }
        }

        System.out.println("Array elements:");
        for (int n : numbers) {
            System.out.print(n + " ");
        }
        System.out.println();
        System.out.println("Maximum value: " + max);
        System.out.println("Minimum value: " + min);
    }
}
