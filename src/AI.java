import java.util.ArrayList;

public class AI extends Player {
    int difficultyLevel = 1;


    AI(String string, int ID) {
        super(string, ID);
    }

    @Override
    int MakeTurn(int[][] field) {
        double maxFuncVal = -100000000;
        int[] turn = new int[]{-1, -1};
        ArrayList<int[]> closedCells = new ArrayList<>(0);
        ArrayList<int[]> nearToOpp = GetNearToOpp(field);
        for (int[] cell : nearToOpp) {
            double curVal;
            if (difficultyLevel == 1) curVal = super.valueFunc(field, cell);
            else {
                curVal = valueFunc(field, cell);
            }
            if (super.valueFunc(field, cell) >= 1) {
                if (curVal > maxFuncVal) {
                    maxFuncVal = curVal;
                    turn = cell;
                    closedCells = GetClosed(field, turn);
                }
            }
        }
        if (turn[0] == -1) {
            return 1;
        }
        field[turn[0]][turn[1]] = ID;
        for (int[] cell : closedCells) {
            field[cell[0]][cell[1]] = ID;
        }
        return 0;
    }

    @Override
    double valueFunc(int[][] field, int[] cell) {
        if (super.valueFunc(field, cell) < 1) {
            return -1;
        }
        int[][] fieldCopy1 = new int[8][8];
        int[][] fieldCopy2 = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                fieldCopy1[i][j] = field[i][j];
                fieldCopy2[i][j] = field[i][j];
            }
        }
        fieldCopy1[cell[0]][cell[1]] = ID;
        fieldCopy2[cell[0]][cell[1]] = ID;
        AI opp = new AI("Opp", 1);
        opp.ID = 1;
        opp.oppID = 2;
        opp.difficultyLevel = 1;
        opp.MakeTurn(fieldCopy2);
        int[] newCell = new int[]{-1, -1};
        Iteration:
        {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (fieldCopy1[i][j] == 0 && fieldCopy2[i][j] == 1) {
                        newCell = new int[]{i, j};
                        break Iteration;
                    }
                }
            }
        }
        if (newCell[0] == -1) {
            return -10000;
        }
        return super.valueFunc(field, cell) - super.valueFunc(fieldCopy2, newCell);
    }
}
