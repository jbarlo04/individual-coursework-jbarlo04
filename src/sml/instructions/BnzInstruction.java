package sml.instructions;


import sml.Instruction;
import sml.Machine;
import sml.Registers;

/**
 * This class represents the Add instruction from the language.
 *
 * @author Juris Barlots
 */
public class BnzInstruction extends Instruction {

    private int s1;
    private String labelToReach;
// L1 bnz s1 L2
    public BnzInstruction(String label, int s1, String labelToReach){
        super(label, "mul");
        this.s1 = s1;
        this.labelToReach = labelToReach;
    }

    @Override
    public String toString() {
        return getLabel() + ": " + getOpcode() + " " + s1 + " " + labelToReach;
    }

    @Override
    public void execute(Machine m) {
        Registers registers = m.getRegisters();
        if(registers.getRegister(s1) != 0){
            m.setPc(m.getLabels().indexOf(labelToReach));
        }
    }
}

