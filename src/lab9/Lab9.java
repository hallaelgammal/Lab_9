/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package lab9;
// Main.java  (sequential 0-mode integration)
import java.util.List;

public class Lab9 {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: java -jar sudoku.jar <csvfile> <mode(0|3|27)>");
            System.exit(1);
        }
        String csv = args[0];
        int mode = Integer.parseInt(args[1]);
        if (mode != 0) {
            System.err.println("This run supports only mode 0 for Part 1. Use mode 0.");
            System.exit(1);
        }

        try {
            SudokuBoard board = CSVLoader.load(csv);
            int[][] arr = board.toArrayCopy();

            ValidationResult result = new ValidationResult();

            // sequential checks
            List<ValidationError> rowErrs = ValidatorFactory.rowValidator(arr).validate();
            List<ValidationError> colErrs = ValidatorFactory.colValidator(arr).validate();
            List<ValidationError> boxErrs = ValidatorFactory.boxValidator(arr).validate();

            result.addAll(rowErrs);
            result.addAll(colErrs);
            result.addAll(boxErrs);

            // Print according to spec
            if (result.isValid()) {
                System.out.println("VALID");
            } else {
                System.out.println("INVALID\n");
                // Sorting: all ROW, then COL, then BOX and by index within each type (nice deterministic order)
                result.getErrors().stream()
                    .sorted((a,b) -> {
                        int ta = typeOrder(a.getType()), tb = typeOrder(b.getType());
                        if (ta != tb) return Integer.compare(ta, tb);
                        if (a.getIndex() != b.getIndex()) return Integer.compare(a.getIndex(), b.getIndex());
                        return Integer.compare(a.getValue(), b.getValue());
                    })
                    .forEach(e -> System.out.println(e.toString()));
            }
        } catch (Exception ex) {
            System.err.println("ERROR: " + ex.getMessage());
            System.exit(2);
        }
    }

    private static int typeOrder(ValidationError.Type t) {
        switch (t) {
            case ROW: return 1;
            case COL: return 2;
            case BOX: return 3;
            default: return 4;
        }
    }
}

