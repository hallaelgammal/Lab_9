/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab9;

import java.util.ArrayList;
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
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // Create tasks for row, column, and box validation
        List<Callable<List<ValidationError>>> tasks = List.of(
            () -> new RowValidator(board.toArrayCopy()).validate(),
            () -> new ColValidator(board.toArrayCopy()).validate(),
            () -> new BoxValidator(board.toArrayCopy()).validate()
        );

        List<ValidationError> errors = new ArrayList<>();

        try {
            // Submit all tasks and wait for them to finish
            List<Future<List<ValidationError>>> futures = executor.invokeAll(tasks);

            // Collect results from all futures
            for (Future<List<ValidationError>> future : futures) {
                try {
                    errors.addAll(future.get());
                } catch (ExecutionException e) {
                    // Handle any exceptions thrown inside the validators
                    e.printStackTrace();
                }
            }
        } finally {
            executor.shutdown();
        }

        return errors;
    }
}

