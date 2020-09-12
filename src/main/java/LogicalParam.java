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
/// File: LogicalParam.java

public class LogicalParam extends LogicalStatement
{
    private boolean negation = false;
    private boolean bracketsNeeded = false;

    public void setNegation()
    {
        negation = true;
    }

    public void needBrackets()
    {
        bracketsNeeded = true;
    }

    @Override
    public String toString()
    {
        String result = "";

        if (negation)
            result += "!";
        if (bracketsNeeded)
            result += "(";

        result += super.toString();

        if (bracketsNeeded)
            result += ")";

        return result;
    }
}
