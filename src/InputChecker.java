import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class InputChecker {
    static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    int[] CheckCell(ArrayList<int[]> arr) {
        String inp;
        int[] ans;
        while (true) {
            try {
                inp = reader.readLine();
                String[] split = inp.split(" ");
                boolean contains = false;
                int num1 = -1, num2 = -1;
                if (split.length == 2 && isNumeric(split[0]) && isNumeric(split[1])) {
                    num1 = Integer.parseInt(split[1]) - 1;
                    num2 = Integer.parseInt(split[0]) - 1;
                    if (num1 >= 0 && num1 < 8 && num2 >= 0 && num2 < 8) {
                        for (int[] pair : arr) {
                            if (pair[0] == num1 && pair[1] == num2) {
                                contains = true;
                                break;
                            }
                        }
                    }
                }

                if (!contains) {
                    System.out.println("Такой ход невозможен, " +
                            "повторите ввод:");
                    continue;
                }
                ans = new int[]{num1, num2};
                break;
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return ans;
    }

    int CheckIntOneTwo() {
        String inp;
        while (true) {
            try {
                inp = reader.readLine();
                if (!isNumeric(inp) || Integer.parseInt(inp) < 1 || Integer.parseInt(inp) > 2) {
                    System.out.println("Введённое число некорректно, повторите ввод:");
                    continue;
                }
                break;
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return Integer.parseInt(inp);
    }
}
