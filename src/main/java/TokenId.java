public class TokenId extends Token
{
    public TokenId(String tokenStr)
    {
        super(TokenType.ID);
        this.value = tokenStr;
        this.detailedType.add(TokenAttr.STATEMENT);
    }

    public String value;
}
