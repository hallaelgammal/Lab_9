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



