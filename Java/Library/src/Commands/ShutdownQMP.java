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
 * A command to request the QEMU to shutdown.
 *
 * @see Command
 * @author Manuel Meireles ( mjcdm@isep.ipp.pt )
 */
public class ShutdownQMP implements Command {

    /**
     * The instruction of the command.
     */
    private static final String INSTRUCTION = "quit";

    /**
     * A private empty constructor. The instruction is predefined.
     */
    private ShutdownQMP(){}

    /**
     * It builds a command to request QEMU to shutdown.
     *
     * @return the built command.
     */
    public static Command create(){
        return new ShutdownQMP();
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
     * @return the {@link ShutdownQMP#instruction()} result.
     */
    @Override
    public String toString(){
        return instruction();
    }

}
