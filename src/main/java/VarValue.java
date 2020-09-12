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
/// File: VarValue.java

public class VarValue extends Expression
{
    private Token constValue = null;

    public void setValue(Token constValue)
    {
        this.constValue = constValue;
    }

    @Override
    public String toString()
    {
        return "" + constValue;
    }
}
