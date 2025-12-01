/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package lab9;

import java.util.List;
import java.util.Scanner;

public class Lab9 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            String file;
            int mode;

            // Check CLI args for file
            if (args.length >= 1) {
                file = args[0];
                if (args.length >= 2) {
                    try {
                        mode = Integer.parseInt(args[1]);
                    } catch (NumberFormatException e) {
                        System.out.println("Mode must be 0, 3, or 27");
                        break;
                    }
                } else {
                    mode = askMode(sc);
                }
            } else {
                // Ask user for CSV
                System.out.print("Enter CSV file name: ");
                file = sc.nextLine().trim();
                mode = askMode(sc);
            }

            try {
                SudokuBoard board = CSVLoader.load(file);
                System.out.println("Sudoku Board Loaded:\n");
                System.out.println(board);

                Validator validator;
                switch (mode) {
                    case 0: validator = new SingleModeValidator(board); break;
                    case 3: validator = new ThreeModeValidator(board); break;
                    case 27: validator = new TwentySevenModeValidator(board); break;
                    default:
                        System.out.println("Invalid mode. Use 0, 3, or 27");
                        continue;
                }

                List<ValidationError> errors = validator.validate();
                if (errors.isEmpty()) {
                    System.out.println("Sudoku board is VALID!");
                } else {
                    System.out.println("Sudoku board has ERRORS:");
                    for (ValidationError e : errors) System.out.println(e);
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            // Ask user to continue
            System.out.print("\nDo you want to continue? (y/n): ");
            String again = sc.nextLine().trim().toLowerCase();
            if (!again.equals("y")) {
                System.out.println("Exiting program...");
                break;
            }

            // Clear CLI args after first run
            args = new String[0];
        }

        sc.close();
    }

    private static int askMode(Scanner sc) {
        int mode;
        while (true) {
            System.out.println("Choose Validation Mode:");
            System.out.println("0-Single-thread?");
            System.out.println("3-3-thread mode?");
            System.out.println("27-27-thread mode?");
            System.out.print("Your choice: ");
            try {
                mode = Integer.parseInt(sc.nextLine().trim());
                if (mode == 0 || mode == 3 || mode == 27) break;
            } catch (NumberFormatException ignored) {}
            System.out.println("Invalid choice. Try again.");
        }
        return mode;
    }
}

