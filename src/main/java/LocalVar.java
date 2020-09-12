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
/// File: LocalVar.java

import java.util.Set;
import java.util.TreeSet;

public class LocalVar
{
    private String var;

    private Set<Integer> reads = new TreeSet<>();
    private Set<Integer> writes = new TreeSet<>();

    private boolean declaredHere = true;
    private LocalVar replacedVar = null;

    private boolean array = false;

    public String getVar()
    {
        return var;
    }

    public void setVar(Var var)
    {
        this.var = var.getName();
        this.array = var.getIndex() != null;
    }

    public boolean isDeclaredHere()
    {
        return declaredHere;
    }

    public LocalVar getReplacedVar()
    {
        return replacedVar;
    }

    public void setReplacedVar(LocalVar replacedVar)
    {
        this.replacedVar = replacedVar;
    }

    public LocalVar copyForInheritedScope()
    {
        LocalVar newVar = new LocalVar();
        newVar.array = this.array;

        newVar.declaredHere = false;
        newVar.replacedVar = this;

        return newVar;
    }

    public boolean isArray()
    {
        return array;
    }

    public void addRead(int readLine)
    {
        reads.add(readLine);
    }

    public void addWrite(int writeLine)
    {
        writes.add(writeLine);
    }

    public Set<Integer> getReads()
    {
        return reads;
    }

    public Set<Integer> getWrites()
    {
        return writes;
    }
}
