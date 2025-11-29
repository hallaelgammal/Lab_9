/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab9;

/**
 *
 * @author Gehad
 */
import java.util.Collections;

public class ValidatorThread extends Thread {
    private final int[][] board;
    private final int index;
    private final String type; // "row", "col", "box"
    private final ValidationResult result;

    public ValidatorThread(int[][] board, int index, String type, ValidationResult result) {
        this.board = board;
        this.index = index;
        this.type = type;
        this.result = result;
    }

    @Override
    public void run() {
        switch (type) {
            case "row":
                validateRow(index);
                break;
            case "col":
                validateCol(index);
                break;
            case "box":
                validateBox(index);
                break;
        }
    }

    private void validateRow(int r) {
        boolean[] seen = new boolean[9];
        for (int c = 0; c < 9; c++) {
            int val = board[r][c];
            if (val < 1 || val > 9 || seen[val - 1]) {
                result.add(new ValidationError(
                        ValidationError.Type.ROW,
                        r,
                        val,
                        Collections.singletonList(r + "," + c)
                ));
                return;
            }
            seen[val - 1] = true;
        }
    }

    private void validateCol(int c) {
        boolean[] seen = new boolean[9];
        for (int r = 0; r < 9; r++) {
            int val = board[r][c];
            if (val < 1 || val > 9 || seen[val - 1]) {
                result.add(new ValidationError(
                        ValidationError.Type.COL,
                        c,
                        val,
                        Collections.singletonList(r + "," + c)
                ));
                return;
            }
            seen[val - 1] = true;
        }
    }

    private void validateBox(int boxIndex) {
        boolean[] seen = new boolean[9];
        int startRow = (boxIndex / 3) * 3;
        int startCol = (boxIndex % 3) * 3;

        for (int r = startRow; r < startRow + 3; r++) {
            for (int c = startCol; c < startCol + 3; c++) {
                int val = board[r][c];
                if (val < 1 || val > 9 || seen[val - 1]) {
                    result.add(new ValidationError(
                            ValidationError.Type.BOX,
                            boxIndex,
                            val,
                            Collections.singletonList(r + "," + c)
                    ));
                    return;
                }
                seen[val - 1] = true;
            }
        }
    }
}
