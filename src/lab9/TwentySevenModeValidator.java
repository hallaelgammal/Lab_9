/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab9;

import java.util.ArrayList;
import java.util.List;

public class TwentySevenModeValidator implements Validator {

    private final SudokuBoard board;

    public TwentySevenModeValidator(SudokuBoard board) {
        this.board = board;
    }

    @Override
    public List<ValidationError> validate() throws InterruptedException {

        List<ValidationResult> partialResults = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();
        int[][] copy = board.toArrayCopy();

        // Create 27 threads: 9 rows, 9 cols, 9 boxes
        for (int i = 0; i < 9; i++) {
            ValidationResult rowResult = new ValidationResult();
            threads.add(new ValidatorThread(copy, i, "row", rowResult));
            partialResults.add(rowResult);

            ValidationResult colResult = new ValidationResult();
            threads.add(new ValidatorThread(copy, i, "col", colResult));
            partialResults.add(colResult);

            ValidationResult boxResult = new ValidationResult();
            threads.add(new ValidatorThread(copy, i, "box", boxResult));
            partialResults.add(boxResult);
        }

        // Start all threads
        for (Thread t : threads) t.start();

        // Wait for all threads to finish
        for (Thread t : threads) t.join();

        // Merge results
        ValidationResult finalResult = new ValidationResult();
        for (ValidationResult vr : partialResults) {
            finalResult.addAll(vr.getErrors());
        }

        return finalResult.getErrors();
    }
}

