package Token;

import Parser.SyntaxError;

public class PlanTokenizer implements Tokenizer{
    private String src, next;
    private int pos;
    public PlanTokenizer(String src) throws SyntaxError {
        this.src = src;  pos = 0;
        computeNext();
    }

    @Override
    public boolean hasNextToken()
    { return next != null; }

    public void checkNextToken() throws NoSuchElementException {
        if (!hasNextToken()) throw new NoSuchElementException("no more tokens");
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

    private void computeNext() throws SyntaxError {
        StringBuilder s = new StringBuilder();
        while (pos < src.length() && (Character.isWhitespace(src.charAt(pos))||src.charAt(pos)=='\n')) {
            pos++;
        }
        if (pos == src.length()) {
            next = null;
            return;
        }
        char c = src.charAt(pos);
        if(c == '#'){
            while (pos < src.length() && src.charAt(pos) !='\n') {
                pos++;
            }
            computeNext();
            return;
        }
        if (Character.isDigit(c)) {
            s.append(c);
            for (pos++; pos < src.length() && Character.isDigit(src.charAt(pos)); pos++) {
                s.append(src.charAt(pos));
            }
        } else if (c == '+' || c == '(' || c == ')' || c == '-' || c == '*' || c == '/' || c == '%'|| c == '^'|| c == '='|| c == '{'|| c == '}') {
            s.append(c);
            pos++;
        } else if(Character.isAlphabetic(c)){
            s.append(c);
            for (pos++; pos < src.length() && Character.isAlphabetic(src.charAt(pos)); pos++) {
                s.append(src.charAt(pos));
            }
        } else  {
            throw new SyntaxError("Unknown character: " + c);
        }
        next = s.toString();
    }
}