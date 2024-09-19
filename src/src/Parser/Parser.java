package Parser;

import Plan.Plan;
import Token.NoSuchElementException;

interface Parser {
    /** Attempts to parse the token stream
     *  given to this parser.
     *  throws: SyntaxError if the token
     *          stream cannot be parsed */
    Plan parse() throws SyntaxError, NoSuchElementException;
}