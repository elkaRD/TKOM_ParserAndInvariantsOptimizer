public interface IScanner
{
    void parseTokens(IInputManager inputManager, IParser parser);
    Token parseNextToken(IInputManager inputManager);
}
