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
package Instances;

import Commands.Command;
import Communications.ExecutionResult;

/**
 * The interface of an instance, i.e. a virtual machine that is identified by
 * an id, that can be started and afterwards can execute commands.
 *
 * @author Manuel Meireles ( mjcdm@isep.ipp.pt )
 */
public interface Instance extends Comparable<Instance> {

    /**
     * A getter of the unique and final identification.
     *
     * @return the instance ID.
     */
    InstanceID id();

    /**
     * It creates and starts the process of the instance.
     *
     * @return a success execution result or a fail if the process was not
     * started.
     */
    ExecutionResult start();

    /**
     * It executes the command.
     *
     * @param command the command to be executed.
     * @return the reply of the execution or a fail if the instance can not
     * process the command.
     */
    ExecutionResult execute(Command command);

}
