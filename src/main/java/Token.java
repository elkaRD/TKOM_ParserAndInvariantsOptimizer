public class Token
{
    public Token()
    {

    }

    public Token(TokenType type)
    {
        this.type = type;
    }

    public Token(TokenType type, CharPos tokebPos)
    {
        this.type = type;
        this.tokenPos = tokenPos;
    }

    public TokenType type = TokenType.INVALID;
    public CharPos tokenPos;
}
