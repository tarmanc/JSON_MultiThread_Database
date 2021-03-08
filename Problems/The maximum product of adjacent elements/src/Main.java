import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int arraySize = scanner.nextInt();
        int firstBiggestNum = 1;
        int secondBiggestNum = 1;

        for (int i = 0; i < arraySize; i++) {
            int input = scanner.nextInt();

            if (input > firstBiggestNum) {
                firstBiggestNum = input;
            } else if (input > secondBiggestNum) {
                secondBiggestNum = input;
            }
        }
        System.out.println(firstBiggestNum * secondBiggestNum);
    }
}