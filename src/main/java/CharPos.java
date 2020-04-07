public class CharPos
{
    IInputManager inputManager;

    public final int cursorPos;
    public final int posInLine;
    public final int lineIndex;

    private final int lenght;

    public CharPos(IInputManager inputManager, int cursorPos, int posInLine, int lineIndex)
    {
        this(inputManager, cursorPos, posInLine, lineIndex, 1);
    }

    public CharPos(IInputManager inputManager, int cursorPos, int posInLine, int lineIndex, int lenght)
    {
        this.inputManager = inputManager;
        this.cursorPos = cursorPos;
        this.posInLine = posInLine;
        this.lineIndex = lineIndex;
        this.lenght = lenght;
    }

    public String showCharInLine()
    {
        String result = inputManager.getLine(lineIndex) + '\n';

        for (int i = 0; i < posInLine; i++)
            result += ' ';
        for (int i = 0; i < lenght; i++)
            result += '^';

        return result;
    }

    public String toString()
    {
        return "[" + cursorPos + "] " + "(" + lineIndex + ", " + posInLine + ")";
    }
}
