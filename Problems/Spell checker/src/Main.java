import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {
            int size = scanner.nextInt();
            HashSet<String> correctWords = new HashSet<>(size);
            for (int i = 0; i < size; i++) {
                correctWords.add(scanner.next().toLowerCase());
            }

            size = scanner.nextInt();
            scanner.nextLine();
            HashSet<String> words = new HashSet<>(size);
            for (int i = 0; i < size; i++) {
                String input = scanner.nextLine().toLowerCase();
                words.addAll(Arrays.asList(input.split(" ")));
            }
            words.removeAll(correctWords);
            words.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}