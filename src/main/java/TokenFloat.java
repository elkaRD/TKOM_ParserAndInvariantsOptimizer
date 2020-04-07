public class TokenFloat extends Token
{
    public TokenFloat(String tokenStr)
    {
        super(TokenType.NUM_FLOAT);
        this.value = Float.parseFloat(tokenStr);
    }

    public TokenFloat(float value)
    {
        super(TokenType.NUM_FLOAT);
        this.value = value;
    }

    public float value;
}
