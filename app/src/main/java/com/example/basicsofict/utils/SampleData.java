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
                "• I make sure that my hands are clean before using the computer.\n" +
                        "• I enter and leave the computer lab quietly.\n" +
                        "• I keep the computer lab clean and use the bin.\n" +
                        "• I do not eat or drink in the computer lab.\n" +
                        "• I do not touch the screen.\n" +
                        "• I tell my teacher if I have problem with the computer.",
                "theory"));

        chapter1Lessons.add(new Lesson("Risks in Computer Lab",
                "Common risks in computer lab:\n" +
                        "• Trailing wires\n" +
                        "• Damaged electrical sockets and wires\n" +
                        "• Overloaded electrical sockets\n" +
                        "• Working with glare or reflections\n" +
                        "• Wrong sitting posture\n" +
                        "• Food or liquids near computer",
                "theory"));

        chapter1Lessons.add(new Lesson("Good Sitting Posture",
                "Proper way to sit in front of computer:\n" +
                        "• My eyes level with the top of the screen\n" +
                        "• I keep my head and back straight and shoulders relaxed\n" +
                        "• My wrist must be straight line with my forearm\n" +
                        "• I keep my elbows close to my body\n" +
                        "• My feet rest flat on the floor",
                "practice"));
        chapter1.setLessons(chapter1Lessons);

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
                        "• Keep the tablet in the tablet casing\n" +
                        "• Tap softly on the screen\n" +
                        "• Keep your keyboard clean\n" +
                        "• Use the pen stylus with care\n" +
                        "• Avoid eating and drinking when using the tablet",
                "theory"));

        chapter2Lessons.add(new Lesson("Parts of a Tablet",
                "Main parts of a tablet:\n" +
                        "• Camera\n" +
                        "• Tablet casing\n" +
                        "• Power button\n" +
                        "• Volume buttons\n" +
                        "• Screen\n" +
                        "• Keyboard\n" +
                        "• Touch pen",
                "theory"));

        chapter2Lessons.add(new Lesson("Switching On and Off",
                "How to switch on a tablet:\n" +
                        "1. Press and hold the power button for 3 seconds\n" +
                        "2. Wait for the screen to show the startup logo\n" +
                        "3. The tablet will be ready to use\n\n" +
                        "How to switch off a tablet:\n" +
                        "1. Press and hold the power button for 3 seconds\n" +
                        "2. Tap on 'Power off'\n" +
                        "3. Tap on 'OK' to confirm",
                "practice"));
        chapter2.setLessons(chapter2Lessons);

        // Add more chapters with real content...
        chapters.add(chapter1);
        chapters.add(chapter2);

        return chapters;
    }
}