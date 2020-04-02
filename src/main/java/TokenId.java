public class TokenId extends Token
{
    public TokenId(String tokenStr)
    {
        super(TokenType.ID);
        this.value = tokenStr;
    }

    public String value;
}
