/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab9;

/**
 *
 * @author Dell
 */
public class ValidatorFactory {

    public static Validator rowValidator(int[][] board) {
        return new RowValidator(board);
    }

    public static Validator colValidator(int[][] board) {
        return new ColValidator(board);
    }

    public static Validator boxValidator(int[][] board) {
        return new BoxValidator(board);
    }
}
