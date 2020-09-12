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
/// File: Statement.java

import java.util.Set;
import java.util.TreeSet;

public class Statement
{
    private CharPos pos;

    public CharPos getPos()
    {
        return pos;
    }

    public void setPos(CharPos pos)
    {
        this.pos = pos;
    }

    public Set<String> getReadVars()
    {
        return new TreeSet<>();
    }

    public Set<String> getWrittenVars()
    {
        return new TreeSet<>();
    }

    public Set<String> getDeclaredVars()
    {
        return new TreeSet<>();
    }

    public boolean optimize()
    {
        return false;
    }
}
