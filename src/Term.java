/**
 * Term class contains a term of a polynomial. Examples: x^4, a^32, -10b^2, +3z
 *
 * A term has up to 5 parts:
 *      Sign:           + || -
 *      Coefficient:    ints only for simplicity
 *      Term:           [a-z] || [A-Z]
 *      Power carat:    ^
 *      Exponent:       ints only for simplicity
 *
 * Delimiters:
 *              ^: separates term from exponent
 *                  lack of ^ indicates no exponent
 *
 *
 */
public class Term {

    // PROPERTIES:
    public final String LEGAL_LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVXYZ";
    public final String LEGAL_NUMBERS = "1234567890";
    public final String LEGAL_SIGNS = "+-";
    private int coefficient;
    private int exponent;
    private char term;


    public Term() {
    }

    /**
     * 3-param constructor initializes term given a coefficient, exponent, and term)
     * @param coefficient the coefficient of the term
     * @param exponent the exponent of the term
     * @param term the alphabetical term itself
     */
    public Term(int coefficient, int exponent, char term) {
        this.coefficient = coefficient;
        this.exponent = exponent;
        this.term = term;
    }

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

    @Override
    public String toString() {
        String termString = "";
        if (term != '\u0000') termString = String.valueOf(term);
        return coefficient + termString + "^" + exponent;
    }
}
