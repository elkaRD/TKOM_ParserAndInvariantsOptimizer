public interface IInputManager
{
     boolean isAvailableChar();
     boolean isAvailableChar(int ahead);
     char getNext();
     char peekNext();
     char peekNext(int ahead);
     //char peekPrev();
     //void unget();
     String getLine(int lineNum);
     CharPos getCurrentPosition();
}
