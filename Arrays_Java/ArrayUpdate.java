public class ArrayUpdate {
    public static void main(String[] args) {
        // Başlangıçta bir dizi oluşturuyoruz
        int[] numbers = {5, 10, 15, 20, 25};

        System.out.println("Original array:");
        for (int n : numbers) {
            System.out.print(n + " ");
        }

        // Dizideki 2. indeksteki değeri güncelliyoruz
        numbers[2] = 100;

        System.out.println("\nUpdated array:");
        for (int n : numbers) {
            System.out.print(n + " ");
        }
    }
}
