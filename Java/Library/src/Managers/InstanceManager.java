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
package Managers;

import Commands.Command;
import Communications.ExecutionResult;
import Instances.Instance;
import Instances.InstanceID;
import java.util.List;
import java.util.Map;

/**
 * The interface of an instances manager, i.e. an object whose responsibility is
 * to create, interact with and destroy instances.
 *
 * @author Manuel Meireles ( mjcdm@isep.ipp.pt )
 */
public interface InstanceManager {

    /**
     * It builds an instance and then executes it right away.
     *
     * @param options the options to be used on the call to execute the
     * instance.
     * @return a success execution result or a fail if the build or execution
     * fail.
     */
    ExecutionResult startInstance(Command options);

    /**
     * It only builds an instance.
     *
     * @param options the options to be used on the call to the
     * {@link Runtime#exec(java.lang.String)} method to execute the instance.
     * @return a success execution result or a fail if the parameter is null.
     */
    ExecutionResult buildInstance(Command options);

    /**
     * It starts an existing instance.
     *
     * @param id the identification of the existing instance to start.
     * @return the execution result of the {@link Instance#start() } or a fail
     * if the parameter is null or the identity is not found.
     */
    ExecutionResult executeInstance(InstanceID id);

    /**
     * It sends the continue command to an existing instance.
     *
     * @param id the identification of the existing instance to start.
     * @return the execution result of the
     * {@link Instance#execute(Commands.Command)} or a fail if the parameter is
     * null or the identity is not found.
     */
    ExecutionResult continueInstance(InstanceID id);

    /**
     * It sends the stop command to an existing instance.
     *
     * @param id the identification of the existing instance to start.
     * @return the execution result of the
     * {@link Instance#execute(Commands.Command)} or a fail if the parameter is
     * null or the identity is not found.
     */
    ExecutionResult suspendInstance(InstanceID id);

    /**
     * It sends the shutdown command to an existing instance.
     *
     * @param id the identification of the existing instance to start.
     * @return the execution result of the
     * {@link Instance#execute(Commands.Command)} or a fail if the parameter is
     * null or the identity is not found.
     */
    ExecutionResult shutdownInstance(InstanceID id);

    /**
     * It lists the existing instances in the manager.
     *
     * @return a list with all the existing instances of the manager.
     */
    ExecutionResult listInstances();

    /**
     * It executes a list of commands to each of the instances.
     *
     * @param commands a map with all the commands,where the keys match the
     * identification of the instances and the value the list of commands to be
     * executed on it.
     * @return a map with the execution results, where the keys match the
     * identification of the instances and the value the list of results
     * produced.
     */
    ExecutionResult executeCommands(Map<InstanceID, List<Command>> commands);

    /**
     * It shuts down all the instances within the manager and then it shuts down
     * the manager itself.
     *
     * @return a list with the execution result of each instance {@link InstanceManager#shutdownInstance(Instances.InstanceID)
     * } request.
     */
    ExecutionResult shutdown();

    /**
     * It loads the configurations from the file at the specified path and name.
     *
     * @param config the path followed by the file name.
     * @return a success execution result or a fail if the file could not be
     * loaded.
     */
    ExecutionResult loadConfigurationFile(String config);

}
