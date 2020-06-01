import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OptimizerTest
{
    @Test
    public void notMovingReadBeforeTest()
    {
        String input = "float a = 5;\n" +
                "void main()\n" +
                "{\n" +
                "    for (int i = 0; i < 30; i = i + 1)\n" +
                "    {\n" +
                "        int d = a / 2;\n" +
                "        a = 3;\n" +
                "    }\n" +
                "}\n";

        String expectedOutput = "float a = 5;\n" +
                "void main()\n" +
                "{\n" +
                "    for (int i = 0; i < 30; i = i + 1)\n" +
                "    {\n" +
                "        int d = a / 2;\n" +
                "        a = 3; \n" +
                "    }\n" +
                "}\n";

        assertTrue(checkEncodeDecode(input, expectedOutput));
    }

    @Test
    public void movingReadAfterTest()
    {
        String input = "float a = 5;\n" +
                "void main()\n" +
                "{\n" +
                "    for (int i = 0; i < 30; i = i + 1)\n" +
                "    {\n" +
                "        a = 3;\n" +
                "        int d = a / 2;\n" +
                "    }\n" +
                "}\n";

        String expectedOutput = "float a = 5;\n" +
                "void main()\n" +
                "{\n" +
                "    a = 3;\n" +
                "    for (int i = 0; i < 30; i = i + 1)\n" +
                "    {\n" +
                "        int d = a / 2;\n" +
                "    }\n" +
                "}\n";

        assertTrue(checkEncodeDecode(input, expectedOutput));
    }

    @Test
    public void notMovingFewWritesTest()
    {
        String input = "int a = 3;\n" +
                "void main()\n" +
                "{\n" +
                "    for (int i = 0; i < 20; i = i + 1)\n" +
                "    {\n" +
                "        a = 6;\n" +
                "        a = 4;\n" +
                "    }\n" +
                "}\n";

        String expectedOutput = "int a = 3;\n" +
                "void main()\n" +
                "{\n" +
                "    for (int i = 0; i < 20; i = i + 1)\n" +
                "    {\n" +
                "        a = 6; \n" +
                "        a = 4;\n" +
                "    }\n" +
                "}\n";

        assertTrue(checkEncodeDecode(input, expectedOutput));
    }

    @Test
    public void movingShadowedTest()
    {
        String input = "int a = 3;\n" +
                "\n" +
                "void main()\n" +
                "{\n" +
                "    for (int i = 0; i < 20; i = i + 1)\n" +
                "    {\n" +
                "        a = 6;\n" +
                "        int a = 8;\n" +
                "        a = 4;\n" +
                "    }\n" +
                "}\n";

        String expectedOutput = "int a = 3;\n" +
                "\n" +
                "void main()\n" +
                "{\n" +
                "    a = 6; \n" +
                "    for (int i = 0; i < 20; i = i + 1)\n" +
                "    {\n" +
                "        int a = 8;\n" +
                "        a = 4;\n" +
                "    }\n" +
                "}\n";

        assertTrue(checkEncodeDecode(input, expectedOutput));
    }

    @Test
    public void movingWhenUsingLocallyStaticTest()
    {
        String input = "void main()\n" +
                "{\n" +
                "    for (int i = 0; i < 20; i = i + 1)\n" +
                "    {\n" +
                "        int k;\n" +
                "        for (int j = 0; j < 10; j = j + 1)\n" +
                "        {\n" +
                "            k = i;\n" +
                "        }\n" +
                "    }\n" +
                "}\n";

        String expectedOutput = "void main()\n" +
                "{\n" +
                "    for (int i = 0; i < 20; i = i + 1)\n" +
                "    {\n" +
                "        int k;\n" +
                "        k = i;\n" +
                "        for (int j = 0; j < 10; j = j + 1)\n" +
                "        {\n" +
                "        }\n" +
                "    }\n" +
                "}\n";

        assertTrue(checkEncodeDecode(input, expectedOutput));
    }

    @Test
    public void forLoopFirstParamWriteTest()
    {
        String input = "" +
                "void main() \n" +
                "{  \n" +
                "   int x = 0; \n" +
                "   for (x = 1;;) \n" +
                "       x = 2; \n" +
                "} \n";

        String expectedOutput = "" +
                "void main() \n" +
                "{  \n" +
                "   int x = 0; \n" +
                "   for (x = 1;;) \n" +
                "       x = 2; \n" +
                "} \n";

        assertTrue(checkEncodeDecode(input, expectedOutput));
    }

    @Test
    public void forLoopSecondParamWriteTest()
    {
        String input = "" +
                "void main() \n" +
                "{  \n" +
                "   int x = 0; \n" +
                "   for (; x > 1 ;) \n" +
                "       x = 2; \n" +
                "} \n";

        String expectedOutput = "" +
                "void main() \n" +
                "{  \n" +
                "   int x = 0; \n" +
                "   for (; x > 1 ;) \n" +
                "       x = 2; \n" +
                "} \n";

        assertTrue(checkEncodeDecode(input, expectedOutput));
    }

    @Test
    public void forLoopThirdParamWriteTest()
    {
        String input = "" +
                "void main() \n" +
                "{  \n" +
                "   int x = 0; \n" +
                "   for (; ; x = 1) \n" +
                "       x = 2; \n" +
                "} \n";

        String expectedOutput = "" +
                "void main() \n" +
                "{  \n" +
                "   int x = 0; \n" +
                "   for (; ; x = 1) \n" +
                "       x = 2; \n" +
                "} \n";

        assertTrue(checkEncodeDecode(input, expectedOutput));
    }

    @Test
    public void forLoopFirstParamReadTest()
    {
        String input = "" +
                "void main() \n" +
                "{  \n" +
                "   int x = 0; \n" +
                "   for (int i = x;;) \n" +
                "       x = 2; \n" +
                "} \n";

        String expectedOutput = "" +
                "void main() \n" +
                "{  \n" +
                "   int x = 0; \n" +
                "   for (int i = x;;) \n" +
                "       x = 2; \n" +
                "} \n";

        assertTrue(checkEncodeDecode(input, expectedOutput));
    }

    @Test
    public void forLoopSecondParamReadTest()
    {
        String input = "" +
                "void main() \n" +
                "{  \n" +
                "   int x = 0; " +
                "   int a = 0;\n" +
                "   for (; a > x ;) \n" +
                "       x = 2; \n" +
                "} \n";

        String expectedOutput = "" +
                "void main() \n" +
                "{  \n" +
                "   int x = 0; " +
                "   int a = 0;\n" +
                "   for (; a > x ;) \n" +
                "       x = 2; \n" +
                "} \n";

        assertTrue(checkEncodeDecode(input, expectedOutput));
    }

    @Test
    public void forLoopThirdParamReadTest()
    {
        String input = "" +
                "void main() \n" +
                "{  \n" +
                "   int x = 0; " +
                "   int a = 0;\n" +
                "   for (; ; a = x) \n" +
                "       x = 2; \n" +
                "} \n";

        String expectedOutput = "" +
                "void main() \n" +
                "{  \n" +
                "   int x = 0; " +
                "   int a = 0;" +
                "   x = 2;\n" +
                "   for (; ; a = x) \n" +
                "   {} \n" +
                "} \n";

        assertTrue(checkEncodeDecode(input, expectedOutput));
    }

    @Test
    public void emptyForLoopTest()
    {
        String input = "" +
                "void main() \n" +
                "{  \n" +
                "   int x = 0; " +
                "   for (; ; ) \n" +
                "       x = 2; \n" +
                "} \n";

        String expectedOutput = "" +
                "void main() \n" +
                "{  \n" +
                "   int x = 0; " +
                "   x = 2;\n" +
                "   for (; ; ) \n" +
                "   {} \n" +
                "} \n";

        assertTrue(checkEncodeDecode(input, expectedOutput));
    }

    @Test
    public void emptyWhileLoopTest()
    {
        String input = "" +
                "void main() \n" +
                "{  \n" +
                "   int x = 0; " +
                "   while (true) \n" +
                "       x = 2; \n" +
                "} \n";

        String expectedOutput = "" +
                "void main() \n" +
                "{  \n" +
                "   int x = 0; " +
                "   x = 2;\n" +
                "   while (true) \n" +
                "   {} \n" +
                "} \n";

        assertTrue(checkEncodeDecode(input, expectedOutput));
    }

    @Test
    public void ifBlockTest()
    {
        String input = "" +
                "void main() \n" +
                "{  \n" +
                "   int x = 0; " +
                "   int y = 1;" +
                "   if (true) " +
                "   {\n" +
                "       x = 2; " +
                "   }" +
                "   else" +
                "   {" +
                "       y = 3;" +
                "   }\n" +
                "} \n";

        String expectedOutput = "" +
                "void main() \n" +
                "{  \n" +
                "   int x = 0; " +
                "   int y = 1;" +
                "   if (true) " +
                "   {\n" +
                "       x = 2; " +
                "   }" +
                "   else" +
                "   {" +
                "       y = 3;" +
                "   }\n" +
                "} \n";

        assertTrue(checkEncodeDecode(input, expectedOutput));
    }

    @Test
    public void complexText()
    {
        String input = "int a = 5;  int b = 10; int c = 20;\n" +
                "int d = 1;  int e = 2;  int f = 3;\n" +
                "int g = 4;  int h = 6;\n" +
                "\n" +
                "void main()\n" +
                "{\n" +
                "    for (int i = 0; i < 5; i = i + 1)\n" +
                "    {\n" +
                "        int m = 5;\n" +
                "        h = 10 * a;\n" +
                "        d = b * 7 + 15;\n" +
                "        f = b * 7 + 15;\n" +
                "        c = 50;\n" +
                "        g = 20;\n" +
                "        int j = 10;\n" +
                "        while (j > 10)\n" +
                "        {\n" +
                "            j = j - 1;\n" +
                "            g = 30;\n" +
                "            e = 2 * c;\n" +
                "            for (int k = 0; k < 100; k = k + 1)\n" +
                "            {\n" +
                "                m = (i - 5) * 5;\n" +
                "                f = 20;\n" +
                "                if (f > 15)\n" +
                "                    a = 8;\n" +
                "            }\n" +
                "            f = 1;\n" +
                "        }\n" +
                "    }\n" +
                "}\n" +
                "\n";

        String expectedOutput = "int a = 5;  int b = 10; int c = 20;\n" +
                "int d = 1;  int e = 2;  int f = 3;\n" +
                "int g = 4;  int h = 6;\n" +
                "\n" +
                "void main()\n" +
                "{\n" +
                "    d = b * 7 + 15;\n" +
                "    c = 50;\n" +
                "    e = 2 * c;       \n" +
                "    for (int i = 0; i < 5; i = i + 1)\n" +
                "    {\n" +
                "        int m = 5;\n" +
                "        h = 10 * a;    \n" +
                "        f = b * 7 + 15; \n" +
                "        g = 20;\n" +
                "        int j = 10;\n" +
                "        g = 30;\n" +
                "        m = (i - 5) * 5;\n" +
                "        while (j > 10)\n" +
                "        {\n" +
                "            j = j - 1;\n" +
                "            f = 20;    \n" +
                "            for (int k = 0; k < 100; k = k + 1)\n" +
                "            {\n" +
                "                if (f > 15)\n" +
                "                    a = 8;\n" +
                "            }\n" +
                "            f = 1;\n" +
                "        }\n" +
                "    }\n" +
                "}\n" +
                "\n";

        assertTrue(checkEncodeDecode(input, expectedOutput));
    }


    private boolean checkEncodeDecode(String inputText, String expectedOutput)
    {
        InputManager input = new InputManager();
        input.readText(inputText);

        String result = null;

        try
        {
            Parser parser = new Parser();
            Optimizer optimizer = new Optimizer();
            Program program = parser.parse(input);
            result = optimizer.optimize(program).replaceAll("\\s+","");
        }
        catch (Exception e)
        {
            return false;
        }

        expectedOutput = expectedOutput.replaceAll("\\s+","");
        return result.equals(expectedOutput);
    }
}
