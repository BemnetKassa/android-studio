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
                "Learn how to stay safe in the computer lab",
                "To work in a safe and healthy environment",
                Arrays.asList(
                        "Recognise the health risks existing in the computer lab",
                        "Show understanding of the proper way to sit in front of a computer"
                )
        );

        List<Lesson> chapter1Lessons = new ArrayList<>();
        chapter1Lessons.add(new Lesson("Rules and regulations", "I make sure that my hands are clean before using the computer...", "theory"));
        chapter1Lessons.add(new Lesson("Risks in computer lab", "Trailing wires, Damaged electrical sockets...", "theory"));
        chapter1Lessons.add(new Lesson("Good sitting posture", "My eyes level with the top of the screen...", "practice"));
        chapter1.setLessons(chapter1Lessons);

        // Chapter 2: Working with Tablets
        Chapter chapter2 = new Chapter(
                2,
                "Working with Computers: Tablets",
                "Learn how to use tablets properly",
                "To introduce pupils to tablets",
                Arrays.asList(
                        "Handle a tablet",
                        "Identify the parts of a tablet",
                        "Switch on and switch off a tablet"
                )
        );

        List<Lesson> chapter2Lessons = new ArrayList<>();
        chapter2Lessons.add(new Lesson("Handling tablets", "Keep the tablet in the tablet casing...", "theory"));
        chapter2Lessons.add(new Lesson("Parts of a tablet", "Camera, Power button, Volume buttons...", "theory"));
        chapter2Lessons.add(new Lesson("Switching on/off", "Press and hold the power button for 3 seconds...", "practice"));
        chapter2.setLessons(chapter2Lessons);

        // Add more chapters following the same pattern...
        chapters.add(chapter1);
        chapters.add(chapter2);

        return chapters;
    }

    public static List<String> getNavigationItems() {
        return Arrays.asList(
                "Home",
                "All Chapters",
                "My Progress",
                "Activities",
                "Settings",
                "Help"
        );
    }
}