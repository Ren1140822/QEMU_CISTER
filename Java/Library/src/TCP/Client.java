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
package TCP;

import Commands.Command;
import Communications.ExecutionResult;

/**
 * The interface of a client in a client/server configuration. It is used to
 * communicate with an instance to which it belongs.
 *
 * @see Instance
 * @author Manuel Meireles ( mjcdm@isep.ipp.pt )
 */
public interface Client {

    /**
     * It sends a command to the instance.
     *
     * @param command the command to be sent. It might have to be parsed within.
     * @return a reply of the instance to the command.
     */
    ExecutionResult send(Command command);

}
