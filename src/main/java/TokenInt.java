public class TokenInt extends Token
{
    public TokenInt(String tokenStr)
    {
        super(TokenType.NUM_INT);
        this.value = Integer.parseInt(tokenStr);
        this.detailedType.add(TokenAttr.NUM_VAL);
        this.detailedType.add(TokenAttr.VAR_VAL);
    }

    public TokenInt(int value)
    {
        super(TokenType.NUM_INT);
        this.value = value;
    }

    public int value;

    @Override
    public String toString()
    {
        return "" + value;
    }
}
