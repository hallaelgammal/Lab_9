/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab9;

import java.util.*;

/**
 *
 * @author Dell
 */
public class ColValidator implements Validator {

    public final int[][] board;

    public ColValidator(int[][] board) {
        this.board = board;
    }

    @Override
    public List<ValidationError> validate()throws InterruptedException {
        List<ValidationError> out = new ArrayList<>();
        for (int c = 0; c < 9; c++) {
            Map<Integer, List<String>> pos = new HashMap<>();//pos=list of rows where it appears in this column

            for (int r = 0; r < 9; r++) {
                int v = board[r][c];//value inside soduku cell
                pos.computeIfAbsent(v, k -> new ArrayList<>()).add(String.valueOf(r + 1));//for value v get its list of rows & create it if it doesn't exist then add current row number
                
            }
            for(Map.Entry<Integer, List<String>> e : pos.entrySet()){
                if(e.getValue().size()>1){//check for duplicates
                    out.add(new ValidationError(ValidationError.Type.COL, c + 1, e.getKey(), e.getValue()));
                    //Create a new error object showing that in this column,the number v appears in these rows,and store that error in the output list.”
                }
            }
        }
        return out;
    }

}
