/**
 * Term class contains a term of a polynomial. Examples: x^4, a^32, -10b^2, +3z
 * <p>
 * A term has up to 5 parts:
 * Sign:           + || -
 * Coefficient:    ints only for simplicity
 * Term:           [a-z] || [A-Z]
 * Power carat:    ^
 * Exponent:       ints only for simplicity
 * <p>
 * Delimiters:
 * ^: separates term from exponent
 * lack of ^ indicates no exponent
 * the base of the term itself, which is the only non-number character outside of the ^
 */
public class Term {

    // PROPERTIES:
    private int coefficient = 1;
    private int exponent = 1;
    private char term;


    /**
     * empty constructor initializes term object (variables are already default)
     */
    public Term() {
    }

    /**
     * 3-param constructor initializes term given a coefficient, exponent, and term
     *
     * @param coefficient the coefficient of the term
     * @param exponent    the exponent of the term
     * @param term        the alphabetical term itself
     */
    public Term(int coefficient, int exponent, char term) {
        this.coefficient = coefficient;
        this.exponent = exponent;
        this.term = term;
    }

    // Accessors and modifiers
    public int getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(int coefficient) {
        this.coefficient = coefficient;
    }

    public int getExponent() {
        return exponent;
    }

    public void setExponent(int exponent) {
        this.exponent = exponent;
    }

    public char getTerm() {
        return term;
    }

    public void setTerm(char term) {
        this.term = term;
    }

    /**
     * toString returns a properly formatted string for the polynomial term
     *
     * @return formatted polynomial term
     */
    @Override
    public String toString() {

        // Convert coefficient to string
        String coefficientString = String.valueOf(coefficient);

        // Handle empty terms first:
        if (term == '\u0000') return coefficientString;


        // Next, handle terms with coefficient 1 or -1:
        if (coefficient == 1) coefficientString = "";
        if (coefficient == -1) coefficientString = "-";

        // Convert the term to string
        String termString = String.valueOf(term);

        // Next, handle exponents and begin return statements
        if (exponent == 0 || exponent == 1) return coefficientString + termString;

        // Finally, return full concatenation
        return coefficientString + termString + "^" + exponent;
    }
}
