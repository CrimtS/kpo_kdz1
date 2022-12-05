import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Game {
    int maxPoints = -1;
    String bestPlayerName;
    Player player1;
    Player player2;
    AI ai;
    InputChecker checker = new InputChecker();
    boolean multiplayer = false;
    Scanner scanner = new Scanner(System.in);
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    int[][] field;
    char[] dotVariants = {' ', '○', '●'};

    boolean GameEnded() {
        for (int[] arr : field) {
            for (int a : arr) {
                if (a == 0) return false;
            }
        }
        return true;
    }


    int PlayerTurn(Player player) {
        ArrayList<ArrayList<int[]>> possibleClosedCells = new ArrayList<>(0);
        ArrayList<int[]> possibleTurns = player.GetPossibleTurns(field, possibleClosedCells);
        DrawPossible(possibleTurns, player.ID);
        System.out.println("Возможные ходы игрока " + player.name + ":");
        for (int[] cell : possibleTurns) {
            System.out.println("x = " + (cell[1] + 1) + ", y = " + (cell[0] + 1) + ";");
        }
        return player.MakeTurn(field);
    }

    int AITurn() {
        DrawTurn();
        System.out.println("Ход ИИ:");
        return ai.MakeTurn(field);
    }

    void Go() {
        field = new int[8][8];
        System.out.println("Добро пожаловать в Реверси! Введите количество игроков:");
        if (checker.CheckIntOneTwo() == 1) {
            System.out.println("Выберите уровень сложности: 1- Новичок, 2- Профессионал");
            ai = new AI("ИИ", 2);
            ai.difficultyLevel = checker.CheckIntOneTwo();
            System.out.println("Введите имя игрока:");
            player1 = new Player(scanner.nextLine(), 1);
        } else {
            multiplayer = true;
            System.out.println("Введите имя первого игрока:");
            player1 = new Player(scanner.nextLine(), 1);
            System.out.println("Введите имя второго игрока:");
            player2 = new Player(scanner.nextLine(), 2);
        }
        field[3][3] = 2;
        field[4][4] = 2;
        field[3][4] = 1;
        field[4][3] = 1;
        int skipscount = 0;
        while (true) {
            if (PlayerTurn(player1) == 1) {
                if (skipscount == 1) {
                    break;
                } else {
                    skipscount += 1;
                }
            } else {
                skipscount = 0;
            }
            if (GameEnded()) {
                break;
            }
            if (multiplayer) {
                if (PlayerTurn(player2) == 1) {
                    if (skipscount == 1) {
                        break;
                    } else {
                        skipscount += 1;
                    }
                } else {
                    skipscount = 0;
                }
            } else {
                if (AITurn() == 1) {
                    System.out.println("Игрок ИИ не может сделать ход, поэтому пропускает.");
                    if (skipscount == 1) {
                        break;
                    } else {
                        skipscount += 1;
                    }
                } else {
                    skipscount = 0;
                }
            }
            if (GameEnded()) {
                break;
            }
        }
        DrawTurn();
        int player1_points = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (field[i][j] == 1) {
                    player1_points += 1;
                }
            }
        }
        if (multiplayer)
            System.out.println("Счёт игры: " + player1.name + " - " + player1_points + ", " + player2.name + "- " + (64 - player1_points));
        else {
            System.out.println("Счёт игры: " + player1.name + " - " + player1_points + ", ИИ - " + (64 - player1_points));
        }
        if (player1_points > 32) {
            System.out.println("Победил " + player1.name + "!");
            if (player1_points > maxPoints) {
                maxPoints = player1_points;
                bestPlayerName = player1.name;
            }
        } else {
            if (multiplayer) {
                System.out.println("Победил " + player2.name + "!");
                if (64 - player1_points > maxPoints) {
                    maxPoints = 64 - player1_points;
                    bestPlayerName = player2.name;
                }
            } else {
                System.out.println("Вас обыграл бот(Но ваш результат не будет забыт!)");
                if (64 - player1_points > maxPoints) {
                    maxPoints = player1_points;
                    bestPlayerName = player1.name;
                }
            }
        }
        Restart();

    }

    void Restart() {
        System.out.println("Хотите сыграть ещё? Да/Нет");
        String ans = scanner.next();
        if (Objects.equals(ans, "Да")) {
            Go();
        } else {
            System.out.println("Жаль. Ну что ж, лучшим игроком стал " + bestPlayerName + " с результатом " + maxPoints);
            System.out.println("Спасибо за игру, заходите ещё!");
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
                    lineOfDots.append("|  ").append(ANSI_GREEN).append(dotVariants[playerID]).append(ANSI_RESET).append("  ");
                } else {
                    lineOfDots.append("|  ").append(dotVariants[field[i][j]]).append("  ");
                }
            }
            System.out.println(lineOfDots.append("|").append(ANSI_RESET));
            System.out.println("+-----+-----+-----+-----+-----+-----+-----+-----+");
        }
    }

}
