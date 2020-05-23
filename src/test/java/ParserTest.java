import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParserTest
{
    @Test
    public void emptyMainTest()
    {
        assertFalse(foundError("void main () {}"));
    }

    @Test
    public void globalInitVarTest()
    {
        assertFalse(foundError("int a; void main () {}"));
        assertFalse(foundError("int x = 5; void main () {}"));
        assertFalse(foundError("float a; int b = 2; bool c = false; void main () {}"));
    }

    @Test
    public void initVarTest()
    {
        assertTrue(checkCodeInMain("int a;"));
        assertTrue(checkCodeInMain("int a = 3;"));
        assertTrue(checkCodeInMain("float f;"));
        assertTrue(checkCodeInMain("float f = 4;"));
        assertTrue(checkCodeInMain("float f = 4.5;"));
        assertTrue(checkCodeInMain("bool b;"));
        assertTrue(checkCodeInMain("bool b = false;"));
    }

    @Test
    public void initTabVarTest()
    {
        assertTrue(checkCodeInMain("int a[5];"));
        assertTrue(checkCodeInMain("int a[4] = 3;"));
        assertTrue(checkCodeInMain("float f[7];"));
        assertTrue(checkCodeInMain("float f[9] = 4;"));
        assertTrue(checkCodeInMain("float f[19] = 4.5;"));
        assertTrue(checkCodeInMain("bool b[2];"));
        assertTrue(checkCodeInMain("bool b[90] = false;"));
    }

    @Test
    public void assignVarTest()
    {
        assertTrue(checkCodeInMain("int x; x = 5;"));
        assertTrue(checkCodeInMain("float x; x = 5.4;"));
        assertTrue(checkCodeInMain("bool x; x = true;"));
    }

    @Test
    public void assignVarTabTest()
    {
        assertTrue(checkCodeInMain("int x[12]; x[0] = 5;"));
        assertTrue(checkCodeInMain("float x[13]; x[3] = 5.4;"));
        assertTrue(checkCodeInMain("bool x[14]; x[6] = true;"));
    }

    @Test
    public void indexTest()
    {
        assertTrue(checkCodeInMain("int x[20]; for (int i = 0; i < 20; i = i+1) { x[i] = i * i; }"));
        assertTrue(checkCodeInMain("int x[20]; for (int i = 0; i < 10; i = i+1) { x[i*2] = i * i; }"));
        assertTrue(checkCodeInMain("int x[20]; for (int i = 0; i < 3; i = i+1) { x[(i * 2) + 5] = i * i; }"));
    }

    @Test
    public void getTabVarTest()
    {
        assertTrue(checkCodeInMain("int y[50]; int x = y[2];"));
        assertTrue(checkCodeInMain("int y[50]; int x = y[2] * 3;"));
        assertTrue(checkCodeInMain("int y[50]; int x = (3 * y[2] + 3) + 1;"));
    }

    @Test
    public void blockTest()
    {
        assertTrue(checkCodeInMain("{}"));
        assertTrue(checkCodeInMain("int x = 0;"));
        assertTrue(checkCodeInMain("return;"));
        assertTrue(checkCodeInMain("{ int x = 0; }"));
        assertTrue(checkCodeInMain("{ int x = 0; }"));
        assertTrue(checkCodeInMain("{ if (true) { break; } while (true) { continue; } int x = 0;" +
                " x = 2; return; }"));
    }

    @Test
    public void statementTest()
    {
        assertTrue(checkCodeInMain("return;"));
        assertTrue(checkCodeInMain("break;"));
        assertTrue(checkCodeInMain("continue;"));
        assertTrue(checkCodeInMain("int x = 2;"));
        assertTrue(checkCodeInMain("int x = 1; if (true) x = 2;"));
        assertTrue(checkCodeInMain("if (false) {}"));
        assertTrue(checkCodeInMain("for (;;) {}"));
        assertTrue(checkCodeInMain("while (0){}"));
    }

    @Test
    public void logicalStatementTest()
    {
        assertTrue(checkCodeInMain("if ((1 >= 2) || true) {}"));
        assertTrue(checkCodeInMain("if (true || false) {}"));
        assertTrue(checkCodeInMain("if ((1 >= 2) || (1 * 3)) {}"));
        assertTrue(checkCodeInMain("if (true || (true)) {}"));
        assertTrue(checkCodeInMain("if (true || false || true) {}"));
    }

    @Test
    public void andConditionTest()
    {
        assertTrue(checkCodeInMain("if ((1 >= 2) && true) {}"));
        assertTrue(checkCodeInMain("if (true && false) {}"));
        assertTrue(checkCodeInMain("if ((1 >= 2) && (1 * 3)) {}"));
        assertTrue(checkCodeInMain("if (true && (true)) {}"));
        assertTrue(checkCodeInMain("if (true && false && true) {}"));
    }

    @Test
    public void equalConditionTest()
    {
        assertTrue(checkCodeInMain("if (1 == 2) {}"));
        assertTrue(checkCodeInMain("if (1 != 2) {}"));
    }

    @Test
    public void relationalConditionTest()
    {
        assertTrue(checkCodeInMain("if (1 > 2) {}"));
        assertTrue(checkCodeInMain("if (1 < 2) {}"));
        assertTrue(checkCodeInMain("if (1 >= 2) {}"));
        assertTrue(checkCodeInMain("if (1 <= 2) {}"));
    }

    @Test
    public void logicalParamTest()
    {
        assertTrue(checkCodeInMain("if (true) {}"));
        assertTrue(checkCodeInMain("if ((true)) {}"));
        assertTrue(checkCodeInMain("if (false) {}"));
        assertTrue(checkCodeInMain("int x = 0; if (x) {}"));
        assertTrue(checkCodeInMain("float x; if (x) {}"));
        assertTrue(checkCodeInMain("bool x; if (x) {}"));
        assertTrue(checkCodeInMain("if ((5 + 2)) {}"));
    }

    @Test
    public void logicalNegationEncodeDecodeInMainTest()
    {
        assertTrue(checkCodeInMain("if (!false) {}"));
        assertTrue(checkCodeInMain("if (!3) {}"));
        assertTrue(checkCodeInMain("bool x; if (!(x)) {}"));
        assertTrue(checkCodeInMain("bool x; if (true && !(x)) {}"));
    }

    @Test
    public void expressionTest()
    {
        assertTrue(checkCodeInMain("int x = 20*40 + 2;"));
        assertTrue(checkCodeInMain("int x = 2 + 20*40*80*100;"));
        assertTrue(checkCodeInMain("int x = 20*40 + 50 * 20;"));
        assertTrue(checkCodeInMain("int x = 2 + 3 + 4;"));
    }

    @Test
    public void multiExpressionTest()
    {
        assertTrue(checkCodeInMain("int x = 20*40;"));
        assertTrue(checkCodeInMain("int x = 20*40*80*100;"));
    }

    @Test
    public void multiParamTest()
    {
        assertTrue(checkCodeInMain("int x = 20;"));
        assertTrue(checkCodeInMain("float x = 20.5;"));
        assertTrue(checkCodeInMain("bool x = true;"));
        assertTrue(checkCodeInMain("int x = (20 / 4);"));
        assertTrue(checkCodeInMain("int x = ((20));"));
        assertTrue(checkCodeInMain("int y; int x = y;"));
    }

    @Test
    public void negativeExprTest()
    {
        assertTrue(checkCodeInMain("int x = -20;"));
        assertTrue(checkCodeInMain("int x = -(20);"));
        assertTrue(checkCodeInMain("int x = -((20));"));
        assertTrue(checkCodeInMain("int x = -((20 - 3) / 5);"));
        assertTrue(checkCodeInMain("int x = 30 - ((20 - 3) / 5);"));
    }

    @Test
    public void forLoopTest()
    {
        assertTrue(checkCodeInMain("for (int i = 0; i < 10; i = i + 1) {}"));
        assertTrue(checkCodeInMain("for (int i = 0; i < 10; i = i + 1) {}"));
        assertTrue(checkCodeInMain("for (int i = 0; true; i = i + 1) {}"));
        assertTrue(checkCodeInMain("for (;;) {}"));
        assertTrue(checkCodeInMain("for (int i = 0; i < 10; i = i + 1) i = 2;"));
    }

    @Test
    public void whileLoopTest()
    {
        assertTrue(checkCodeInMain("while (2 < 10) {}"));
        assertTrue(checkCodeInMain("while (true) {}"));
        assertTrue(checkCodeInMain("int x; while (x < 10) x = 2;"));
    }

    @Test
    public void ifTest()
    {
        assertTrue(checkCodeInMain("if (0 == 1) {}"));
        assertTrue(checkCodeInMain("int x; if (x == 1) x = 2;"));
        assertTrue(checkCodeInMain("int x; if (x == 1) x = 2; else {}"));
        assertTrue(checkCodeInMain("int x; if (x == 1) x = 2; else x = 3;"));
    }

    private boolean foundError(String inputText)
    {
        InputManager input = new InputManager();
        input.readText(inputText);

        try
        {
            IParser parser = new Parser();
            parser.parse(input);
        }
        catch (Exception e)
        {
            return true;
        }

        return false;
    }

    private boolean checkCodeInMain(String codeInMain)
    {
        String inputText = "void main () { " + codeInMain + " }";
        return !foundError(inputText);
    }
}