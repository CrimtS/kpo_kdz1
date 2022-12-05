import java.util.ArrayList;

public class Player {
    String name;
    int ID;
    int oppID;

    Player(String string, int ID) {
        name = string;
        this.ID = ID;
        if (ID == 1) {
            oppID = 2;
        } else {
            oppID = 1;
        }
    }


    int MakeTurn(int[][] field) {
        System.out.println("Введите координаты клетки из доступных:");
        InputChecker checker = new InputChecker();
        ArrayList<ArrayList<int[]>> possibleClosedCells = new ArrayList<>(0);
        ArrayList<int[]> PossibleTurns = GetPossibleTurns(field, possibleClosedCells);
        if (PossibleTurns.size() == 0) {
            System.out.println("У игрока " + name + " нет доступных ходов, поэтому он пропускает ход.");
            return 1;
        }
        int[] turn = checker.CheckCell(PossibleTurns);
        for (int i = 0; i < PossibleTurns.size(); i++) {
            if (PossibleTurns.get(i)[0] == turn[0] && PossibleTurns.get(i)[1] == turn[1]) {
                for (int[] cell : possibleClosedCells.get(i)) {
                    field[cell[0]][cell[1]] = ID;
                }
            }
        }
        field[turn[0]][turn[1]] = ID;
        return 0;
    }

    ArrayList<int[]> GetPossibleTurns(int[][] field, ArrayList<ArrayList<int[]>> possibleClosedCells) {
        ArrayList<int[]> nearToOpp = GetNearToOpp(field);
        ArrayList<int[]> possibleMoves = new ArrayList<>(0);
        for (int[] cell : nearToOpp) {
            if (valueFunc(field, cell) >= 1) {
                possibleMoves.add(cell);
                possibleClosedCells.add(GetClosed(field, cell));
            }
        }
        return possibleMoves;
    }

    double valueFunc(int[][] field, int[] cell) {
        ArrayList<int[]> closedCells = GetClosed(field, cell);
        double func = 0;
        if (cell[0] == 0 || cell[0] == 7) {
            if (cell[1] == 0 || cell[1] == 7) {
                func += 0.8;
            } else {
                func += 0.4;
            }
        } else if (cell[1] == 0 || cell[1] == 7) {
            func += 0.4;
        }
        for (int[] closedCell : closedCells) {
            if (closedCell[0] == 0 || closedCell[0] == 7 || closedCell[1] == 0 || closedCell[1] == 7) {
                func += 2;
            } else {
                func += 1;
            }
        }
        return func;
    }

