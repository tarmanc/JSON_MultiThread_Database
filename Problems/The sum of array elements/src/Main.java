import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner((System.in));
        int result = 0;
        int arraySize = scanner.nextInt();
        for (int i = 0; i < arraySize; i++) {
            result += scanner.nextInt();
        }
        System.out.println(result);
    }
}