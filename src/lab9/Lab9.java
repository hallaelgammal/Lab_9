/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package lab9;

import java.io.IOException;
import java.util.List;

public class Lab9 {

    public static void main(String[] args) {
        try {
            // 1️⃣ Load the Sudoku board from CSV
            SudokuBoard board = CSVLoader.load("test.csv"); // put your CSV file path here

            // 2️⃣ Create the 3-thread validator
            ThreeModeValidator validator = new ThreeModeValidator(board);

            // 3️⃣ Validate the board
            List<ValidationError> errors = null;
            try {
                 errors = validator.validate();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 4️⃣ Print the results
            if (errors.isEmpty()) {
                System.out.println("Sudoku board is valid!");
            } else {
                System.out.println("Errors found:");
                for (ValidationError e : errors) {
                    System.out.println(e);
                }
            }

        } catch (IOException e) {
            System.out.println("Failed to load the CSV file: " + e.getMessage());
        }
    }
}
