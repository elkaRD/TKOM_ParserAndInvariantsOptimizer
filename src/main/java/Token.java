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
/// File: Token.java

import java.util.EnumSet;

public class Token
{
    public Token()
    {

    }

    public Token(TokenType type)
    {
        this.type = type;
    }

    public Token(TokenType type, CharPos tokebPos)
    {
        this.type = type;
        this.tokenPos = tokenPos;
    }

    public TokenType type = TokenType.INVALID;
    public CharPos tokenPos;
    public EnumSet<TokenAttr> detailedType = EnumSet.noneOf(TokenAttr.class);

    @Override
    public String toString()
    {
        return "" + ReservedTokens.getInstance().getStr(type);
    }
}
