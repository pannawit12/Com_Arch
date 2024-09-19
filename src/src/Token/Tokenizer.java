package Token;

import Parser.SyntaxError;

public interface Tokenizer {
    /** Returns true if there is
     *  more token */
    boolean hasNextToken();

    /** Returns the next token
     *  in the input stream. */
    String peek() throws NoSuchElementException;

    /** Consumes the next token
     *  from the input stream
     *  and returns it.
     *  effects: removes the next token
     *           from the input stream */
    String consume() throws NoSuchElementException, SyntaxError;
}