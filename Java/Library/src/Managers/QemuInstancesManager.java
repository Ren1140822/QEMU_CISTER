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
import Commands.ContinueQMP;
import Communications.Fail;
import Communications.Fail.Reason;
import Commands.ShutdownQMP;
import Commands.SuspendQMP;
import Communications.Creation;
import Communications.ExecutionResult;
import Communications.ListResult;
import Communications.MapResult;
import Configurations.Settings;
import Instances.Instance;
import Instances.InstanceID;
import Instances.QemuInstance;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * A QEMU instances manager.
 *
 * @see InstanceManager
 * @author Manuel Meireles ( mjcdm@isep.ipp.pt )
 */
public class QemuInstancesManager implements InstanceManager {

    /**
     * The manager itself. It implements the singleton pattern.
     */
    private static InstanceManager singleton;

    /**
     * A map with all the existing instances. The keys are the instances
     * identification and the values the instances themselves.
     */
    private final Map<InstanceID, Instance> qemuInstances;

    /**
     * A private constructor to ensure the singleton pattern. It initializes the
     * map empty.
     */
    private QemuInstancesManager() {
        qemuInstances = new HashMap<>();
    }

    /**
     * A getter of the manager singleton. It will create the manager if it does
     * not exist yet.
     *
     * @return the manager.
     */
    public static InstanceManager getInstance() {
        if (singleton == null) {
            singleton = new QemuInstancesManager();
        }
        return singleton;
    }

    /**
     * It builds an instance and then executes it right away.
     *
     * @param options the options to be used on the call to execute the
     * instance.
     * @return <ul><li>the execution result of {@link QemuInstancesManager#startInstance(Commands.Command)
     * };</li><li>the execution result of {@link QemuInstancesManager#buildInstance(Commands.Command)
     * } if not {@link Created};</li><li>a {@link Fail} due to
     * {@link Reason#NULL_ARGUMENT};</li></ul>
     */
    @Override
    public ExecutionResult startInstance(Command options) {
        Settings.logger().entering(getClass().getName(), "startInstance", options);
        ExecutionResult result;
        if (options == null) {
            result = Fail.because(Reason.NULL_ARGUMENT);
            Settings.logger().exiting(getClass().getName(), "startInstance", result);
            return result;
        }
        result = buildInstance(options);
        if (result instanceof Creation && result.wasSuccessful()) {
            result = executeInstance(((Creation) result).id());
            Settings.logger().exiting(getClass().getName(), "startInstance", result);
            return result;
        }
        Settings.logger().exiting(getClass().getName(), "startInstance", result);
        return result;
    }

    /**
     * It builds an instance.
     *
     * @param options the options to be used on the call to execute the
     * instance.
     * @return <ul><li>the execution result {@link Created};</li><li>a
     * {@link Fail} due to {@link Reason#NULL_ARGUMENT};</li></ul>
     */
    @Override
    public ExecutionResult buildInstance(Command options) {
        Settings.logger().entering(getClass().getName(), "buildInstance", options);
        if (options == null) {
            Settings.logger().exiting(getClass().getName(), "buildInstance", null);
            return Fail.because(Reason.NULL_ARGUMENT);
        }
        Instance instance = QemuInstance.create(options);
        InstanceID id = instance.id();
        qemuInstances.put(id, instance);
        Settings.logger().exiting(getClass().getName(), "buildInstance", id);
        return Creation.create(id, true);
    }

    /**
     * It builds an instance and then executes it right away.
     *
     * @param id the identification of the existing instance to start.
     * @return <ul><li>the execution result of {@link Instance#start()
     * };</li><li>a {@link Fail} due to {@link Reason#NULL_ARGUMENT};</li><li>a
     * {@link Fail} due to {@link Reason#UNKNOWN_ID};</li></ul>
     */
    @Override
    public ExecutionResult executeInstance(InstanceID id) {
        Settings.logger().entering(getClass().getName(), "executeInstance", id);
        ExecutionResult result;
        if (id == null) {
            result = Fail.because(Reason.NULL_ARGUMENT);
            Settings.logger().exiting(getClass().getName(), "executeInstance", result);
            return result;
        }
        Instance instance = qemuInstances.get(id);
        if (instance == null) {
            result = Fail.because(Reason.UNKNOWN_ID);
            Settings.logger().exiting(getClass().getName(), "executeInstance", result);
            return result;
        }
        result = instance.start();
        Settings.logger().exiting(getClass().getName(), "executeInstance", result);
        return result;
    }

