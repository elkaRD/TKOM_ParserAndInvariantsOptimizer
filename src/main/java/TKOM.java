import jdk.internal.util.xml.impl.Input;

public class TKOM
{
    public static void main(String[] args)
    {
        System.out.print("Hello World");

        String example = "void main ()\n" +
                "{\n" +
                "     int var = 5004; //this is the first line\n" +
                "     //this 2nd line\n" +
                "     float a = (0.5+var) +-15;/*middle comment*/\n" +
                "     for (int i = 0; i < 12; /*and*/ i = i+1) t\n" +
                "     if (var >= a && i != a) {} else {bool cond = false;}/*hdfjksdjd\n" +
                "     fndsnfds*/end\n" +
                "     close\n" +
                "}\n";

//        String example = "i = 20; b == c";

//        String example = "abc//def";

        InputManager input = new InputManager();
        input.readText(example);

        Scanner scanner = new Scanner();
        scanner.parseTokens(input);
    }
}
