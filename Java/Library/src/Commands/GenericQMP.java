/*
 *                      QEMU INSTANCES MANAGER LIBRARY
 *                                Java Version
 * 
 * PRODUCT OWNER: Research Centre in Real-Time & Embedded Computing Systems
 * PROJECT MANAGER: Luís Pinho and Cláudio Maia {lmn,clrrm}@isep.ipp.pt
 * DEVELOPER: Manuel Meireles ( mjcdm@isep.ipp.pt )
 * DATE: 24/11/2017
 * SCOPE: CISTER Summer Internship 2017
 * VERSION: 1.0
 */
package Commands;

/**
 * A command with a personalized instruction.
 *
 * @see Command
 * @author Manuel Meireles ( mjcdm@isep.ipp.pt )
 */
public class GenericQMP implements Command {

    /**
     * The instruction of the command.
     */
    private final String instruction;

    /**
     * A private empty constructor. The instruction is as given. Despite no
     * validation is made, the instruction should not be null.
     */
    private GenericQMP(String theInstruction) {
        instruction = theInstruction;
    }

    /**
     * It builds a command with a given instruction. Despite no validation is
     * made, the instruction should not be null.
     *
     * @param instruction the instruction from the command to be executed.
     * @return the built command.
     */
    public static Command create(String instruction) {
        return new GenericQMP(instruction);
    }

    /**
     * A textual representation of the instruction.
     *
     * @return the instruction as text.
     */
    @Override
    public String instruction() {
        return instruction;
    }

    /**
     * The textual representation of the command.
     *
     * @return the {@link GenericQMP#instruction()} result.
     */
    @Override
    public String toString() {
        return instruction();
    }

}