    /**
     * It sends the continue command to an existing instance.
     *
     * @param id the identification of the existing instance to start.
     * @return <ul><li>the execution result of the
     * {@link Instance#execute(Commands.Command)} with a
     * {@link ContinueQMP};</li><li>a {@link Fail} due to
     * {@link Reason#NULL_ARGUMENT};</li><li>a {@link Fail} due to
     * {@link Reason#UNKNOWN_ID};</li></ul>
     */
    @Override
    public ExecutionResult continueInstance(InstanceID id) {
        Settings.logger().entering(getClass().getName(), "continueInstance", id);
        ExecutionResult result;
        if (id == null) {
            result = Fail.because(Reason.NULL_ARGUMENT);
            Settings.logger().exiting(getClass().getName(), "continueInstance", result);
            return result;
        }
        Instance instance = qemuInstances.get(id);
        if (instance == null) {
            result = Fail.because(Reason.UNKNOWN_ID);
            Settings.logger().exiting(getClass().getName(), "continueInstance", result);
            return result;
        }
        Command command = ContinueQMP.create();
        result = instance.execute(command);
        Settings.logger().exiting(getClass().getName(), "continueInstance", result);
        return result;
    }

    /**
     * It sends the stop command to an existing instance.
     *
     * @param id the identification of the existing instance to start.
     * @return <ul><li>the execution result of the
     * {@link Instance#execute(Commands.Command)} with a
     * {@link SuspendQMP};</li><li>a {@link Fail} due to
     * {@link Reason#NULL_ARGUMENT};</li><li>a {@link Fail} due to
     * {@link Reason#UNKNOWN_ID};</li></ul>
     */
    @Override
    public ExecutionResult suspendInstance(InstanceID id) {
        Settings.logger().entering(getClass().getName(), "suspendInstance", id);
        ExecutionResult result;
        if (id == null) {
            result = Fail.because(Reason.NULL_ARGUMENT);
            Settings.logger().exiting(getClass().getName(), "suspendInstance", result);
            return result;
        }
        Instance instance = qemuInstances.get(id);
        if (instance == null) {
            result = Fail.because(Reason.UNKNOWN_ID);
            Settings.logger().exiting(getClass().getName(), "suspendInstance", result);
            return result;
        }
        Command command = SuspendQMP.create();
        result = instance.execute(command);
        Settings.logger().exiting(getClass().getName(), "suspendInstance", result);
        return result;
    }

    /**
     * It sends the shutdown command to an existing instance. It also removes
     * the instance from the {@link QemuInstancesManager#qemuInstances}
     *
     * @param id the identification of the existing instance to start.
     * @return <ul><li>the execution result of the
     * {@link Instance#execute(Commands.Command)} with a
     * {@link ShutdownQMP};</li><li>a {@link Fail} due to
     * {@link Reason#NULL_ARGUMENT};</li><li>a {@link Fail} due to
     * {@link Reason#UNKNOWN_ID};</li></ul>
     */
    @Override
    public ExecutionResult shutdownInstance(InstanceID id) {
        Settings.logger().entering(getClass().getName(), "shutdownInstance", id);
        ExecutionResult result;
        if (id == null) {
            result = Fail.because(Reason.NULL_ARGUMENT);
            Settings.logger().exiting(getClass().getName(), "shutdownInstance", result);
            return result;
        }
        Instance instance = qemuInstances.get(id);
        if (instance == null) {
            result = Fail.because(Reason.UNKNOWN_ID);
            Settings.logger().exiting(getClass().getName(), "shutdownInstance", result);
            return result;
        }
        Command command = ShutdownQMP.create();
        result = instance.execute(command);
        if (result.wasSuccessful()) {
            qemuInstances.remove(id);
        }
        Settings.logger().exiting(getClass().getName(), "shutdownInstance", result);
        return result;
    }

