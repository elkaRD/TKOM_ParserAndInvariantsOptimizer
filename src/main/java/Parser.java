public class Parser implements IParser
{
    public void readToken(Token token)
    {

    }

    public void parse(IInputManager inputManager)
    {
//        IScanner scanner = new Scanner();
        parseProgram();
    }

    private void parseProgram()
    {
        Token token = nextToken();
       // if (token )
    }

    private Token nextToken()
    {
        return null;
    }
}
