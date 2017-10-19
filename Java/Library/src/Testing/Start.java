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
package Testing;

import Commands.BuildQemuInstance;
import Commands.Command;
import Commands.GenericQMP;
import Communications.ExecutionResult;
import Communications.Fail;
import Instances.InstanceID;
import Instances.QemuInstanceID;
import Managers.InstanceManager;
import Managers.QemuInstancesManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * A testing class. It builds a console that allows interaction with the
 * manager. It is somewhat basic and for testing purpose only.
 *
 * @author Manuel Meireles ( mjcdm@isep.ipp.pt )
 */
public class Start {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("WELCOME");
        InstanceManager manager = QemuInstancesManager.getInstance();
        startMachines(manager, 4);
        System.out.println(manager.listInstances().description());
        loop();
        System.out.println(manager.shutdown());
        System.out.println("GOODBYE");
    }

    /**
     * The loop to keep reading commands.
     */
    private static void loop() {
        Scanner in = new Scanner(System.in);
        InstanceManager manager = QemuInstancesManager.getInstance();
        boolean iterate = true;
        do {
            System.out.println("Insert instruction: ");
            String[] input = in.nextLine().split("\\|");
            if (input.length == 2) {
                System.out.println(runCommand(manager, input[0], input[1]));
            } else {
                iterate = false;
            }
        } while (iterate);
    }

    /**
     * It starts a set of machines.
     *
     * @param manager the manager of the machines.
     * @param number the number of machines to be created.
     */
    private static void startMachines(InstanceManager manager, int number) {
        String qemu = "C:\\Program Files\\qemu\\qemu-system-i386";
        for (int i = 0; i < number; i++) {
            manager.startInstance(BuildQemuInstance.command(qemu));
        }
    }

    /**
     * It runs a given command.
     *
     * @param manager the manager to send the command.
     * @param instruction the command to the manager or the id of an instance
     * for the "executeCMD".
     * @param argument the id of the machine in which to apply the instruction
     * or the command to send on the "executeCMD".
     * @return the execution result.
     */
    private static ExecutionResult runCommand(InstanceManager manager, String instruction, String argument) {
        long id;
        try {
            id = Long.parseLong(instruction);
        } catch (NumberFormatException e) {
            return executeManagerInstruction(manager, instruction, argument);
        }
        Map<InstanceID, List<Command>> result = new TreeMap<>();
        List<Command> commands = new ArrayList<>();
        InstanceID instance = QemuInstanceID.valueOf(id);
        Command command = GenericQMP.create(argument);
        commands.add(command);
        result.put(instance, commands);
        return manager.executeCommands(result);
    }

    /**
     * It calls the right method on a given instruction to the manager.
     *
     * @param manager the manager to execute the instruction.
     * @param instruction the instruction to be executed.
     * @param argument the parameters of the instruction.
     * @return the execution result.
     */
    private static ExecutionResult executeManagerInstruction(InstanceManager manager, String instruction, String argument) {
        int id;
        switch (instruction) {
            case "continue":
                id = Integer.parseInt(argument);
                return manager.continueInstance(QemuInstanceID.valueOf(id));
            case "suspend":
                id = Integer.parseInt(argument);
                return manager.suspendInstance(QemuInstanceID.valueOf(id));
            case "shutdown":
                id = Integer.parseInt(argument);
                return manager.shutdownInstance(QemuInstanceID.valueOf(id));
            case "list":
                return manager.listInstances();
            default:
                return Fail.because(Fail.Reason.NULL_ARGUMENT, "Unknown command \"" + instruction + "\".");
        }
    }

}
