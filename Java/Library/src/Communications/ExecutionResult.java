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
package Communications;

/**
 * The interface of the result from the execution of a command.
 *
 * @see Commands.Command
 * @author Manuel Meireles ( mjcdm@isep.ipp.pt )
 */
public interface ExecutionResult {

    /**
     * A textual representation of the result.
     *
     * @return the result as text.
     */
    String description();

    /**
     * An indicator to assess if the result was successful or not.
     *
     * @return true if the execution achieved a successful result or false
     * otherwise.
     */
    boolean wasSuccessful();

}
