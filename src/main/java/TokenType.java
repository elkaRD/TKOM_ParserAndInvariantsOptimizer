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
/// File: TokenType.java

public enum TokenType
{
    INVALID,

    VOID,
    PARENTHESES_OPEN,
    PARENTHESES_CLOSE,
    CURLY_OPEN,
    CURLY_CLOSE,
    SQUARE_OPEN,
    SQUARE_CLOSE,
    SEMICOLON,
    IF,
    ELSE,
    WHILE,
    FOR,
    COMMA,
    RETURN,

    ASSIGN,

    //mathematical operators
    ADD,
    SUB,
    DIV,
    MUL,

    //logical operators
    AND,
    OR,
    GREATER,
    GREATER_EQUAL,
    LESS,
    LESS_EQUAL,
    EQUAL,
    NOT_EQUAL,
    NEG,

    //variable types
    INT,
    FLOAT,
    BOOL,

    //constant numeric values
    NUM_INT,
    NUM_FLOAT,

    ID,

    //boolean values
    TRUE,
    FALSE,

    //loop control keywords
    CONTINUE,
    BREAK
}
