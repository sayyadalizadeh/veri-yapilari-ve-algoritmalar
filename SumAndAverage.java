public class SumAndAverage {
    public static void main(String[] args) {
        // Bir dizi oluşturuyoruz
        int[] numbers = {10, 20, 30, 40, 50};

        int sum = 0;

        // Dizideki elemanların toplamını hesaplıyoruz
        for (int n : numbers) {
            sum += n;
        }

        double average = (double) sum / numbers.length;

        System.out.println("Array elements:");
        for (int n : numbers) {
            System.out.print(n + " ");
        }
        System.out.println();
        System.out.println("Sum of elements: " + sum);
        System.out.println("Average of elements: " + average);
    }
}
