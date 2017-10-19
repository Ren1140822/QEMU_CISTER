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

import Configurations.Settings;

/**
 * A command to build a new QEMU instance.
 *
 * @see Command
 * @author Manuel Meireles ( mjcdm@isep.ipp.pt )
 */
public class BuildQemuInstance implements Command {

    /**
     * The instructions to start the QEMU instance. When the process is to be
     * started, the QEMU path from the configurations will be appended before
     * string and the «-qmp» parameter will be appended after. It should not,
     * therefore, contain this elements.
     */
    private final String instruction;

    /**
     * The IP address in which the QEMU will be operating.
     */
    private final String ip;

    /**
     * The port number on which the QEMU will be listening.
     */
    private final int port;

    /**
     * The default IP address to use when none is indicated (localhost).
     */
    private static final String DEFAULT_IP = "127.0.0.1";

    /**
     * The number of ports the that have been assigned. Since each QEMU instance
     * must have its own port, in order to ensure that no mistakes are made, the
     * port numbers may be automatically assigned by adding this value to the
     * starting port number from the configurations.
     */
    private static int PORTS_ASSIGNED = 0;

    /**
     * A private constructor that initializes the instruction, the IP address
     * and the port number. Despite no validation is made, none of the
     * parameters should be null.
     *
     * @param theInstructions the instructions to start the QEMU instance (see {@link BuildQemuInstance#instruction}).
     * @param theIP the IP address on which the QEMU will be operating.
     * @param thePort the port number on which the QEMU will be listening.
     */
    private BuildQemuInstance(String theInstructions, String theIP, int thePort) {
        instruction = theInstructions;
        ip = theIP;
        port = thePort;
    }

    /**
     * It creates a new BuildQemuInstance command with the default IP address and port number.
     * 
     * @param options the instructions to start the QEMU instance (see {@link BuildQemuInstance#instruction}).
     * @return the built command.
     */
    public static BuildQemuInstance command(String options) {
        int portNumber = Settings.getStartingPortNumber() + PORTS_ASSIGNED;
        PORTS_ASSIGNED++;
        return new BuildQemuInstance(options, DEFAULT_IP, portNumber);
    }

    /**
     * It creates a new BuildQemuInstance command with a given IP address and port number. Despite no validation is made, none of the
     * parameters should be null.
     * 
     * @param options the instructions to start the QEMU instance (see {@link BuildQemuInstance#instruction}).
     * @param ip the IP address on which the QEMU will be operating.
     * @param port the port number on which the QEMU will be listening.
     * @return the built command.
     */
    public static BuildQemuInstance command(String options, String ip, int port) {
        return new BuildQemuInstance(options, ip, port);
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
     * A getter of the IP address.
     *
     * @return the {@link BuildQemuInstance#ip}.
     */
    public String ip() {
        return ip;
    }

    /**
     * A getter of the port number.
     *
     * @return the {@link BuildQemuInstance#port}.
     */
    public int port() {
        return port;
    }

    /**
     * The textual representation of the command.
     *
     * @return the {@link BuildQemuInstance#instruction()} result.
     */
    @Override
    public String toString() {
        return instruction();
    }

}
