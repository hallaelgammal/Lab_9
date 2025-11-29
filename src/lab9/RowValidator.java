/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab9;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Abdallah
 */
public class RowValidator implements Validator {
    private final int[][] board;
    
    public RowValidator(int[][] board) // save the board 
    {
        this.board = board;
    }
    
    @Override
    public List<ValidationError> validate(){
        List<ValidationError> out = new ArrayList<>();// create empty list to hold any detected errors 
        
        for(int r=0; r<9; r++)
        {
            Map<Integer, List<String>> pos = new HashMap<>();
            for(int c=0; c<9; c++){
                int v = board[r][c];
                
                pos.computeIfAbsent(v, k-> new ArrayList<>()).add(String.valueOf(c+1));
                 
            }
            for(Map.Entry<Integer, List<String>> e: pos.entrySet()){ // for cheecking duplicates
                if(e.getValue().size() >1) {
                    out.add(new ValidationError( //3ashan ne validate error b makano bzabt
                    ValidationError.Type.ROW,
                    r+1, 
                    e.getKey(), // duplicated value
                    e.getValue()));// list of positions
                }
            }
        }
        return out; // return all row errors
    }
}
