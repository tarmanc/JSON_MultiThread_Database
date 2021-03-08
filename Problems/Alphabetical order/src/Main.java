import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> stringList = new ArrayList<>();
        boolean flag = true;

        while (scanner.hasNext()) {
            String input = scanner.next();

            for (int i = stringList.size() - 1; i >= 0; i--) {
                if (input.compareTo(stringList.get(i)) < 0) {
                    flag = false;
                    break;
                }
            }
            if(!flag){
                break;
            }
            stringList.add(input);
        }

        System.out.println(flag);

    }
}