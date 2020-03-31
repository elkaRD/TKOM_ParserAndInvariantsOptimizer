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
