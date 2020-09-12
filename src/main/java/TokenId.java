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
/// File: TokenId.java

public class TokenId extends Token
{
    public TokenId(String tokenStr)
    {
        super(TokenType.ID);
        this.value = tokenStr;
        this.detailedType.add(TokenAttr.STATEMENT);
    }

    public String value;
}
