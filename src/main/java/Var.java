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
/// File: Var.java

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Var extends Expression
{
    private String name;
    private Expression index = null;

    private CharPos pos = null;

    public void setName(String name)
    {
        this.name = name;
    }

    public void setIndex(Expression index)
    {
        this.index = index;
    }

    public String getName()
    {
        return this.name;
    }

    public Expression getIndex()
    {
        return this.index;
    }

    public boolean isArray()
    {
        return index != null;
    }

    public CharPos getPos()
    {
        return pos;
    }

    public void setPos(CharPos pos)
    {
        this.pos = pos;
    }

    @Override
    public String toString()
    {
        String result = name;
        if (index != null)
            result += "[" + index + "]";

        return result;
    }

    @Override
    public Set<String> getVars()
    {
        Set<String> result = new TreeSet<>();
        result.add(name);
        return result;
    }
}
