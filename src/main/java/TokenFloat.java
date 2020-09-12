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
/// File: TokenFloat.java

public class TokenFloat extends Token
{
    public TokenFloat(String tokenStr)
    {
        super(TokenType.NUM_FLOAT);
        this.value = Float.parseFloat(tokenStr);
        this.detailedType.add(TokenAttr.NUM_VAL);
        this.detailedType.add(TokenAttr.VAR_VAL);
    }

    public TokenFloat(float value)
    {
        super(TokenType.NUM_FLOAT);
        this.value = value;
    }

    public float value;

    @Override
    public String toString()
    {
        return "" + value;
    }
}
