///
/// EN: Project for the ‘Compilation Techniques’ course
///     Warsaw University of Technology
///     Parser and Optimizer of Own Programming Language
///
/// PL: Projekt TKOM (Techniki kompilacji)
///     PW WEiTI 20L
///     Parser i optymalizator wlasnego jezyka programowania
///
/// Copyright (C) Robert Dudzinski 2020
///
/// File: InputManagerTest.java

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InputManagerTest
{
    @Test
    public void getLineTest()
    {
        String inputText = "first line\n" +
                "second line\n" +
                "third line  \n" +
                "the last line\n";

        InputManager inputManager = new InputManager();
        inputManager.readText(inputText);

        assertTrue(inputManager.getLine(0).equals("first line"));
        assertTrue(inputManager.getLine(1).equals("second line"));
        assertTrue(inputManager.getLine(2).equals("third line  "));
        assertTrue(inputManager.getLine(3).equals("the last line"));
    }

    @Test
    public void getCharTest()
    {
        InputManager inputManager = new InputManager();
        inputManager.readText("abcd");

        assertTrue(inputManager.peekNext() == 'a');
        assertTrue(inputManager.peekNext(2) == 'b');
        assertTrue(inputManager.getNext() == 'a');
        assertTrue(inputManager.isAvailableChar());
        assertTrue(inputManager.isAvailableChar(1));

        assertTrue(inputManager.peekNext() == 'b');
        assertTrue(inputManager.peekNext(2) == 'c');
        assertTrue(inputManager.getNext() == 'b');
        assertTrue(inputManager.isAvailableChar());
        assertTrue(inputManager.isAvailableChar(1));

        assertTrue(inputManager.peekNext() == 'c');
        assertTrue(inputManager.peekNext(2) == 'd');
        assertTrue(inputManager.getNext() == 'c');
        assertTrue(inputManager.isAvailableChar());
        assertFalse(inputManager.isAvailableChar(2));

        assertTrue(inputManager.peekNext() == 'd');
        assertTrue(inputManager.getNext() == 'd');
        assertFalse(inputManager.isAvailableChar());
        assertFalse(inputManager.isAvailableChar(2));
    }

    @Test
    public void getCurrentPositionTest()
    {
        String inputText =  "abcde\n" +
                            "0123456\n" +
                            "the last line\n";

        InputManager inputManager = new InputManager();
        inputManager.readText(inputText);

        CharPos beginPos = inputManager.getCurrentPosition();
        assertTrue(beginPos.cursorPos == 0);
        assertTrue(beginPos.posInLine == 0);
        assertTrue(beginPos.lineIndex == 0);

        int cursorPos = 1;

        for (int i = 0; i < 5; i++, cursorPos++)
        {
            inputManager.getNext();
            CharPos curPos = inputManager.getCurrentPosition();
            assertTrue(curPos.cursorPos == cursorPos);
            assertTrue(curPos.posInLine == cursorPos);
            assertTrue(curPos.lineIndex == 0);
        }

        inputManager.getNext(); //'\n'
        cursorPos++;

        for (int i = 0; i < 7; i++, cursorPos++)
        {
            inputManager.getNext();
            CharPos curPos = inputManager.getCurrentPosition();
            assertTrue(curPos.cursorPos == cursorPos);
            assertTrue(curPos.posInLine == cursorPos - 6);
            assertTrue(curPos.lineIndex == 1);
        }

        inputManager.getNext(); //'\n'
        cursorPos++;

        for (int i = 0; i < 13; i++, cursorPos++)
        {
            inputManager.getNext();
            CharPos curPos = inputManager.getCurrentPosition();
            assertTrue(curPos.cursorPos == cursorPos);
            assertTrue(curPos.posInLine == cursorPos - 6 - 8);
            assertTrue(curPos.lineIndex == 2);
        }
    }
}
