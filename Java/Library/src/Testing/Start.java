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
        printHeader();
        loop();
        printFooter();
    }

    /**
     * The loop to keep reading commands.
     */
    private static void loop() {
        Scanner in = new Scanner(System.in);
        InstanceManager manager = QemuInstancesManager.getInstance();
        boolean iterate = true;
        System.out.println("== For information about this application type \"help\".");
        do {
            System.out.println("");
            System.out.println("== Insert instruction:");
            String[] input = in.nextLine().split("\\|");
            switch (input.length) {
                case 2:
                    input[0] = input[0].trim().toLowerCase();
                    input[1] = input[1].trim().toLowerCase();
                    System.out.println(runCommand(manager, input[0], input[1]));
                    break;
                case 1:
                    input[0] = input[0].trim().toLowerCase();
                    switch(input[0]){
                        case "list":
                            System.out.println(runCommand(manager, input[0], ""));
                            break;
                        case "help":
                            printHelp();
                            break;
                        case "shutdown":
                            System.out.println(manager.shutdown());
                            iterate = false;
                            break;
                        default:
                            System.out.println("Unrecognized command.");
                    }
                    break;
                default:
                    System.out.println("Unrecognized command.");
                    break;
            }
        } while (iterate);
    }

    /**
     * It prints a welcoming message.
     */
    private static void printHeader(){
        System.out.println("==================================================");
        System.out.println("==                                              ==");
        System.out.println("==           QEMU MANAGER CONSOLE APP           ==");
        System.out.println("==                   WELCOME                    ==");
        System.out.println("==                                              ==");
        System.out.println("==================================================");
        System.out.println("==");
        System.out.println("== VERSION: Alpha v1.0");
        System.out.println("==");
        System.out.println("");
    }

    /**
     * It prints a goodbye message.
     */
    private static void printFooter(){
        System.out.println("");
        System.out.println("==================================================");
        System.out.println("==                                              ==");
        System.out.println("==           QEMU MANAGER CONSOLE APP           ==");
        System.out.println("==                   GOODBYE                    ==");
        System.out.println("==                                              ==");
        System.out.println("==================================================");
    }

    /**
     * It prints the available commands.
     */
    private static void printHelp() {
        System.out.println("");
        System.out.println("== Available commands:");
        System.out.println("==  - \"start | {options}");
        System.out.println("==  - \"build | {options}");
        System.out.println("==  - \"execute | {id}");
        System.out.println("==  - \"continue | {id}\"");
        System.out.println("==  - \"suspend | {id}\"");
        System.out.println("==  - \"shutdown | {id}\"");
        System.out.println("==  - \"{id} | {command}\"");
        System.out.println("==  - \"config | {file}\"");
        System.out.println("==  - \"list\"");
        System.out.println("==  - \"help\"");
        System.out.println("==  - \"shutdown\"");
        System.out.println("== To exit type \"shutdown\".");
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
            case "start":
                return manager.startInstance(BuildQemuInstance.command(argument));
            case "build":
                return manager.buildInstance(BuildQemuInstance.command(argument));
            case "execute":
                id = Integer.parseInt(argument);
                return manager.executeInstance(QemuInstanceID.valueOf(id));
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
            case "config":
                return manager.loadConfigurationFile(argument);
            default:
                return Fail.because(Fail.Reason.NULL_ARGUMENT, "Unknown command \"" + instruction + "\".");
        }
    }

}
