/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package lab9;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Lab9 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {
            try {
                // ----------------------------
                // 1) Ask user for CSV file
                // ----------------------------
                System.out.print("Enter CSV file name: ");
                String file = sc.nextLine().trim();

                SudokuBoard board = CSVLoader.load(file);
                System.out.println("Sudoku Board Loaded:\n");
                System.out.println(board);

                // ----------------------------
                // 2) Ask mode
                // ----------------------------
                System.out.println("Choose Validation Mode:");
                System.out.println("0 → Single-thread");
                System.out.println("3 → 3-thread mode");
                System.out.println("27 → 27-thread mode");
                System.out.print("Your choice: ");

                int mode = sc.nextInt();
                sc.nextLine(); // consume extra newline

                Validator validator;

                // ----------------------------
                // 3) Select validator
                // ----------------------------
                switch (mode) {
                    case 0:
                        validator = new SingleModeValidator(board);
                        break;
                    case 3:
                        validator = new ThreeModeValidator(board);
                        break;
                    case 27:
                        validator = new TwentySevenModeValidator(board);
                        break;
                    default:
                        System.out.println("Invalid mode. Try again.");
                        continue;
                }

                // ----------------------------
                // 4) Validate
                // ----------------------------
                List<ValidationError> errors = validator.validate();

                // ----------------------------
                // 5) Print results
                // ----------------------------
                if (errors.isEmpty()) {
                    System.out.println("✔ Sudoku board is VALID!");
                } else {
                    System.out.println("✘ Sudoku board has ERRORS:");
                    for (ValidationError e : errors) {
                        System.out.println(e);
                    }
                }

            } catch (IOException e) {
                System.out.println("❌ Error loading CSV: " + e.getMessage());
            } catch (InterruptedException e) {
                System.out.println("❌ Validation interrupted.");
                Thread.currentThread().interrupt();
            }

            // ----------------------------
            // 6) Ask user to continue
            // ----------------------------
            System.out.print("\nDo you want to test another board? (y/n): ");
            String again = sc.nextLine().trim().toLowerCase();

            if (!again.equals("y")) {
                System.out.println("Exiting program...");
                break;
            }
        }

        sc.close();
    }
}
