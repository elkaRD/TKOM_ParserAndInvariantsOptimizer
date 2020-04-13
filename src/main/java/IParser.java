public interface IParser
{
    void readToken(Token token);
    void parse(IInputManager inputManager);
}
