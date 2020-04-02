public class ErrorHandler implements IErrorHandler
{
    private static ErrorHandler instance = new ErrorHandler();

    private ErrorHandler()
    {

    }

    public static ErrorHandler getInstance()
    {
        return instance;
    }

    public void displayError(String msg)
    {
        print(msg);
    }

    public void displayErrorLine(CharPos errorLine, String msg)
    {
        print(errorLine.showCharInLine());
        print("ERROR (" + errorLine.lineIndex + ", " + errorLine.posInLine + "): " + msg);
    }

    private void print(String str)
    {
        System.out.println(str);
    }
}
