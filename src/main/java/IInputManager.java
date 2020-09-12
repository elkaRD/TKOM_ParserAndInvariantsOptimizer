///
/// EN: Project for the ‘Compilation Techniques’ course
///     Warsaw University of Technology
///     Parser and Optimizer of Own Programming Language
///
/// PL: Projekt TKOM (Techniki kompilacji)
///     PW WEiTI 20L
///     Parser i optymalizator wlasnego jezyka programowania
///
/// Copyright (C) Robert Dudzinski 2020
///
/// File: IInputManager.java

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
