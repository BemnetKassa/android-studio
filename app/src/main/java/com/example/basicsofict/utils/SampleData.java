package com.example.basicsofict.utils;

import com.example.basicsofict.models.Chapter;
import com.example.basicsofict.models.Lesson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SampleData {

    public static List<Chapter> getSampleChapters() {
        List<Chapter> chapters = new ArrayList<>();

        // Chapter 1: Working safely with computers
        Chapter chapter1 = new Chapter(
                1,
                "Working safely with computers",
                "Learn computer lab safety rules and proper computer usage",
                "To work in a safe and healthy environment",
                Arrays.asList(
                        "Recognise the health risks existing in the computer lab",
                        "Show understanding of the proper way to sit in front of a computer",
                        "Follow computer lab rules and regulations"
                )
        );

        List<Lesson> chapter1Lessons = new ArrayList<>();
        chapter1Lessons.add(new Lesson("Rules and Regulations",
                "• Make sure that your hands are clean before using the computer.\n" +
                "• Enter and leave the computer lab quietly.\n" +
                "• Keep the computer lab clean and use the bin.\n" +
                "• Do not eat or drink in the computer lab.\n" +
                "• Do not touch the screen.\n" +
                "• Do not touch the electrical sockets and plugs.\n" +
                "• Do not run in the computer lab.\n" +
                "• Do not insert any objects in the drives, USB ports or jack socket.\n" +
                "• Push your chair under the table before leaving the computer lab.\n" +
                "• Tell your teacher if you have a problem with the computer.",
                "theory"));

        chapter1Lessons.add(new Lesson("Risks in Computer Lab",
                "Common risks in the computer lab:\n" +
                "• Trailing wires\n" +
                "• Damaged electrical sockets and wires\n" +
                "• Overloaded electrical sockets\n" +
                "• Working with glare or reflections\n" +
                "• Wrong sitting posture\n" +
                "• Food or liquids near computer\n" +
                "• Touching computer parts with wet hands\n" +
                "• Hitting the keys too hard",
                "theory"));

        chapter1Lessons.add(new Lesson("Good Sitting Posture",
                "Proper way to sit in front of computer:\n" +
                "• Your eyes should be level with the top of the screen.\n" +
                "• Keep your head and back straight and shoulders relaxed.\n" +
                "• Your wrist must be in a straight line with your forearm.\n" +
                "• Keep your elbows close to your body.\n" +
                "• Your feet should rest flat on the floor.",
                "practice"));
        chapter1.setLessons(chapter1Lessons);
        chapters.add(chapter1);

        // Chapter 2: Working with Tablets
        Chapter chapter2 = new Chapter(
                2,
                "Working with Computers: Tablets",
                "Learn how to handle and use tablets properly",
                "To introduce pupils to tablets",
                Arrays.asList(
                        "Handle a tablet correctly",
                        "Identify the parts of a tablet",
                        "Switch on and switch off a tablet"
                )
        );

        List<Lesson> chapter2Lessons = new ArrayList<>();
        chapter2Lessons.add(new Lesson("Handling Tablets",
                "How to handle tablets safely:\n" +
                "• Keep the tablet in the tablet casing.\n" +
                "• Tap softly on the screen.\n" +
                "• Keep your keyboard clean.\n" +
                "• Use the pen stylus with care.\n" +
                "• Avoid eating and drinking when using the tablet.",
                "theory"));

        chapter2Lessons.add(new Lesson("Parts of a Tablet",
                "Main parts of a tablet:\n" +
                "• Camera\n" +
                "• Tablet casing\n" +
                "• Power button\n" +
                "• Volume buttons\n" +
                "• Screen\n" +
                "• Keyboard\n" +
                "• Touch pen (stylus)",
                "theory"));

        chapter2Lessons.add(new Lesson("Switching On and Off",
                "How to switch on a tablet:\n" +
                "1. Press and hold the power button for 3 seconds.\n" +
                "2. Wait for the screen to show the startup logo.\n" +
                "3. The tablet will be ready to use.\n\n" +
                "How to switch off a tablet:\n" +
                "1. Press and hold the power button for 3 seconds.\n" +
                "2. Tap on 'Power off'.\n" +
                "3. Tap on 'OK' to confirm.",
                "practice"));
        chapter2.setLessons(chapter2Lessons);
        chapters.add(chapter2);

        // Chapter 3: Identifying parts and types of computers
        Chapter chapter3 = new Chapter(
                3,
                "Identifying parts and types of computers",
                "Learn about the different parts and types of computers",
                "To introduce pupils to the different parts and types of computers.",
                Arrays.asList(
                        "Identify the desktop computer, tablet, laptop, smartphone and the smartwatch.",
                        "Recognise the different parts of the computer."
                )
        );
        List<Lesson> chapter3Lessons = new ArrayList<>();
        chapter3Lessons.add(new Lesson("Types of Computers",
                "Computers come in different shapes and sizes:\n" +
                "• Desktop Computer\n" +
                "• Laptop\n" +
                "• Tablet\n" +
                "• Smartphone\n" +
                "• Smartwatch", "theory"));
        chapter3Lessons.add(new Lesson("Parts of a Desktop Computer",
                "The main parts of a desktop computer are:\n" +
                "• System unit\n" +
                "• Monitor (Screen)\n" +
                "• Keyboard\n" +
                "• Mouse\n" +
                "• Speakers\n" +
                "• Printer\n" +
                "• Headset", "theory"));
        chapter3.setLessons(chapter3Lessons);
        chapters.add(chapter3);

        // Chapter 4: Developing Mouse Skills
        Chapter chapter4 = new Chapter(
                4,
                "Developing Mouse Skills",
                "Learn how to use a computer mouse",
                "To introduce pupils to the use of the mouse.",
                Arrays.asList(
                        "Hold the mouse properly.",
                        "Show an understanding of the various functions of the mouse.",
                        "Manipulate the mouse."
                )
        );
        List<Lesson> chapter4Lessons = new ArrayList<>();
        chapter4Lessons.add(new Lesson("Holding and Using the Mouse",
                "• Place your thumb on the left side of the mouse.\n" +
                "• Place your ring finger and little finger on the right side.\n" +
                "• Place your index finger on the left button.\n" +
                "• Place your middle finger on the right button.\n" +
                "• Always put the mouse on a flat surface or a mouse pad.", "theory"));
        chapter4Lessons.add(new Lesson("Mouse Functions",
                "• Point: Move the pointer on the screen.\n" +
                "• Click: Press the left mouse button once.\n" +
                "• Double Click: Press the left mouse button two times quickly.\n" +
                "• Right Click: Press the right mouse button once.\n" +
                "• Drag and Drop: Hold the left button to move an item and release it.", "practice"));
        chapter4.setLessons(chapter4Lessons);
        chapters.add(chapter4);

        // Chapter 5: Developing Keyboarding Skills
        Chapter chapter5 = new Chapter(
                5,
                "Developing Keyboarding Skills",
                "Learn to use the keyboard and type",
                "To introduce pupils to the computer keyboard and the Word Processing Program.",
                Arrays.asList(
                        "Identify and open a Word Processing Program.",
                        "Recognise different keys of the keyboard.",
                        "Type letters, numbers, simple words and sentences."
                )
        );
        List<Lesson> chapter5Lessons = new ArrayList<>();
        chapter5Lessons.add(new Lesson("The Keyboard Keys",
                "• Alphabet Keys: For typing letters.\n" +
                "• Number Keys: For typing numbers.\n" +
                "• Caps Lock Key: To switch between CAPITAL and small letters.\n" +
                "• Spacebar Key: To add a space between words.\n" +
                "• Enter Key: To move to the next line.\n" +
                "• Backspace Key: To erase characters to the left of the cursor.", "theory"));
        chapter5Lessons.add(new Lesson("Typing Practice",
                "Practice typing letters, numbers, and simple sentences. Use the different keys you have learned about to edit your text.", "practice"));
        chapter5.setLessons(chapter5Lessons);
        chapters.add(chapter5);

        // Chapter 6: Browsing the Internet Safely
        Chapter chapter6 = new Chapter(
                6,
                "Browsing the Internet Safely",
                "Learn the basics of web browsing",
                "To introduce pupils to a web page and its contents.",
                Arrays.asList(
                        "Understand that web pages contain text, images and videos.",
                        "Navigate around a given web page using hyperlinks."
                )
        );
        List<Lesson> chapter6Lessons = new ArrayList<>();
        chapter6Lessons.add(new Lesson("Web Pages and Hyperlinks",
                "A web page can contain text, images, and videos. A hyperlink allows you to navigate from one page to another. The mouse pointer often changes to a hand symbol when you move it over a hyperlink.", "theory"));
        chapter6.setLessons(chapter6Lessons);
        chapters.add(chapter6);

        // Chapter 7: Multimedia and Drawing
        Chapter chapter7 = new Chapter(
                7,
                "Multimedia and Drawing",
                "Learn to watch videos and use a drawing program",
                "To introduce pupils to a media playing interface and a graphic program.",
                Arrays.asList(
                        "Watch videos on the computer.",
                        "Interact with a media playing interface (play, pause, stop).",
                        "Use basic tools in a drawing program like Paint."
                )
        );
        List<Lesson> chapter7Lessons = new ArrayList<>();
        chapter7Lessons.add(new Lesson("Watching Videos",
                "You can watch videos on a computer. A media player has controls to:\n" +
                "• Play: Start the video.\n" +
                "• Pause: Temporarily stop the video.\n" +
                "• Stop: End the video playback.\n" +
                "• You can hear sound from speakers or headsets.", "theory"));
        chapter7Lessons.add(new Lesson("Drawing with Paint",
                "You can use a program like Paint to draw. It has many tools:\n" +
                "• Pencil tool: To draw free-form lines.\n" +
                "• Eraser tool: To erase parts of your drawing.\n" +
                "• Brush tool: To draw with different brush shapes and sizes.\n" +
                "• Text tool: To add text to your drawing.\n" +
                "• Line tool: To draw straight lines.\n" +
                "• Undo/Redo: To cancel or re-apply your last action.", "practice"));
        chapter7.setLessons(chapter7Lessons);
        chapters.add(chapter7);

        return chapters;
    }
}
