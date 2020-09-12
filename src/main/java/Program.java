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
/// File: Program.java

import java.util.ArrayList;
import java.util.List;

public class Program
{
    private List<InitVar> globalVars= new ArrayList<>();
    private DefFunction mainFunction;

    public void addGlobalVar(InitVar var)
    {
        globalVars.add(var);
    }

    public void setMainFunction(DefFunction function)
    {
        mainFunction = function;
    }

    @Override
    public String toString()
    {
        String result = "";
        for (InitVar var : globalVars)
            result += var + ";\n";
        result += mainFunction;

        return result;
    }

    public Block getBlock()
    {
        return mainFunction.getBlock();
    }

    public boolean optimize()
    {
        return mainFunction.optimize();
    }
}
