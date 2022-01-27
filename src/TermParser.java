import java.util.Scanner;

/**
 * Class term parser reads in a term from a given string
 * The first thing we're going to want to do is read a string
 * We can do that using the console and a scanner.
 * Once we have the string, we want to separate it into its parts
 * <p>
 * A term has up to 5 parts:
 * Sign:           + || -
 * Coefficient:    ints only for simplicity
 * Term:           [a-z] || [A-Z]
 * Power carat:    ^
 * Exponent:       ints only for simplicity
 * <p>
 * It's tricky cause there's so many combinations:
 * Coefficient only:                   2
 * Sign & coefficient:                 -2
 * Term only:                          x
 * Sign & term:                        -x
 * Term & carat & exponent:            x^3
 * Sign & coefficient & term:          -2x
 * Sign & term & carat & exponent:     -x^3
 * All:                                -2x^2
 * <p>
 * If we look at it as a table we can start to see where we can separate it:
 * +/- Coeff   Term    ^   Exponent
 * 2
 * x
 * -   2
 * -           x
 * -   2       x
 * x       ^   3
 * -           x       ^   3
 * 2       x       ^   3
 * -   2       x       ^   3
 * <p>
 * Clearly the best place to start is the exponent. The carat is unique value, making it a good delimiter
 * If the carat exists, store the next int after the exponent under the term's exponent
 * If the carat does not exist, continue by working back to front with the remaining term:
 * <p>
 * Once we've removed the exponent, the remaining string can only have terms and coefficients
 * We can separate the rest of the string into pieces by working back to front:
 * Check the last char. If it is NOT [a-z] || [A-Z], then it must be a number, so store coefficient. all done!
 * If the last char IS a term, store the term, and continue to work back to front.
 * Now, the last char can only be a sign or a number
 * If it's just a sign, store the coefficient as -1 or +1 accordingly. all done!
 * If it's a number, store the whole rest of the string as the coefficient. all done!
 */
public class TermParser {

    public final String LEGAL_LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVXYZ";
    public final String LEGAL_NUMBERS = "1234567890";
    public final String LEGAL_SIGNS = "+-";

    // Step 1: Check for exponent
    // If exponent, store first
    // Continue to step 2

    // Step 2: Check last char
    // If it is a #, store the remainder as a coefficient
    // Otherwise store the last char first
    // then check if it is a number or a sign
    // sign? store -1 or +1
    // #? store the remainder as a coefficient

    public static void main(String[] args) {

        // Welcome message
        System.out.println("Welcome to Term Parser! Please enter a term");

        // Read input from console
        Scanner keysIn = new Scanner(System.in);
        String inputTerm = keysIn.next();

        // Create temporary term to store values
        Term tempTerm = new Term();

        // Check for exponent
        if (hasExponent(inputTerm)) {

            // If it has an exponent, store it in tempTerm (convert to integer and use substring after carat)
            tempTerm.setExponent(Integer.parseInt(inputTerm.substring(inputTerm.indexOf("^") + 1)));

            // Delete what remains after the carat
            inputTerm = inputTerm.substring(0, inputTerm.indexOf("^"));
        }
        System.out.println("Check for exponent" + tempTerm.toString());


        // Next, check if it's just a coefficient remaining
        if (isJustCoefficient(inputTerm)) {

            // Store coefficient in tempTerm
            tempTerm.setCoefficient(Integer.parseInt(inputTerm));
        } else {
            System.out.println("is not coefficient" + inputTerm);

            // If it isn't, last character is the term, so store it
            char term = inputTerm.charAt(inputTerm.length()-1);
            tempTerm.setTerm(term);

            // Trim out the term character
            inputTerm = inputTerm.substring(0, inputTerm.length()-1);

            System.out.println("term stored" + tempTerm);


            // Next, check if the remaining term is a coefficient or just a "+" or "-":
            if (isSign(inputTerm)) {

                // If it's just a sign with no number, set the coefficient appropriately
                if (inputTerm.charAt(inputTerm.length()) == '-') tempTerm.setCoefficient(-1);
                else tempTerm.setCoefficient(1);

                // Otherwise, the remaining term is the coefficient
            } else if (inputTerm.length() > 0) tempTerm.setCoefficient(Integer.parseInt(inputTerm));

        }


        // Test statement
        System.out.println(tempTerm.toString());


    }

    /**
     * checks for an exponent in a term
     *
     * @param termToCheck string passed from console
     * @return true if exponent exists, false otherwise
     */
    private static boolean hasExponent(String termToCheck) {
        System.out.println("hasexponent test");
        if (termToCheck.contains("^")) return true;
        return false;
    }

    private static boolean isJustCoefficient(String termToCheck) {
        System.out.println("isJustCoefficient test");
        Term tempTerm = new Term();
        if (tempTerm.LEGAL_NUMBERS.contains(String.valueOf(termToCheck.charAt(termToCheck.length()-1)))) return true;
        return false;
    }

    private static boolean isSign(String termToCheck) {


        Term tempTerm = new Term();

        if (termToCheck.length() > 1) {
            if (tempTerm.LEGAL_SIGNS.contains(String.valueOf(termToCheck.charAt(termToCheck.length() - 1))))
                return true;
        }
        return false;


    }


}













































