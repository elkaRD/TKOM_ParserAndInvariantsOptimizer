public class SimpleStatement extends Statement
{
    private Token token = null;

    public SimpleStatement(Token token)
    {
        this.token = token;
    }

    @Override
    public String toString()
    {
        return ReservedTokens.getInstance().getStr(token.type);
    }
}
