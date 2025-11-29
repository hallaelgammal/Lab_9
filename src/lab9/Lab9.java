/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package lab9;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Lab9 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Ask for CSV file path
        System.out.print("Enter path to Sudoku CSV file: ");
        String csvPath = sc.nextLine();

        // Ask for validation mode
        int mode = -1;
        while (mode != 0 && mode != 3 && mode != 27) {
            System.out.print("Choose validation mode (0=sequential, 3=three-thread, 27=27-thread): ");
            if (sc.hasNextInt()) {
                mode = sc.nextInt();
            } else {
                sc.next(); // skip invalid input
            }
        }

        try {
            SudokuBoard board = CSVLoader.load(csvPath);
            System.out.println("\nLoaded Sudoku board:");
            System.out.println(board);

            runMode(board, mode);

        } catch (IOException e) {
            System.err.println("Failed to read CSV file: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("Validation interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    private static void runMode(SudokuBoard board, int mode) throws InterruptedException {
        List<ValidationError> errors=null;
        long startTime = System.nanoTime();

        switch (mode) {
            case 0:
              boolean valid = true;
                if (!new RowValidator(board.toArrayCopy()).validate().isEmpty()) valid = false;
                if (!new ColValidator(board.toArrayCopy()).validate().isEmpty()) valid = false;
                if (!new BoxValidator(board.toArrayCopy()).validate().isEmpty()) valid = false;

                long endTime = System.nanoTime();
                System.out.println("\n========== Mode 0 (Sequential) ==========");
                System.out.println(valid ? "✅ The Sudoku board is valid!" : "❌ The Sudoku board is invalid!");
        System.out.printf("Execution time: %.3f ms%n", (endTime - startTime) / 1_000_000.0);
                break;

            case 3:
                List<ValidationError> errors3 = new ThreeModeValidator(board).validate();
                endTime = System.nanoTime();
        System.out.println("\n========== Mode 3 ==========");
        printFormattedErrors(errors3);
        System.out.printf("Execution time: %.3f ms%n", (endTime - startTime) / 1_000_000.0);
                break;

            case 27:
                ValidationResult result = new ValidationResult();
                ExecutorService executor = Executors.newFixedThreadPool(27);

                for (int r = 0; r < 9; r++)
                    executor.submit(new ValidatorThread(board.toArrayCopy(), r, "row", result));
                for (int c = 0; c < 9; c++)
                    executor.submit(new ValidatorThread(board.toArrayCopy(), c, "col", result));
                for (int b = 0; b < 9; b++)
                    executor.submit(new ValidatorThread(board.toArrayCopy(), b, "box", result));

                executor.shutdown();
                executor.awaitTermination(10, TimeUnit.SECONDS);
                List<ValidationError> errors27 = result.getErrors();
                 endTime = System.nanoTime();
        System.out.println("\n========== Mode 27 ==========");
        printFormattedErrors(errors27);
        System.out.printf("Execution time: %.3f ms%n", (endTime - startTime) / 1_000_000.0);
                break;

            default:
                // This shouldn't happen because of input validation
//                errors = new RowValidator(board.toArrayCopy()).validate();
//                errors.addAll(new ColValidator(board.toArrayCopy()).validate());
//                errors.addAll(new BoxValidator(board.toArrayCopy()).validate());
                System.out.println("Invalid mode selected!");
                break;
        } 
    }

    private static void printFormattedErrors(List<ValidationError> errors) {
        if (errors.isEmpty()) {
            System.out.println("✅ The Sudoku board is valid!");
        } else {
            System.out.println("❌ Validation errors found:");

            System.out.println("\nRow Errors:");
            errors.stream()
                  .filter(e -> e.getType() == ValidationError.Type.ROW)
                  .forEach(e -> System.out.printf(
                          "  Row %d: value %d repeated at columns %s%n",
                          e.getIndex(), e.getValue(), e.getLocations()));

            System.out.println("\nColumn Errors:");
            errors.stream()
                  .filter(e -> e.getType() == ValidationError.Type.COL)
                  .forEach(e -> System.out.printf(
                          "  Column %d: value %d repeated at rows %s%n",
                          e.getIndex(), e.getValue(), e.getLocations()));

            System.out.println("\nBox Errors:");
            errors.stream()
                  .filter(e -> e.getType() == ValidationError.Type.BOX)
                  .forEach(e -> System.out.printf(
                          "  Box %d: value %d repeated at positions %s%n",
                          e.getIndex(), e.getValue(), e.getLocations()));
        }
    }
}
