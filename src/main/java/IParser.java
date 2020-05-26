public interface IParser
{
    void readToken(Token token);
    Program parse(IInputManager inputManager) throws Exception;
    String getParsedProgram();
}
