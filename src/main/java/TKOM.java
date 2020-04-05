import jdk.internal.util.xml.impl.Input;

public class TKOM
{
    public static void main(String[] args)
    {
        String example = "void main ()\n" +
                "{\n" +
                "int var = 5004; //this is the first line\n" +
                "     //this 2nd line\n" +
                "     float a = (0.5+var) +-15;/*middle comment*/\n" +
                "     for (int i = 0; i < 12; /*and*/ i = i+1) t\n" +
                "   hdaksajdhkashjd$%$#WR#Wdfe4 343242.432432.4323..\n" +
                "     if (var >= a && i != a) {} else {bool cond = false;}/*hdfjksdjd\n" +
                "     fndsnfds*/end\n" +
                "     float close = 0.50.23  4657bas\n" +
                "}\n";

//        String example = "15.12 0.12 .12 12. abc";

//        String example = "abc//def";

        InputManager input = new InputManager();
        input.readText(example);

        Scanner scanner = new Scanner();
        scanner.parseTokens(input, null);
    }
}
