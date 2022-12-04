import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketOption;
import java.util.ArrayList;
import java.util.Arrays;

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

    int[] CheckPairInArr(ArrayList<int[]> arr) {
        String inp;
        int[] ans;
        while (true) {
            try {
                inp = reader.readLine();
                String[] split = inp.split(" ");
                boolean contains = false;
                if (isNumeric(split[0]) && isNumeric(split[1]) && split.length == 2) {
                    for (int[] pair : arr) {
                        if (pair[0] == Integer.parseInt(split[0]) && pair[1] == Integer.parseInt(split[1])) {
                            contains = true;
                            break;
                        }
                    }
                }

                if (!contains) {
                    System.out.println("Такой ход невозможен, " +
                            "повторите ввод:");
                    continue;
                }
                ans = new int[]{Integer.parseInt(split[0]), Integer.parseInt(split[1])};
                break;
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return ans;
    }

    int CheckIntGaps(int min, int max) {
        String inp;
        while (true) {
            try {
                inp = reader.readLine();
                if (!isNumeric(inp) || Integer.parseInt(inp) < min || Integer.parseInt(inp) > max) {
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
