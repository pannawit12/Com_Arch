package Token;

import Parser.SyntaxError;

import java.util.Map;

public class ConfigTokenizer implements Tokenizer{
    Map<String,Integer> k;
    private String src, next;
    private int pos;

    public ConfigTokenizer(String src) throws SyntaxError{
        this.src = src;
        pos = 0;
        computeNext();
    }

    public void checkNextToken() throws NoSuchElementException {
        if (!hasNextToken()) throw new NoSuchElementException("no more tokens");
    }

    public boolean hasNextToken() {
        return next != null;
    }

    @Override
    public String peek() throws NoSuchElementException {
        checkNextToken();
        return next;
    }
    public boolean peek(String s) throws NoSuchElementException {
        if (!hasNextToken()) {
            return false;
        }
        return peek().equals(s);
    }

    @Override
    public String consume() throws NoSuchElementException, SyntaxError {
        checkNextToken();
        String result = next;
        computeNext();
        return result;
    }
    public void consume(String s) throws SyntaxError, NoSuchElementException {
        if (peek(s)) {
            consume();
        } else {
            throw new SyntaxError(s + " expected");
        }
    }
    private void computeNext() {
        StringBuilder s = new StringBuilder();
        while (pos < src.length() && (Character.isWhitespace(src.charAt(pos))||src.charAt(pos)=='\n')){
            pos++;
        }
        if (pos == src.length()) {
            next = null;
            return;
        }
        char c = src.charAt(pos);
        if(c == 'm'){
            pos++;
            while(pos < src.length() && src.charAt(pos) !='\n'){
                s.append(c);
            }
            k.put("m",1);
        }
    }
}