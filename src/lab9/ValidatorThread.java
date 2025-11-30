/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab9;

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
                validateRow();
                break;
            case "col":
                validateCol();
                break;
            case "box":
                validateBox();
                break;
        }
    }

    private void validateRow() {
        boolean[] seen = new boolean[9];
        for (int c = 0; c < 9; c++) {
            checkCell(index, c, seen, ValidationError.Type.ROW);
        }
    }

    private void validateCol() {
        boolean[] seen = new boolean[9];
        for (int r = 0; r < 9; r++) {
            checkCell(r, index, seen, ValidationError.Type.COL);
        }
    }

    private void validateBox() {
        boolean[] seen = new boolean[9];
        int startRow = (index / 3) * 3;
        int startCol = (index % 3) * 3;
        for (int r = startRow; r < startRow + 3; r++) {
            for (int c = startCol; c < startCol + 3; c++) {
                checkCell(r, c, seen, ValidationError.Type.BOX);
            }
        }
    }

    private void checkCell(int r, int c, boolean[] seen, ValidationError.Type typeEnum) {
        int val = board[r][c];

        // Invalid number check
        if (val < 1 || val > 9) {
            result.add(new ValidationError(
                typeEnum,
                typeEnum == ValidationError.Type.ROW ? r + 1 : typeEnum == ValidationError.Type.COL ? c + 1 : (r/3*3 + c/3 + 1),
                val,
                Collections.singletonList((r + 1) + "," + (c + 1))
            ));
            return;
        }

        // Duplicate check
        if (seen[val - 1]) {
            result.add(new ValidationError(
                typeEnum,
                typeEnum == ValidationError.Type.ROW ? r + 1 : typeEnum == ValidationError.Type.COL ? c + 1 : (r/3*3 + c/3 + 1),
                val,
                Collections.singletonList((r + 1) + "," + (c + 1))
            ));
        } else {
            seen[val - 1] = true;
        }
    }
}
