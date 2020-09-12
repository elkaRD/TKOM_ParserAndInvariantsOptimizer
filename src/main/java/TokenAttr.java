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
/// File: TokenAttr.java

public enum TokenAttr
{
    SUM_OPERATOR,
    MUL_OPERATOR,
    AND_OPERATOR,
    OR_OPERATOR,
    RELATIONAL_OPERATOR,
    EQUAL_OPERATOR,

    VAR_TYPE,
    BOOL_VAL,
    NUM_VAL,
    VAR_VAL,

    STATEMENT
}
