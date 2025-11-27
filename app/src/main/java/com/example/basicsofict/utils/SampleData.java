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
                "Learn computer lab safety rules",
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

        // Chapter 3: Identifying parts and types of computers
        Chapter chapter3 = new Chapter(
                3,
                "Identifying parts and types of computers",
                "Learn about different computers and their parts",
                "To introduce pupils with the different parts and types of computers",
                Arrays.asList(
                        "Identify the desktop computer, tablet, laptop, smartphone and the smartwatch",
                        "Recognise the different parts of the computer"
                )
        );

        List<Lesson> chapter3Lessons = new ArrayList<>();
        chapter3Lessons.add(new Lesson("Types of computers", "Desktop, Laptop, Tablet, Smartphone...", "theory"));
        chapter3Lessons.add(new Lesson("Computer parts", "Monitor, Mouse, Keyboard, System Unit...", "theory"));
        chapter3Lessons.add(new Lesson("Coloring activity", "Color the different parts of computer", "activity"));
        chapter3.setLessons(chapter3Lessons);

        // Add Chapters 4-7 following the same pattern...
        chapters.add(chapter1);
        chapters.add(chapter2);
        chapters.add(chapter3);
        // Add remaining chapters here...

        return chapters;
    }
}