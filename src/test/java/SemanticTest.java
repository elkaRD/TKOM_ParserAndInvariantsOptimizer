import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SemanticTest
{
    @Test
    public void usingDeclaredVarsTest()
    {
        assertTrue(checkCodeInMain("int a; a = 2;"));
        assertTrue(checkCodeInMain("int a; if (true) { a = 2; }"));
        assertTrue(checkCodeInMain("int a; if (true) { while (false) { a = 2; } }"));
    }

    @Test
    public void usingUndeclaredVarsTest()
    {
        assertFalse(checkCodeInMain("a = 2;"));
        assertFalse(checkCodeInMain("if (true) { a = 2; }"));
        assertFalse(checkCodeInMain("if (true) { while (false) { a = 2; } }"));
    }

    @Test
    public void usingDeclaredGlobalVarsTest()
    {
        assertTrue(checkCodeInMainWithGlobalVars("int a;", "a = 2;"));
        assertTrue(checkCodeInMainWithGlobalVars("int a;", "if (true) { a = 2; }"));
        assertTrue(checkCodeInMainWithGlobalVars("int a;", "if (true) { while (false) { a = 2; } }"));
    }

    @Test
    public void usingDeclaredTabVarsTest()
    {
        assertTrue(checkCodeInMain("int a[10]; a[2] = 2;"));
        assertTrue(checkCodeInMain("int a[10]; if (true) { a[2] = 2; }"));
        assertTrue(checkCodeInMain("int a[10]; if (true) { while (false) { a[2] = 2; } }"));
    }

    @Test
    public void usingUndeclaredTabVarsTest()
    {
        assertFalse(checkCodeInMain("a[2] = 2;"));
        assertFalse(checkCodeInMain("if (true) { a[2] = 2; }"));
        assertFalse(checkCodeInMain("if (true) { while (false) { a[2] = 2; } }"));
    }

    @Test
    public void usingDeclaredGlobalTabVarsTest()
    {
        assertTrue(checkCodeInMainWithGlobalVars("int a[7];", "a[5] = 2;"));
        assertTrue(checkCodeInMainWithGlobalVars("int a[7];", "if (true) { a[5] = 2; }"));
        assertTrue(checkCodeInMainWithGlobalVars("int a[7];", "if (true) { while (false) { a[5] = 2; } }"));
    }

    @Test
    public void varsInForLoopTest()
    {
        assertTrue(checkCodeInMain("for (int i = 0; i < 0; ) {}"));
        assertTrue(checkCodeInMain("for (int i = 0; ; i = i + 1 ) {}"));
        assertTrue(checkCodeInMain("float x = 2; for (int i = 0; i < x; ) {}"));

        assertFalse(checkCodeInMain("for (int i = 0; i < 0; ) {} i = 5;"));
    }

    @Test
    public void usingIndexWithNotTableVarsTest()
    {
        assertFalse(checkCodeInMain("int x; x[2] = 5;"));
    }

    @Test
    public void skipIndexWithTableVarsTest()
    {
        assertFalse(checkCodeInMain("int x[22]; x = 4;"));
    }

    @Test
    public void varRedeclarationTest()
    {
        assertFalse(checkCodeInMain("int x; int x;"));
        assertFalse(checkCodeInMain("int x; float x;"));
    }

    @Test
    public void varShadowingTest()
    {
        assertTrue(checkCodeInMain("int x; { int x; }"));
    }

    @Test
    public void varShadowingDiffrentTypeTest()
    {
        assertTrue(checkCodeInMain("bool x; { float x; }"));
    }

    @Test
    public void varShadowingFromTableToSingleTest()
    {
        assertTrue(checkCodeInMain("int x[10]; { int x; }"));
        assertTrue(checkCodeInMain("int x[10]; { int x; x = 4; }"));
        assertTrue(checkCodeInMain("int x[10]; { int x; } x[2] = 7;"));
        assertTrue(checkCodeInMain("int x[10]; { int x; x = 4; } x[2] = 7;"));
    }

    @Test
    public void varShadowingFromSingleToTableTest()
    {
        assertTrue(checkCodeInMain("int x; { int x[8]; }"));
        assertTrue(checkCodeInMain("int x; { int x[8]; x[0] = 4; }"));
        assertTrue(checkCodeInMain("int x; { int x[8]; } x = 7;"));
        assertTrue(checkCodeInMain("int x; { int x[8]; x[0] = 4; } x = 7;"));
    }

    @Test
    public void codeTest()
    {
        String example = "" +
                "bool t[20] = true; \n" +
                "float a = 5.0; \n" +
                "void main () \n" +
                "{ \n" +
                "   int z = 0; \n" +
                "   for (int i = 0; !(i < 20); i = i + 1 ) \n" +
                "   { \n" +
                "       int b = 3 * i + (12 / i); b = 12;  \n" +
                "       t[i] = 20; \n" +
                "   }  \n" +
                "   int c; int x; \n" +
                "   while (x <= 1 || x > 10 || (x == 1 && !(x!=5)))  \n" +
                "   { \n" +
                "      x = x + 20; \n" +
                "      c = -(a+20 + -x); \n" +
                "       if (true) { continue; } else break; \n" +
                "   } \n" +
                "   while (true) \n" +
                "       t[1] = 3; \n" +
                "} \n";

        assertFalse(foundError(example));
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

    private boolean checkCodeInMainWithGlobalVars(String globalVars, String codeInMain)
    {
        String inputText = globalVars + " void main () { " + codeInMain + " }";
        return !foundError(inputText);
    }
}
