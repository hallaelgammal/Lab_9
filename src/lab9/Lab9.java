/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package lab9;
public class Lab9 {
    public static void main(String[] args) {
        String csv;
        int mode;

        // If no args are provided, use defaults for testing
        if (args.length < 2) {
            System.out.println("No arguments provided. Using default test CSV and mode 0.");
            csv = "test.csv"; // make sure this file exists in your project folder
            mode = 0;
        } else {
            csv = args[0].trim();
            mode = Integer.parseInt(args[1].trim());
        }

        if (mode != 0) {
            System.err.println("This run supports only mode 0 for Part 1. Use mode 0.");
            System.exit(1);
        }

        try {
            SudokuBoard board = CSVLoader.load(csv);
            int[][] arr = board.toArrayCopy();

            ValidationResult result = new ValidationResult();

            // sequential checks
            result.addAll(ValidatorFactory.rowValidator(arr).validate());
            result.addAll(ValidatorFactory.colValidator(arr).validate());
            result.addAll(ValidatorFactory.boxValidator(arr).validate());

            // Print according to spec
            if (result.isValid()) {
                System.out.println("VALID");
            } else {
                System.out.println("INVALID\n");
                result.getErrors().stream()
                    .sorted((a,b) -> {
                        int ta = typeOrder(a.getType()), tb = typeOrder(b.getType());
                        if (ta != tb) return Integer.compare(ta, tb);
                        if (a.getIndex() != b.getIndex()) return Integer.compare(a.getIndex(), b.getIndex());
                        return Integer.compare(a.getValue(), b.getValue());
                    })
                    .forEach(System.out::println);
            }
        } catch (Exception ex) {
            System.err.println("ERROR: " + ex.getMessage());
            ex.printStackTrace(); // optional, helps debug
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
