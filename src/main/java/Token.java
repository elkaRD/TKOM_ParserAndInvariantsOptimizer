public class Token
{
    public TokenType type = TokenType.INVALID;

    public String valueId;
    public int valueInt;
    public float valueFloat;

    public long cursorPos;
    public int posInLine;
    public int line;
}
