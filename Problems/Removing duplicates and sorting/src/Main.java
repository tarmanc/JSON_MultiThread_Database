import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int arraySize = scanner.nextInt();
        Set<String> set = new TreeSet<>();
        for (int i = 0; i < arraySize; i++) {
            set.add(scanner.next());
        }
        set.forEach(System.out::println);
    }
}