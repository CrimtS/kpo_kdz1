import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Game {
    Player player1;
    Player player2;
    AI ai;
    InputChecker checker = new InputChecker();
    boolean multiplayer = false;
    Scanner scanner = new Scanner(System.in);
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    int[][] field = new int[8][8];
    char[] dotVariants = {' ', '○', '●'};

    boolean GameEnded() {
        for (int[] arr : field) {
            if (Arrays.asList(arr).contains(0)) {
                return false;
            }
        }
        return true;
    }

    void changeField(int[] arr, int value) {
        field[arr[0]][arr[1]] = value;
    }

    void PlayerTurn(Player player) {
        ArrayList<ArrayList<int[]>> possibleClosedCells = new ArrayList<>(0);
        DrawPossible(player.GetPossibleTurns(field, possibleClosedCells), player.ID);
        DrawTurn();
    }

    void AITurn() {
        changeField(ai.MakeTurn(), 2);
        DrawTurn();
    }

    void Start() {
        System.out.println("Добро пожаловать в Реверси! Введите количество игроков:");
        if (checker.CheckIntGaps(1, 2) == 1) {
            System.out.println("Выберите уровень сложности: 1- Новичок, 2- Профессионал");
            ai = new AI(checker.CheckIntGaps(1, 2));
            System.out.println("Введите имя игрока:");
            player1 = new Player(scanner.nextLine(), 1);
        } else {
            multiplayer = true;
            System.out.println("Введите имя первого игрока:");
            player1 = new Player(scanner.nextLine(), 1);
            System.out.println("Введите имя второго игрока:");
            player2 = new Player(scanner.nextLine(), 2);
        }
        while (true) {
            PlayerTurn(player1);
            if (GameEnded()) {
                break;
            }
            if (multiplayer) {
                PlayerTurn(player2);
            } else {
                AITurn();
            }
            if (GameEnded()) {
                break;
            }
        }

    }

    void DrawTurn() {
        System.out.println("+-----+-----+-----+-----+-----+-----+-----+-----+");
        for (int i = 0; i < 8; i++) {
            StringBuilder lineOfDots = new StringBuilder();
            for (int num : field[i]) {
                lineOfDots.append("|  ").append(dotVariants[num]).append("  ");
            }
            System.out.println(lineOfDots.append("|").append(ANSI_RESET));
            System.out.println("+-----+-----+-----+-----+-----+-----+-----+-----+");
        }
    }

    void DrawPossible(ArrayList<int[]> possibleTurns, int playerID) {
        System.out.println("+-----+-----+-----+-----+-----+-----+-----+-----+");
        for (int i = 0; i < 8; i++) {
            StringBuilder lineOfDots = new StringBuilder();
            for (int j = 0; j < 8; j++) {
                boolean contains = false;
                for (int[] pair : possibleTurns) {
                    if (pair[0] == i && pair[1] == j) {
                        contains = true;
                        break;
                    }
                }
                if (contains) {
                    lineOfDots.append("|  ").append(ANSI_GREEN + dotVariants[playerID] + ANSI_RESET).append("  ");
                } else {
                    lineOfDots.append("|  ").append(dotVariants[field[i][j]]).append("  ");
                }
            }
            System.out.println(lineOfDots.append("|").append(ANSI_RESET));
            System.out.println("+-----+-----+-----+-----+-----+-----+-----+-----+");
        }
    }

}