    /**
     * It lists the existing instances in the manager.
     *
     * @return a {@link ListResult} with all the existing instances of the
     * manager.
     */
    @Override
    public ExecutionResult listInstances() {
        Settings.logger().entering(getClass().getName(), "listInstances");
        ExecutionResult result = ListResult.create(qemuInstances.values());
        Settings.logger().exiting(getClass().getName(), "listInstances", result);
        return result;
    }

    /**
     * It executes a list of commands to each of the instances. The commands
     * will be trapped to be replaced for the predefined ones if they match.
     *
     * @param commands a map with all the commands,where the keys match the
     * identification of the instances and the value the list of commands to be
     * executed on it.
     * @return a map with the execution results, where the keys match the
     * identification of the instances and the value the list of results
     * produced.
     */
    @Override
    public ExecutionResult executeCommands(Map<InstanceID, List<Command>> commands) {
        Settings.logger().entering(getClass().getName(), "executeCommands", commands);
        Map<InstanceID, ExecutionResult> resultsMap = new TreeMap<>();
        for (Map.Entry<InstanceID, List<Command>> entry : commands.entrySet()) {
            InstanceID id = entry.getKey();
            Instance instance = qemuInstances.get(id);
            List<ExecutionResult> resultsList = new ArrayList<>();
            if (instance == null) {
                resultsList.add(Fail.because(Reason.UNKNOWN_ID));
                resultsMap.put(id, ListResult.create(resultsList));
            } else {
                for (Command command : entry.getValue()) {
                    if (!trapCommand(instance, command, resultsList)) {
                        resultsList.add(instance.execute(command));
                    }
                }
                resultsMap.put(id, ListResult.create(resultsList));
            }
        }
        ExecutionResult result = MapResult.create(resultsMap);
        Settings.logger().exiting(getClass().getName(), "executeCommands", result);
        return result;
    }

    /**
     * It shuts down all the instances within the manager and then it shuts down
     * the manager itself.
     *
     * @return a {@link ListResult} with the execution result of each instance {@link QemuInstancesManager#shutdownInstance(Instances.InstanceID)
     * } request.
     */
    @Override
    public ExecutionResult shutdown() {
        Settings.logger().entering(getClass().getName(), "shutdown");
        ExecutionResult result;
        List<ExecutionResult> results = new ArrayList<>();
        Command shutdownQMP = ShutdownQMP.create();
        for (Instance instance : qemuInstances.values()) {
            results.add(instance.execute(shutdownQMP));
        }
        result = ListResult.create(results);
        singleton = null;
        Settings.logger().exiting(getClass().getName(), "shutdown", result);
        return result;
    }

    /**
     * It loads the configurations from the file at the specified path and name.
     *
     * @param config the path followed by the file name.
     * @return the execution result of {@link Settings#loadConfigurations(java.lang.String)
     * }.
     */
    @Override
    public ExecutionResult loadConfigurationFile(String config) {
        Settings.logger().entering(getClass().getName(), "loadConfigurationFile", config);
        ExecutionResult result = Settings.loadConfigurations(config);
        Settings.logger().exiting(getClass().getName(), "loadConfigurationFile", result);
        return result;
    }

    /**
     * It tries to replace the command for the predefined one if it matches.
     *
     * @param instance the instance to which the command is sent.
     * @param command the given command.
     * @param results the result list that holds the execution result.
     * @return true if the command could be replaced by a predefined one or
     * false otherwise.
     */
    private boolean trapCommand(Instance instance, Command command, List<ExecutionResult> results) {
        String instruction = command.instruction();
        switch (instruction) {
            case "cont":
                results.add(continueInstance(instance.id()));
                return true;
            case "stop":
                results.add(suspendInstance(instance.id()));
                return true;
            case "quit":
                results.add(shutdownInstance(instance.id()));
                return true;
            default:
                return false;
        }
    }

}
