public class TokenInt extends Token
{
    public TokenInt(String tokenStr)
    {
        super(TokenType.NUM_INT);
        this.value = Integer.parseInt(tokenStr);
    }

    public TokenInt(int value)
    {
        super(TokenType.NUM_INT);
        this.value = value;
    }

    public int value;
}