    ArrayList<int[]> GetNearToOpp(int[][] field) {
        ArrayList<int[]> nearToOpp = new ArrayList<>(0);
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (field[y][x] == 0) {
                    if (y != 0 && field[y - 1][x] == oppID) {
                        nearToOpp.add(new int[]{y, x});
                        continue;
                    }
                    if (y != 7 && x != 7 && field[y + 1][x + 1] == oppID) {
                        nearToOpp.add(new int[]{y, x});
                        continue;
                    }
                    if (y != 0 && x != 0 && field[y - 1][x - 1] == oppID) {
                        nearToOpp.add(new int[]{y, x});
                        continue;
                    }
                    if (y != 0 && x != 7 && field[y - 1][x + 1] == oppID) {
                        nearToOpp.add(new int[]{y, x});
                        continue;
                    }
                    if (y != 7 && x != 0 && field[y + 1][x - 1] == oppID) {
                        nearToOpp.add(new int[]{y, x});
                        continue;
                    }

                    if (y != 7 && field[y + 1][x] == oppID) {
                        nearToOpp.add(new int[]{y, x});
                        continue;
                    }
                    if (x != 0 && field[y][x - 1] == oppID) {
                        nearToOpp.add(new int[]{y, x});
                        continue;
                    }
                    if (x != 7 && field[y][x + 1] == oppID) {
                        nearToOpp.add(new int[]{y, x});
                    }
                }
            }
        }
        return nearToOpp;
    }

    ArrayList<int[]> GetClosed(int[][] field, int[] cell) {
        ArrayList<int[]> closedCells = new ArrayList<>(0);
        int y = cell[0];
        int x = cell[1];
        int targetY;
        int targetX;
        //right:
        if (x != 7) {
            targetX = -1;
            for (int i = x + 1; i < 8; i++) {
                if (field[y][i] == 0) break;
                if (field[y][i] == ID) {
                    targetX = i;
                    break;
                }
            }
            if (targetX != -1) {
                for (int i = x + 1; i < targetX; i++) {
                    if (field[y][i] == oppID) {
                        closedCells.add(new int[]{y, i});
                    }
                }
            }
        }

        //left:
        if (x != 0) {
            targetX = -1;
            for (int i = x - 1; i >= 0; i--) {
                if (field[y][i] == 0) break;
                if (field[y][i] == ID) {
                    targetX = i;
                    break;
                }
            }
            if (targetX != -1) {
                for (int i = x - 1; i > targetX; i--) {
                    if (field[y][i] == oppID) {
                        closedCells.add(new int[]{y, i});
                    }
                }
            }
        }
        //down:
        if (y != 7) {
            targetY = -1;
            for (int j = y + 1; j < 8; j++) {
                if (field[j][x] == 0) break;
                if (field[j][x] == ID) {
                    targetY = j;
                    break;
                }
            }
            if (targetY != -1) {
                for (int j = y + 1; j < targetY; j++) {
                    if (field[j][x] == oppID) {
                        closedCells.add(new int[]{j, x});
                    }
                }
            }
        }
        //up:
        if (y != 0) {
            targetY = -1;
            for (int j = y - 1; j >= 0; j--) {
                if (field[j][x] == 0) break;
                if (field[j][x] == ID) {
                    targetY = j;
                    break;
                }
            }
            if (targetY != -1) {
                for (int j = y - 1; j > targetY; j--) {
                    if (field[j][x] == oppID) {
                        closedCells.add(new int[]{j, x});
                    }
                }
            }
        }

        //main diag up
        if (y != 0 && x != 0) {
            targetX = -1;
            targetY = -1;
            int i = y - 1;
            int j = x - 1;
            while (i >= 0 && j >= 0) {
                if (field[i][j] == 0) break;
                if (field[i][j] == ID) {
                    targetY = i;
                    targetX = j;
                    break;
                }
                i--;
                j--;
            }
            if (targetX != -1) {
                i = y - 1;
                j = x - 1;
                while (i > targetY && j > targetX) {
                    if (field[i][j] == oppID) {
                        closedCells.add(new int[]{i, j});
                    }
                    i--;
                    j--;
                }
            }
        }

        //main diag down
        if (y != 7 && x != 7) {
            targetX = -1;
            targetY = -1;
            int i = y + 1;
            int j = x + 1;
            while (i < 8 && j < 8) {
                if (field[i][j] == 0) break;
                if (field[i][j] == ID) {
                    targetY = i;
                    targetX = j;
                    break;
                }
                i++;
                j++;
            }
            if (targetX != -1) {
                i = y + 1;
                j = x + 1;
                while (i < targetY && j < targetX) {
                    if (field[i][j] == oppID) {
                        closedCells.add(new int[]{i, j});
                    }
                    i++;
                    j++;
                }
            }
        }
        //sub diag up
        if (y != 0 && x != 7) {
            targetX = -1;
            targetY = -1;
            int i = y - 1;
            int j = x + 1;
            while (i >= 0 && j < 8) {
                if (field[i][j] == 0) break;
                if (field[i][j] == ID) {
                    targetY = i;
                    targetX = j;
                    break;
                }
                i--;
                j++;
            }
            if (targetX != -1) {
                i = y - 1;
                j = x + 1;
                while (i > targetY && j < targetX) {
                    if (field[i][j] == oppID) {
                        closedCells.add(new int[]{i, j});
                    }
                    i--;
                    j++;
                }
            }
        }
        //sub diag down
        if (y < 7 && x > 0) {
            targetX = -1;
            targetY = -1;
            int i = y + 1;
            int j = x - 1;
            while (i < 8 && j >= 0) {
                if (field[i][j] == 0) break;
                if (field[i][j] == ID) {
                    targetY = i;
                    targetX = j;
                    break;
                }
                i++;
                j--;
            }
            if (targetX != -1) {
                i = y + 1;
                j = x - 1;
                while (i < targetY && j > targetX) {
                    if (field[i][j] == oppID) {
                        closedCells.add(new int[]{i, j});
                    }
                    i++;
                    j--;
                }
            }
        }
        return closedCells;
    }

}
