/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab9;

import java.util.*;

/**
 *
 * @author Gehad
 */
public class BoxValidator implements Validator {
    private final int[][] board;

    public BoxValidator(int[][] board) {
        if (board == null) throw new IllegalArgumentException("board cannot be null");
        this.board = board;
    }

    @Override
    public List<ValidationError> validate() throws InterruptedException{
        List<ValidationError> out = new ArrayList<>();
        int boxIndex = 1; 
//outer loops over boxes
        for (int br = 0; br < 3; br++) {
            for (int bc = 0; bc < 3; bc++) {
                Map<Integer, List<String>> positions = new HashMap<>();
//inner loops over boxes
                for (int r = br * 3; r < br * 3 + 3; r++) {
                    for (int c = bc * 3; c < bc * 3 + 3; c++) {
                        int v = board[r][c];

                      //skip invalid inputs
                        if (v < 1 || v > 9) continue;

                        String pos = "(" + (r + 1) + "," + (c + 1) + ")";
                        positions.computeIfAbsent(v, k -> new ArrayList<>()).add(pos);
                    }
                }
//detect duplicates
                for (Map.Entry<Integer, List<String>> entry : positions.entrySet()) {
                    List<String> locs = entry.getValue();
                    if (locs.size() > 1) {
                        // defensive copy to protect internal lists
                        out.add(new ValidationError(
                                ValidationError.Type.BOX,
                                boxIndex,
                                entry.getKey(),
                                new ArrayList<>(locs)
                        ));
                    }
                }

                boxIndex++;
            }
        }

        return out;
    }
}

    
            
