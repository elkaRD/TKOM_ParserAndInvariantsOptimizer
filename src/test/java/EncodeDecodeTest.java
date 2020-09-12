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
/// File: EncodeDecodeTest.java

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EncodeDecodeTest
{
    @Test
    public void emptyMainEncodeDecodeTest()
    {
        assertTrue(checkEncodeDecode("void main () {}"));
    }

    @Test
    public void globalInitVarEncodeDecodeTest()
    {
        assertTrue(checkEncodeDecode("int a; void main () {}"));
        assertTrue(checkEncodeDecode("int x = 5; void main () {}"));
        assertTrue(checkEncodeDecode("float a; int b = 2; bool c = false; void main () {}"));
    }

    @Test
    public void initVarEncodeDecodeInMainTest()
    {
        assertTrue(checkEncodeDecodeInMain("int a;"));
        assertTrue(checkEncodeDecodeInMain("int a = 3;"));
        assertTrue(checkEncodeDecodeInMain("float f;"));
        assertTrue(checkEncodeDecodeInMain("float f = 4;"));
        assertTrue(checkEncodeDecodeInMain("float f = 4.5;"));
        assertTrue(checkEncodeDecodeInMain("bool b;"));
        assertTrue(checkEncodeDecodeInMain("bool b = false;"));
    }

    @Test
    public void initTabVarEncodeDecodeInMainTest()
    {
        assertTrue(checkEncodeDecodeInMain("int a[5];"));
        assertTrue(checkEncodeDecodeInMain("int a[4] = 3;"));
        assertTrue(checkEncodeDecodeInMain("float f[7];"));
        assertTrue(checkEncodeDecodeInMain("float f[9] = 4;"));
        assertTrue(checkEncodeDecodeInMain("float f[19] = 4.5;"));
        assertTrue(checkEncodeDecodeInMain("bool b[2];"));
        assertTrue(checkEncodeDecodeInMain("bool b[90] = false;"));
    }

    @Test
    public void assignVarEncodeDecodeInMainTest()
    {
        assertTrue(checkEncodeDecodeInMain("int x; x = 5;"));
        assertTrue(checkEncodeDecodeInMain("float x; x = 5.4;"));
        assertTrue(checkEncodeDecodeInMain("bool x; x = true;"));
    }

    @Test
    public void assignVarTabTest()
    {
        assertTrue(checkEncodeDecodeInMain("int x[12]; x[0] = 5;"));
        assertTrue(checkEncodeDecodeInMain("float x[13]; x[3] = 5.4;"));
        assertTrue(checkEncodeDecodeInMain("bool x[14]; x[6] = true;"));
    }

    @Test
    public void indexEncodeDecodeInMainTest()
    {
        assertTrue(checkEncodeDecodeInMain("int x[20]; for (int i = 0; i < 20; i = i+1) { x[i] = i * i; }"));
        assertTrue(checkEncodeDecodeInMain("int x[20]; for (int i = 0; i < 10; i = i+1) { x[i*2] = i * i; }"));
        assertTrue(checkEncodeDecodeInMain("int x[20]; for (int i = 0; i < 3; i = i+1) { x[(i * 2) + 5] = i * i; }"));
    }

    @Test
    public void getTabVarEncodeDecodeInMainTest()
    {
        assertTrue(checkEncodeDecodeInMain("int y[50]; int x = y[2];"));
        assertTrue(checkEncodeDecodeInMain("int y[50]; int x = y[2] * 3;"));
        assertTrue(checkEncodeDecodeInMain("int y[50]; int x = (3 * y[2] + 3) + 1;"));
    }

    @Test
    public void blockEncodeDecodeInMainTest()
    {
        assertTrue(checkEncodeDecodeInMain("{}"));
        assertTrue(checkEncodeDecodeInMain("int x = 0;"));
        assertTrue(checkEncodeDecodeInMain("return;"));
        assertTrue(checkEncodeDecodeInMain("{ int x = 0; }"));
        assertTrue(checkEncodeDecodeInMain("{ int x = 0; }"));
        assertTrue(checkEncodeDecodeInMain("{ if (true) { break; } while (true) { continue; } int x = 0;" +
                " x = 2; return; }"));
    }

    @Test
    public void statementEncodeDecodeInMainTest()
    {
        assertTrue(checkEncodeDecodeInMain("return;"));
        assertTrue(checkEncodeDecodeInMain("break;"));
        assertTrue(checkEncodeDecodeInMain("continue;"));
        assertTrue(checkEncodeDecodeInMain("int x = 2;"));
        assertTrue(checkEncodeDecodeInMain("int x = 1; if (true) x = 2;"));
        assertTrue(checkEncodeDecodeInMain("if (false) {}"));
        assertTrue(checkEncodeDecodeInMain("for (;;) {}"));
        assertTrue(checkEncodeDecodeInMain("while (0){}"));
    }

    @Test
    public void logicalStatementEncodeDecodeInMainTest()
    {
        assertTrue(checkEncodeDecodeInMain("if ((1 >= 2) || true) {}"));
        assertTrue(checkEncodeDecodeInMain("if (true || false) {}"));
        assertTrue(checkEncodeDecodeInMain("if ((1 >= 2) || (1 * 3)) {}"));
        assertTrue(checkEncodeDecodeInMain("if (true || (true)) {}"));
        assertTrue(checkEncodeDecodeInMain("if (true || false || true) {}"));
    }

    @Test
    public void andConditionEncodeDecodeInMainTest()
    {
        assertTrue(checkEncodeDecodeInMain("if ((1 >= 2) && true) {}"));
        assertTrue(checkEncodeDecodeInMain("if (true && false) {}"));
        assertTrue(checkEncodeDecodeInMain("if ((1 >= 2) && (1 * 3)) {}"));
        assertTrue(checkEncodeDecodeInMain("if (true && (true)) {}"));
        assertTrue(checkEncodeDecodeInMain("if (true && false && true) {}"));
    }

    @Test
    public void equalConditionEncodeDecodeInMainTest()
    {
        assertTrue(checkEncodeDecodeInMain("if (1 == 2) {}"));
        assertTrue(checkEncodeDecodeInMain("if (1 != 2) {}"));
    }

    @Test
    public void relationalConditionEncodeDecodeInMainTest()
    {
        assertTrue(checkEncodeDecodeInMain("if (1 > 2) {}"));
        assertTrue(checkEncodeDecodeInMain("if (1 < 2) {}"));
        assertTrue(checkEncodeDecodeInMain("if (1 >= 2) {}"));
        assertTrue(checkEncodeDecodeInMain("if (1 <= 2) {}"));
    }

    @Test
    public void logicalParamEncodeDecodeInMainTest()
    {
        assertTrue(checkEncodeDecodeInMain("if (true) {}"));
        assertTrue(checkEncodeDecodeInMain("if ((true)) {}"));
        assertTrue(checkEncodeDecodeInMain("if (false) {}"));
        assertTrue(checkEncodeDecodeInMain("int x = 0; if (x) {}"));
        assertTrue(checkEncodeDecodeInMain("float x; if (x) {}"));
        assertTrue(checkEncodeDecodeInMain("bool x; if (x) {}"));
        assertTrue(checkEncodeDecodeInMain("if ((5 + 2)) {}"));
    }

    @Test
    public void logicalNegationEncodeDecodeInMainTest()
    {
        assertTrue(checkEncodeDecodeInMain("if (!false) {}"));
        assertTrue(checkEncodeDecodeInMain("if (!3) {}"));
        assertTrue(checkEncodeDecodeInMain("bool x; if (!(x)) {}"));
        assertTrue(checkEncodeDecodeInMain("bool x; if (true && !(x)) {}"));
    }

    @Test
    public void expressionEncodeDecodeInMainTest()
    {
        assertTrue(checkEncodeDecodeInMain("int x = 20*40 + 2;"));
        assertTrue(checkEncodeDecodeInMain("int x = 2 + 20*40*80*100;"));
        assertTrue(checkEncodeDecodeInMain("int x = 20*40 + 50 * 20;"));
        assertTrue(checkEncodeDecodeInMain("int x = 2 + 3 + 4;"));
    }

    @Test
    public void multiExpressionEncodeDecodeInMainTest()
    {
        assertTrue(checkEncodeDecodeInMain("int x = 20*40;"));
        assertTrue(checkEncodeDecodeInMain("int x = 20*40*80*100;"));
    }

    @Test
    public void multiParamEncodeDecodeInMainTest()
    {
        assertTrue(checkEncodeDecodeInMain("int x = 20;"));
        assertTrue(checkEncodeDecodeInMain("float x = 20.5;"));
        assertTrue(checkEncodeDecodeInMain("bool x = true;"));
        assertTrue(checkEncodeDecodeInMain("int x = (20 / 4);"));
        assertTrue(checkEncodeDecodeInMain("int x = ((20));"));
        assertTrue(checkEncodeDecodeInMain("int y; int x = y;"));
    }

    @Test
    public void negativeExprEncodeDecodeInMainTest()
    {
        assertTrue(checkEncodeDecodeInMain("int x = -20;"));
        assertTrue(checkEncodeDecodeInMain("int x = -(20);"));
        assertTrue(checkEncodeDecodeInMain("int x = -((20));"));
        assertTrue(checkEncodeDecodeInMain("int x = -((20 - 3) / 5);"));
        assertTrue(checkEncodeDecodeInMain("int x = 30 - ((20 - 3) / 5);"));
    }

    @Test
    public void forLoopEncodeDecodeInMainTest()
    {
        assertTrue(checkEncodeDecodeInMain("for (int i = 0; i < 10; i = i + 1) {}"));
        assertTrue(checkEncodeDecodeInMain("for (int i = 0; i < 10; i = i + 1) {}"));
        assertTrue(checkEncodeDecodeInMain("for (int i = 0; true; i = i + 1) {}"));
        assertTrue(checkEncodeDecodeInMain("for (;;) {}"));
        assertTrue(checkEncodeDecodeInMain("for (int i = 0; i < 10; i = i + 1) i = 2;"));
    }

    @Test
    public void whileLoopEncodeDecodeInMainTest()
    {
        assertTrue(checkEncodeDecodeInMain("int x; while (x < 10) {}"));
        assertTrue(checkEncodeDecodeInMain("while (true) {}"));
        assertTrue(checkEncodeDecodeInMain("int x; while (x < 10) x = 2;"));
    }

    @Test
    public void ifEncodeDecodeInMainTest()
    {
        assertTrue(checkEncodeDecodeInMain("if (0 == 1) {}"));
        assertTrue(checkEncodeDecodeInMain("int x; if (x == 1) x = 2;"));
        assertTrue(checkEncodeDecodeInMain("int x; if (x == 1) x = 2; else {}"));
        assertTrue(checkEncodeDecodeInMain("int x; if (x == 1) x = 2; else x = 3;"));
    }

    @Test
    public void decodeEncodeTest()
    {
        String program = "bool t = true; \n" +
                "float a = 5.0; \n" +
                "int x; \n" +
                "void main () \n" +
                "{ \n" +
                "   for (int i = 0; !(i < 20); i = i + 1 ) \n" +
                "   { \n" +
                "       int b = 3 * i + (12 / i); b = 12;  \n" +
                "   }  \n" +
                "   while (x <= 1 || x > 10 || x == 1 && !(x!=5))  \n" +
                "   { \n" +
                "      x = x + 20; \n" +
                "      float c = -(a+20 + -x); \n" +
                "      if (true) { continue; } else break;  \n" +
                "   } \n" +
                "} \n";

        assertTrue(checkEncodeDecode(program));
    }

    private boolean checkEncodeDecode(String inputText)
    {
        InputManager input = new InputManager();
        input.readText(inputText);

        String result = null;

        try
        {
            IParser parser = new Parser();
            parser.parse(input);
            result = parser.getParsedProgram().replaceAll("\\s+","");
        }
        catch (Exception e)
        {
            return false;
        }

        inputText = inputText.replaceAll("\\s+","");
        return result.equals(inputText);
    }
    
    private boolean checkEncodeDecodeInMain(String inputText)
    {
        return checkEncodeDecode("void main () { " + inputText + "}");
    }
}
