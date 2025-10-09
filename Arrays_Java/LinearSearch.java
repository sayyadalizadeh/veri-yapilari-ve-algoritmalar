public class LinearSearch {
    public static void main(String[] args) {
        // Bir dizi oluşturuyoruz
        int[] numbers = {5, 10, 15, 20, 25};

        int target = 20; // Aranacak sayı
        boolean found = false;

        // Dizide lineer arama yapıyoruz
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] == target) {
                System.out.println("Element " + target + " found at index " + i);
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Element " + target + " not found in the array");
        }
    }
}
