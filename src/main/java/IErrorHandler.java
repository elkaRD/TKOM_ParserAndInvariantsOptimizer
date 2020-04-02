public interface IErrorHandler
{
    void displayError(String msg);
    void displayErrorLine(CharPos errorLine, String msg);
}
