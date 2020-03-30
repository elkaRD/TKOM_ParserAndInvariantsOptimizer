import jdk.internal.util.xml.impl.Input;

public class TKOM
{
    public static void main(String[] args)
    {
        System.out.print("Hello World");

        String example = "void main ()\n" +
                "{\n" +
                "     example line 1 //this is the first line\n" +
                "     and this is 2nd line\n" +
                "     here we test/*middle comment*/something\n" +
                "     here spaces /*and*/ two\n" +
                "     beg/*hdfjksdjd\n" +
                "     fndsnfds*/end\n" +
                "     close\n" +
                "}\n";

//        String example = "abc//def";

        InputManager input = new InputManager();
        input.readText(example);

        Scanner scanner = new Scanner();
        scanner.parseTokens(input);
    }
}
