public class ArrayCreation {
    public static void main(String[] args) {
        // Bir dizi (array) oluşturuyoruz
        int[] numbers = {10, 20, 30, 40, 50};

        // Dizinin elemanlarını ekrana yazdırıyoruz
        System.out.println("Array elemanları:");
        for (int i = 0; i < numbers.length; i++) {
            System.out.println("İndeks " + i + ": " + numbers[i]);
        }
    }
}
