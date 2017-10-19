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
 * A command to request the QEMU to resume its operations.
 *
 * @see Command
 * @author Manuel Meireles ( mjcdm@isep.ipp.pt )
 */
public class ContinueQMP implements Command {

    /**
     * The instruction of the command.
     */
    private static final String INSTRUCTION = "cont";

    /**
     * A private empty constructor. The instruction is predefined.
     */
    private ContinueQMP() {
    }

    /**
     * It builds a command to request QEMU to resume its operations.
     *
     * @return the built command.
     */
    public static Command create() {
        return new ContinueQMP();
    }

    /**
     * A textual representation of the instruction.
     *
     * @return the instruction as text.
     */
    @Override
    public String instruction() {
        return INSTRUCTION;
    }

    /**
     * The textual representation of the command.
     *
     * @return the {@link ContinueQMP#instruction()} result.
     */
    @Override
    public String toString() {
        return instruction();
    }

}
