package sml.instructions;

import sml.Instruction;
import sml.Machine;
import sml.Registers;

/**
 * This class represents the Add instruction from the language.
 *
 * @author Juris Barlots
 */
public class MulInstruction extends Instruction {

    private int register;
    private int s1;
    private int s2;

    public MulInstruction(String label, int register, int s1, int s2){
        super(label, "mul");
        this.register = register;
        this.s1 = s1;
        this.s2 = s2;
    }

    @Override
    public String toString() {
        return getLabel() + ": " + getOpcode() + " " + register + " " + s1 + " " + s2;
    }

    @Override
    public void execute(Machine m) {
        Registers regs = m.getRegisters();
        regs.setRegister(register, regs.getRegister(s1) * regs.getRegister(s2));
    }
}
