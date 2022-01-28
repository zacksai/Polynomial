import java.util.Scanner;

/**
 * Class term parser reads in a term from a given string
 * The first thing we're going to want to do is read a string
 * We can do that using the console and a scanner.
 * Once we have the string, we want to separate it into its parts
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
 *             x       ^   3
 * -           x       ^   3
 *     2       x       ^   3
 * -   2       x       ^   3
 * <p>
 * Clearly the best place to start is the exponent. The carat is unique value, making it a good delimiter
 * If the carat exists, store the next int after the exponent under the term's exponent
 * If the carat does not exist, continue by working back to front with the remaining term:
 * <p>
 * Once we've removed the exponent, the remaining string can only have terms and coefficients
 * We can separate the rest of the string into pieces by working :back to front
 * Check the last char. If it is NOT [a-z] || [A-Z], then it must be a number, so store coefficient. all done!
 * If the last char IS a term, store the term, and continue to work back to front.
 * Now, the last char can only be a sign or a number
 * If it's just a sign, store the coefficient as -1 or +1 accordingly. all done!
 * If it's a number, store the whole rest of the string as the coefficient. all done!
 */
public class TermParser {

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


        // Check for exponent (handle case that only a carat is passed)
        if (hasExponent(inputTerm)) {

            // If it has an exponent, store it in tempTerm (convert to integer and use substring after carat)
            tempTerm.setExponent(Integer.parseInt(inputTerm.substring(inputTerm.indexOf("^") + 1)));

            // Delete what remains after the carat
            inputTerm = inputTerm.substring(0, inputTerm.indexOf("^"));

        }


        // Next, check if it's just a coefficient remaining
        if (isJustCoefficient(inputTerm)) {

            // Store coefficient in tempTerm
            tempTerm.setCoefficient(Integer.parseInt(inputTerm));
        } else {

            // If it isn't, last character is the term, so check if it's legal then store it
            char term = inputTerm.charAt(inputTerm.length() - 1);
            if (isValidChar(term)) tempTerm.setTerm(term);

            // Trim out the term character
            inputTerm = inputTerm.substring(0, inputTerm.length() - 1);

            // Next, check if the remaining term is a coefficient or just a "+" or "-":
            if (isSign(inputTerm)) {

                // If it's just a sign with no number, set the coefficient appropriately
                if (inputTerm.charAt(inputTerm.length() - 1) == '-') tempTerm.setCoefficient(-1);

                // Otherwise, the remaining term is the coefficient
            } else if (inputTerm.length() > 0) tempTerm.setCoefficient(Integer.parseInt(inputTerm));

        }

        // At this point, the term is totally initialized, but we still have to handle certain formatting issues:


        // Handle case: coefficient^exponent (no term, ie 2^5 or 3^2):
        if (tempTerm.getTerm() == '\u0000') {
            double value = Math.pow(Double.valueOf(tempTerm.getCoefficient()), Double.valueOf(tempTerm.getExponent()));
            tempTerm.setCoefficient((int) value);
            tempTerm.setExponent(1);
        }

        // Handle case: term^0 (ie -4x^0 or z^0)
        if (tempTerm.getExponent() == 0) {

            // Remove term, leaving only coefficient. Redundancy in case anything slips through previous case
            if (tempTerm.getTerm() != '\u0000') {
                tempTerm.setTerm('\u0000');
            }
        }

        // Handle case: coefficient = 0
        if (tempTerm.getCoefficient() == 0) {
            tempTerm.setTerm('\u0000');
            tempTerm.setExponent(1);
        }

        // Test statement
        System.out.println(tempTerm);
    }

    /**
     * checks for an exponent in a term
     *
     * @param termToCheck string passed from console
     * @return true if exponent exists, false otherwise
     */
    private static boolean hasExponent(String termToCheck) {
        if (termToCheck.contains("^")) return true;
        return false;
    }

    /**
     * check if the remainder of a term is just a coefficient (no x or z or other term)
     * @param termToCheck the term being checked
     * @return true if the last character is 0-9
     */
    private static boolean isJustCoefficient(String termToCheck) {

        // Make a list of legal numbers and return if termToCheck ends in one or not
        String legalNumbers = "0123456789";
        if (legalNumbers.contains(String.valueOf(termToCheck.charAt(termToCheck.length() - 1)))) return true;
        return false;
    }

    /**
     * a method to determine if a string is a +/- sign or not
     * @param termToCheck string being checked
     * @return true if string was a + or -
     */
    private static boolean isSign(String termToCheck) {

        // Make a list of legal signs and return if termToCheck ends in one or not
        String legalSigns = "+-";
        if (termToCheck.length() > 0 &&
                legalSigns.contains(String.valueOf(termToCheck.charAt(termToCheck.length() - 1)))) return true;

        return false;
    }

    /**
     * a method to confirm if a char is valid for use as a term
     * @param charToCheck char being checked
     * @return false and exit program if not [a-z] || [A-Z]
     */
    private static boolean isValidChar(char charToCheck){

        // Create list of legal chars and check if char is in it
        String legalChars = "abcdefghijklmnopqurstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        if (legalChars.contains(String.valueOf(charToCheck))) return true;

        // Invalid? Print error message and end program.
        System.out.println("The term is not valid. Please try again.");
        System.exit(0);
        return false;
    }

    /**
     * method to ensure a term input is valid
     * @param stringToCheck
     */
    private static void ensureValidTerm(String stringToCheck){

        String legalChars = "abcdefghijklmnopqurstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890-+";
        for (int i = 0; i < stringToCheck.length()-1; i++){
            if (stringToCheck.charAt(i) == '^') {
                //Print error message and end program.
                System.out.println("The term is not valid. Please try again.");
                System.exit(0);
            }
        }

    }


}













































