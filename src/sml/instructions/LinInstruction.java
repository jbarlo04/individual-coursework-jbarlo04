package sml.instructions;

import sml.Instruction;
import sml.Machine;
import sml.Registers;

/**
 * This class represents the Add instruction from the language.
 *
 * @author Juris Barlots
 */
public class LinInstruction extends Instruction {

    private int register;
    private int x;

    public LinInstruction(String label, int register, int x){
        super(label, "lin");
        this.register = register;
        this.x = x;
    }

    @Override
    public void execute(Machine m) {
        Registers regs = m.getRegisters();
        regs.setRegister(register, x);
    }
}
