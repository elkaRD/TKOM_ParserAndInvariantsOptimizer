public class TKOM
{
    public static void main(String[] args)
    {
//        String example = "" +
//                "bool t[20] = true; \n" +
//                "float a = 5.0; \n" +
//                "void main () \n" +
//                "{ \n" +
//                "   int z = 0; \n" +
//                "   for (int i = 0; !(i < 20); i = i + 1 ) \n" +
//                "   { \n" +
//                "       int b = 3 * i + (12 / i); b = 12;  \n" +
////                "       int b = 3; \n" +
//                "       t[i] = 20;\n" +
//                "   }  \n" +
//                "   int c; int x; \n" +
//                "   while (x <= 1 || x > 10 || (x == 1 && !(x!=5)))  \n" +
//                "   { \n" +
//                "      x = x + 20; \n" +
//                "      c = -(a+20 + -x); \n" +
//                "       if (true) { continue; } else break; \n" +
//                "   } \n" +
//                "   while (true) \n" +
//                "       t[1] = 3; \n" +
//                "} \n";

//        String example = "" +
//                //"int a = 5; \n" +
//                "" +
//                "void main() \n" +
//                "{ int a; int b = a;\n" +
////                "   for (int i = 0; i < 10; i = i + 1) \n" +
////                "   { \n" +
////                "        \n" +
////                "   } \n" +
//                "} \n";

//        String example = "" +
//                "void main()" +
//                "{ \n" +
//                "   int tab[20]; \n" +
//                "   int a = 3 * tab[0]; \n" +
//                "   { int tab; \n" +
//                "       tab[0] = 4; } \n" +
//                "} \n";

//        String example = "" +
//                "void main()" +
//                "{" +
//                "   int a;" +
//                "   float b;" +
//                "   bool c;" +
//                "   int x;" +
//                "   for (int i = a; i < 10; i = i + b)" +
//                "   {   " +
//                "       b = 2 * i;" +
//                "       float first;" +
//                "       float second;" +
//                "       int t1;" +
//                "       int t2;" +
//                "       while (true)" +
//                "       {" +
//                "           x = i;" +
//                "           i = i * 2;" +
//                "           t1 = first;" +
//                "           t2 = second;" +
//                "           if (false)" +
//                "           {" +
//                "               b = i;" +
//                "               for (;;)" +
//                "                   b = i;" +
//                "           }" +
//                "           while (false)" +
//                "           {" +
////                "               b = i;" +
//                "               for (;;){" +
//                "                   b = i;" +
////                "                   second = 3;" +
//                "               }" +
//                "           }" +
//                "           while (false){}" +
//                "           second = 1;" +
//                "       }" +
//                "       b = 5;" +
//                "   }" +
//                "}";

//        String example = "" +
//                "void main()" +
//                "{" +
//                "   int a;" +
//                "   int b;" +
//                "   float x;" +
//                "   float y;" +
//                "   for (x = 1;; )" +
//                "   { " +
////                        param1
////                        param2
//                "       x = a;" +
//                "       b = 1;" +
//                "       y = b;" +
////                        param3
//                "       " +
//                "   }" +
//                "}";

        String example = "" +
                "void main()" +
                "{ " +
                "   int x = 0;" +
                "   for (;;x=0)" +
                "       x = 2;" +
                "   }" +
                "}";

//        String example = "" +
//                "void main()" +
//                "{" +
//                "   int x;" +
//                "   if (false)" +
//                "   {" +
//                "       for (;;)" +
//                "           x = 2;" +
//                "   }" +
//                "}";

        InputManager input = new InputManager();
        input.readText(example);

        try
        {
            Parser parser = new Parser();
            String result = parser.parseAndOptimize(input);

            System.out.println(result);
        }
        catch (Exception e)
        {
            System.out.println("Got exception from parser");
            e.printStackTrace();
        }
    }
}
