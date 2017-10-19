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
 * The interface of a command that can be executed.
 * 
 * @author Manuel Meireles ( mjcdm@isep.ipp.pt )
 */
public interface Command {

    /**
     * It provides the instruction of the command.
     * 
     * @return the instruction of the command.
     */
    String instruction();

}
