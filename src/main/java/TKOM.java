public class TKOM
{
    public static void main(String[] args)
    {
        String example = "" +
                "bool t = true; \n" +
                "float a = 5.0; \n" +
                "void main () \n" +
                "{ \n" +
                "   int z = 0; \n" +
//                "   for (int i = 0; !(i < 20); i = i + 1 ) \n" +
//                "   { \n" +
//                "       int b = 3 * i + (12 / i); b = 12;  \n" +
//                //"       int b = 3; \n" +
//                //"       a = 20; \n" +
//                "   }  \n" +
                "   int c; int x; \n" +
                "   while (x <= 1 || x > 10 || (x == 1 && !(x!=5)))  \n" +
                "   { \n" +
                "      x = x + 20; \n" +
                "      c = -(a+20 + -x); \n" +
                "       if (true) { continue; } else break; \n" +
                "   } \n" +
                "   while (true) \n" +
                "       t = 3; \n" +
                "} \n";

//        String example = "" +
//                "int a = 5; \n" +
//                "" +
//                "void main() \n" +
//                "{ \n" +
//                "   a = 5; \n" +
//                "} \n";

        InputManager input = new InputManager();
        input.readText(example);

        try
        {
            IParser parser = new Parser();
            parser.parse(input);
        }
        catch (Exception e)
        {
            System.out.println("Got exception from parser");
            e.printStackTrace();
        }
    }
}
