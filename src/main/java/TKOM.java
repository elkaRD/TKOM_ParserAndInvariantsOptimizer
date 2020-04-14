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
        String example = "void  main    ()\n" +
                "{\n" +
                "int var = 5004; //this is the first line\n" +
                "     //this 2nd line\n" +
                "     float a = (0.5+var) +15;/*middle comment*/\n" +
                "     for (int i = 0; i < 12; /*and*/ i = i+1) t = 3;\n" +
               // "   hdaksajdhkashjd$%$#WR#Wdfe4 343242.432432.4323..\n" +
                "     if (var >= a /*&& i != a*/) {} else {bool cond = false;}/*hdfjksdjd\n" +
                "     fndsnfds*/\n" +
                "     float close = 0.50;//.23  4657bas\n" +
                "}\n" ;

//        String example = "15.12 0.12 .12 12. abc";

//        String example = "abc//def";

//        String example = "void main () {if (var >= a && i != a) {}}";

        InputManager input = new InputManager();
        input.readText(example);

        IParser parser = new Parser();
        parser.parse(input);

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
