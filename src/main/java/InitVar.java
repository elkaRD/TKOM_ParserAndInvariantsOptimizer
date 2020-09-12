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
/// File: InitVar.java

import java.util.Set;
import java.util.TreeSet;

public class InitVar extends Statement
{
    private Token varType = null;
    private Var var = null;
    private Expression varValue = null;

    public void setType(Token varType)
    {
        this.varType = varType;
    }

    public void setVar(Var var)
    {
        this.var = var;
    }

    public Var getVar()
    {
        return var;
    }

    public void setVarValue(Expression varValue)
    {
        this.varValue = varValue;
    }

    public Expression getVarValue()
    {
        return this.varValue;
    }

    @Override
    public String toString()
    {
        String result = ReservedTokens.getInstance().getStr(varType.type) + " " + var;
        if (varValue != null)
            result += " = " + varValue;

        return result;
    }

    @Override
    public Set<String> getReadVars()
    {
        Set<String> result = new TreeSet<>();
        if (var.getIndex() != null)
            result.addAll(var.getIndex().getVars());
        if (varValue != null)
            result.addAll(varValue.getVars());

        return result;
    }

    @Override
    public Set<String> getWrittenVars()
    {
        Set<String> result = new TreeSet<>();
        result.add(var.getName());

        return result;
    }

    @Override
    public Set<String> getDeclaredVars()
    {
        Set<String> result =  new TreeSet<>();
        result.add(var.getName());

        return result;
    }
}
