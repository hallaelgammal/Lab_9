/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab9;

import java.util.concurrent.*;
import java.util.List;

/**
 *
 * @author Dell
 */
public class ThreeModeValidator implements Validator {

    private final SudokuBoard board;

    public ThreeModeValidator(SudokuBoard board) {
        this.board = board;
    }

    @Override
    public List<ValidationError> validate() throws InterruptedException {
        ValidationResult result = new ValidationResult();
        ExecutorService executor = Executors.newFixedThreadPool(3);
        executor.submit(() -> {
            result.addAll(new RowValidator(board.toArrayCopy()).validate());
        });
        executor.submit(() -> {

            result.addAll(new ColValidator(board.toArrayCopy()).validate());

        });
        executor.submit(() -> {

            result.addAll(new BoxValidator(board.toArrayCopy()).validate());

        });
        executor.shutdown();
        try {
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // restore interrupt status
            System.out.println("Executor termination interrupted");
        }
        return result.getErrors();
    }
}
