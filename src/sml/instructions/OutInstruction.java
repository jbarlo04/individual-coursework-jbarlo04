package sml.instructions;

import sml.Instruction;
import sml.Machine;
import sml.Registers;

/**
 * This class represents the Add instruction from the language.
 *
 * @author Juris Barlots
 */
public class OutInstruction extends Instruction {

    private int s1;

    public OutInstruction(String label, int s1){
        super(label, "out");
        this.s1 = s1;
    }

    @Override
    public void execute(Machine m) {
        Registers regs = m.getRegisters();
        System.out.println(regs.getRegister(s1));
    }
}
