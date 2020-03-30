public class InputManager implements IInputManager
{
    private String plainText;
    private int cursor;
    private int lineIndex;
    private int posInLine;

    private void reset()
    {
        cursor = 0;
        lineIndex = 0;
        posInLine = 0;
        plainText = "";
    }

    public void readFile(String pathToFile)
    {

    }

    public void readText(String text)
    {
        reset();
        plainText = text;
    }

    public boolean isAvailableChar()
    {
        return cursor < plainText.length();
    }

    public boolean isAvailableChar(int ahead)
    {
        return (cursor + ahead - 1) < plainText.length();
    }

    public char getNext()
    {
        char result = peekNext();
        cursor++;
        return result;
    }

    public char peekNext()
    {
        if (!isAvailableChar() || cursor < 0)
            throw new IndexOutOfBoundsException("No more characters in loaded plain text");

        return plainText.charAt(cursor);
    }

    public char peekNext(int ahead)
    {
        ahead--;
        cursor += ahead;
        char result = peekNext();
        cursor -= ahead;
        return result;
    }

    public char peekPrev()
    {
        cursor--;
        char result = peekNext();
        cursor++;

        return result;
    }

    public void unget()
    {
        if (cursor == 0)
            throw new IndexOutOfBoundsException("Trying unget() at first character");

        cursor--;
    }

    public String getLine(int lineNum)
    {
        //TODO

        return "";
    }
}
