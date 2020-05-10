import jdk.internal.util.xml.impl.Input;

import java.util.EnumSet;

public class TKOM
{
    enum detailed
    {
        first,
        second
    }

    public static void main(String[] args)
    {
//        String example = "void  main    ()\n" +
//                "{\n" +
//                "int var = 5004; //this is the first line\n" +
//                "     //this 2nd line\n" +
//                "     float a = (0.5+var) +15;/*middle comment*/\n" +
//                "     for (int i = 0; i < 12; /*and*/ i = i+1) t = 3;\n" +
//               // "   hdaksajdhkashjd$%$#WR#Wdfe4 343242.432432.4323..\n" +
//                "     if (var >= a && i != a) {} else {bool cond = false;}/*hdfjksdjd\n" +
//                "     fndsnfds*/\n" +
//                "     float close = 0.50;//.23  4657bas\n" +
//                "     int x = (a + b + c + d) * ((2/3 + 2) * 2 + 2 ); \n" +
//                "}\n" ;

//        String example = "15.12 0.12 .12 12. abc";

//        String example = "void main () {for (;;) {}}";

        String example = "" +
                "bool t = true; \n" +
                "float f = 5.0; \n" +
                "void main () \n" +
                "{ \n" +
                "   for (int i = 0; !(i < 20); i = i + 1 ) \n" +
                "   { \n" +
                "       int b = 3 * i + (12 / i); b = 12;  \n" +
                "   }  \n" +
                "   while (x <= 1 || x > 10 || x == 1 && !(x!=5))  \n" +
                "   { \n" +
                "      x = x + 20; \n" +
                "      c = -(a+20 + -x); \n" +
                "       if (true) { continue; } else break;  \n" +
                "   } \n" +
                "} \n";

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

//        Scanner scanner = new Scanner();
//        scanner.parseTokens(input, null);
//
//        EnumSet<detailed> test = EnumSet.of(detailed.second, detailed.first);
//        if (test.contains(detailed.first))
//        {
//            System.out.println("found first");
//        }
//        if (test.contains(detailed.second))
//        {
//            System.out.println("found second");
//        }
    }
}
