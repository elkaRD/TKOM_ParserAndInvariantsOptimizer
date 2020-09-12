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
/// File: SimpleStatement.java

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
