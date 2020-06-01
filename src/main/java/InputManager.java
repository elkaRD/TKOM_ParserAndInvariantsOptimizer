import java.io.BufferedReader;
import java.io.FileReader;
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
        reset();
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(pathToFile));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            plainText = sb.toString();
            br.close();
        }
        catch (Exception e)
        {
            System.out.println("Got exception when trying to read source file " + pathToFile);
        }
        detectNewLines();
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

        cursor++;
        posInLine++;
        if (isNewLine(result))
        {
            posInLine = 0;
            lineIndex++;
        }

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

    public String getLine(int lineNum)
    {
        if (lineNum+1 < linesBeginnings.size())
            return plainText.substring(linesBeginnings.get(lineNum) + 1, linesBeginnings.get(lineNum+1));

        return plainText.substring(linesBeginnings.get(lineNum) + 1);
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
        linesBeginnings.put(0, -1);
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
