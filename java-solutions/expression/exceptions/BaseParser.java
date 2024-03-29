package expression.exceptions;

public class BaseParser {
    protected static final char END = '\0';

    private final CharSource source;

    private char ch = 0xffff;

    private String token;

    protected BaseParser(final CharSource source) {
        this.source = source;
        this.token = "";
        take();
    }

    protected char take() {
        final char result = ch;
        ch = source.hasNext() ? source.next() : END;
        return result;
    }

    protected void takeToken() {
        StringBuilder sb = new StringBuilder();
        while (!eof() && Character.isLetterOrDigit(ch)) {
            sb.append(take());
        }
        token = sb.toString();
    }

    protected String getToken() {
        return token;
    }

    protected char current() {
        return ch;
    }

    protected boolean test(final char expected) {
        return ch == expected;
    }

    protected boolean take(String expected) {
        if (token.isEmpty() && Character.isLetter(ch)) {
            takeToken();
        }
        if (expected.equals(token)) {
            takeToken();
            return true;
        }
        return false;
    }

    protected boolean take(final char expected) {
        if (test(expected)) {
            take();
            return true;
        }
        return false;
    }

    protected void expect(final char expected) throws ParserException {
        if (!take(expected)) {
            throw new MismatchException("Expected '" + expected + "', found '" + ch + "' in position" + getErrorPosition());
        }
    }

    protected int getErrorPosition() {
        return source.errorPosition();
    }

    protected void expect(final String value) throws ParserException {
        for (final char c : value.toCharArray()) {
            expect(c);
        }
    }

    protected boolean isWhitespace() {
        return Character.isWhitespace(ch);
    }

    protected boolean eof() {
        return take(END);
    }

    protected boolean between(final char from, final char to) {
        return from <= ch && ch <= to;
    }
}
