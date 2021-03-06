package sml;

import sml.instructions.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.*;

/**
 * This class ....
 * <p>
 * The translator of a <b>S</b><b>M</b>al<b>L</b> program.
 *
 * @author ...
 */
public final class Translator {

    private static final String PATH = "";

    // word + line is the part of the current line that's not yet processed
    // word has no whitespace
    // If word and line are not empty, line begins with whitespace
    private final String fileName; // source file of SML code
    private String line = "";

    public Translator(String file) {
        fileName = PATH + file;
    }

    // translate the small program in the file into lab (the labels) and
    // prog (the program)
    // return "no errors were detected"

    public boolean readAndTranslate(Labels lab, List<Instruction> prog) {
        try (var sc = new Scanner(new File(fileName), "UTF-8")) {
            // Scanner attached to the file chosen by the user
            // The labels of the program being translated
            lab.reset();
            // The program to be created
            prog.clear();

            try {
                line = sc.nextLine();
            } catch (NoSuchElementException ioE) {
                return false;
            }

            // Each iteration processes line and reads the next input line into "line"
            while (line != null) {
                // Store the label in label
                var label = scan();

                if (label.length() > 0) {
                    var ins = getInstruction(label);
                    if (ins != null) {
                        lab.addLabel(label);
                        prog.add(ins);
                    }
                }
                try {
                    line = sc.nextLine();
                } catch (NoSuchElementException ioE) {
                    return false;
                }
            }
        } catch (IOException ioE) {
            System.err.println("File: IO error " + ioE);
            return false;
        }
        return true;
    }

    // The input line should consist of an SML instruction, with its label already removed.
    // Translate line into an instruction with label "label" and return the instruction
    public Instruction getInstruction(String label) {
        if (line.equals("")) {
            return null;
        }
        var opCode = scan();

        // 1. Find the class based on the opcode("lin") LinInstruction
        // 2. Get the constructor parameters
        // 3. We build the constructor parameters
        // 4. We instance the object and return it

        try{
            var className = "sml.instructions."+ capitalizeFirstLetter(opCode) + "Instruction";
            var classType = Class.forName(className);

            Constructor constructor = classType.getDeclaredConstructors()[0];
            Class[] pType = constructor.getParameterTypes();

            Object[] pValue = new Object[pType.length];
            pValue[0] = label;

            for(int i = 0; i<pType.length; i++) {
                if(pType[i].equals(int.class)){
                    pValue[i] = scanInt();
                } else if(pType[i].equals(String.class)){
                    pValue[i] = scan();
                } else {
                    throw new Error("Unknown instruction "+opCode);
                }
            }
            return (Instruction) constructor.newInstance(pValue);
        } catch(Exception e) {
            System.err.println("File unknown instruction");
        }
        return null;
    }

    private String capitalizeFirstLetter(String word ) {
        return word.substring(0,1).toUpperCase() + word.substring(1);
    }

    /*
     * Return the first word of line and remove it from line. If there is no word, return ""
     */
    private String scan() {
        line = line.trim();
        if (line.length() == 0) {
            return "";
        }

        int i = 0;
        while (i < line.length() && line.charAt(i) != ' ' && line.charAt(i) != '\t') {
            i = i + 1;
        }
        String word = line.substring(0, i);
        line = line.substring(i);
        return word;
    }

    // Return the first word of line as an integer. If there is any error, return the maximum int
    private int scanInt() {
        String word = scan();
        if (word.length() == 0) {
            return Integer.MAX_VALUE;
        }

        try {
            return Integer.parseInt(word);
        } catch (NumberFormatException e) {
            return Integer.MAX_VALUE;
        }
    }
}