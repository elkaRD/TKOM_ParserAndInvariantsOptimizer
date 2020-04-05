import java.util.Map;
import java.util.TreeMap;

public class InputManager implements IInputManager
{
    private String plainText;
    private int cursor;
    private int lineIndex;
    private int posInLine;

    private Map<Integer, Integer> linesBeginnings = new TreeMap<>();

    private void reset()
    {
        cursor = 0;
        lineIndex = 0;
        posInLine = 0;
        plainText = "";
        linesBeginnings.clear();
    }

    public void readFile(String pathToFile)
    {

    }

    public void readText(String text)
    {
        reset();
        plainText = text;
        detectNewLines();
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

        if (isNewLine(result))
        {
            posInLine = 0;
            lineIndex++;
        }
        cursor++;
        posInLine++;

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

//    public char peekPrev()
//    {
//        cursor--;
//        char result = peekNext();
//        cursor++;
//
//        return result;
//    }

//    public void unget()
//    {
//        if (cursor == 0)
//            throw new IndexOutOfBoundsException("Trying unget() at first character");
//
//        cursor--;
//    }

    public String getLine(int lineNum)
    {
        if (lineNum+1 < linesBeginnings.size())
            return plainText.substring(linesBeginnings.get(lineNum), linesBeginnings.get(lineNum+1));

        return plainText.substring(linesBeginnings.get(lineNum));
    }

    public CharPos getCurrentPosition()
    {
        return new CharPos(this, cursor, posInLine, lineIndex);
    }

    private boolean isNewLine(char curChar)
    {
        return curChar == '\n';
    }

    private void detectNewLines()
    {
        linesBeginnings.clear();
        linesBeginnings.put(0, 0);
        int linesCounter = 1;

        for (int i = 0; i < plainText.length(); i++)
        {
            if (isNewLine(plainText.charAt(i)))
            {
                linesBeginnings.put(linesCounter, i);
                linesCounter++;
            }
        }
    }
}
