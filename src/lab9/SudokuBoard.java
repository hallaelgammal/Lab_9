/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab9;

/**
 *
 * @author Gehad
 */
public class SudokuBoard {

    private final int[][] board = new int[9][9];

    public SudokuBoard(int[][] data) {
        for (int r = 0; r < 9; r++) {
            System.arraycopy(data[r], 0, board[r], 0, 9);
        }
    }

    public int get(int r, int c) {
        return board[r][c];
    }

    public int[][] toArrayCopy() {
        int[][] copy = new int[9][9];
        for (int r = 0; r < 9; r++) {
            System.arraycopy(board[r], 0, copy[r], 0, 9);
        }
        return copy;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                sb.append(board[r][c]);
                if (c < 8) sb.append(" ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}

