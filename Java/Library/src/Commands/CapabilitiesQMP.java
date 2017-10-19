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

import Instances.QemuInstance;

/**
 * A command to request capabilities to QEMU. This command should be requested
 * right after the {@link QemuInstance} is started, in order to allow it to
 * handle all the other commands.
 *
 * @see Command
 * @author Manuel Meireles ( mjcdm@isep.ipp.pt )
 */
public class CapabilitiesQMP implements Command {

    /**
     * The instruction of the command.
     */
    private static final String INSTRUCTION = "qmp_capabilities";

    /**
     * A private empty constructor. The instruction is predefined.
     */
    private CapabilitiesQMP() {
    }

    /**
     * It builds a command to request capabilities to the QEMU.
     *
     * @return the built command.
     */
    public static Command create() {
        return new CapabilitiesQMP();
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
     * @return the {@link CapabilitiesQMP#instruction()} result.
     */
    @Override
    public String toString() {
        return instruction();
    }

}
