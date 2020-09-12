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
/// File: ExpressionParam.java

import java.util.ArrayList;
import java.util.List;

public class ExpressionParam extends Expression
{
    private boolean negative = false;
    private boolean areBracketsRequired = false;

    public void setNegative()
    {
        negative = true;
    }

    public void bracketsRequired()
    {
        areBracketsRequired = true;
    }

    @Override
    public String toString()
    {
        String result = "";

        if (negative)
            result += "-";

        if (areBracketsRequired)
            return result + "(" + super.toString() + ")";

        return result + super.toString();
    }
}
