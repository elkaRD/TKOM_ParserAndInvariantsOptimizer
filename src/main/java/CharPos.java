public class CharPos
{
    IInputManager inputManager;

    public final int cursorPos;
    public final int posInLine;
    public final int lineIndex;

    public CharPos(IInputManager inputManager, int cursorPos, int posInLine, int lineIndex)
    {
        this.inputManager = inputManager;
        this.cursorPos = cursorPos;
        this.posInLine = posInLine;
        this.lineIndex = lineIndex;
    }

    public String showCharInLine()
    {
        String result = inputManager.getLine(lineIndex) + '\n';

        for (int i = 0; i < posInLine-1; i++)
            result += ' ';
        result += '^';

        return result;
    }
}
