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
        ValidationResult result = new ValidationResult();
        // Create tasks for row, column, and box validation
//        List<Callable<List<ValidationError>>> tasks = List.of(
//            () -> new RowValidator(board.toArrayCopy()).validate(),
//            () -> new ColValidator(board.toArrayCopy()).validate(),
//            () -> new BoxValidator(board.toArrayCopy()).validate()
//        );
//
//        List<ValidationError> errors = new ArrayList<>();
//
//        try {
//            // Submit all tasks and wait for them to finish
//            List<Future<List<ValidationError>>> futures = executor.invokeAll(tasks);
//
//            // Collect results from all futures
//            for (Future<List<ValidationError>> future : futures) {
//                try {
//                    errors.addAll(future.get());
//                } catch (ExecutionException e) {
//                    // Handle any exceptions thrown inside the validators
//                    e.printStackTrace();
//                }
//            }
//        } finally {
//            executor.shutdown();
//        }
//
//        return errors;


    // Submit row, column, and box validators
    executor.submit(() -> {
        try {
            result.addAll(new RowValidator(board.toArrayCopy()).validate());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    });

    executor.submit(() -> {
        try {
            result.addAll(new ColValidator(board.toArrayCopy()).validate());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    });

    executor.submit(() -> {
        try {
            result.addAll(new BoxValidator(board.toArrayCopy()).validate());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    });

    executor.shutdown();
    boolean finished = executor.awaitTermination(10, TimeUnit.SECONDS);
    if (!finished) {
        System.err.println("Warning: Validation did not finish in time.");
    }

    return result.getErrors();
}
}



