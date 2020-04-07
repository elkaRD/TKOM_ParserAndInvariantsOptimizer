public interface IInputManager
{
     boolean isAvailableChar();
     boolean isAvailableChar(int ahead);
     char getNext();
     char peekNext();
     char peekNext(int ahead);
     String getLine(int lineNum);
     CharPos getCurrentPosition();
}
