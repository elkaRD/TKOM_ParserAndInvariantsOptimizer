public class TokenFloat extends Token
{
    public TokenFloat(String tokenStr)
    {
        super(TokenType.NUM_FLOAT);
        this.value = Float.parseFloat(tokenStr);
        this.detailedType.add(TokenAttr.NUM_VAL);
        this.detailedType.add(TokenAttr.VAR_VAL);
    }

    public TokenFloat(float value)
    {
        super(TokenType.NUM_FLOAT);
        this.value = value;
    }

    public float value;

    @Override
    public String toString()
    {
        return "" + value;
    }
}
