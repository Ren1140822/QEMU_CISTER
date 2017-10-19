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
package Parsers;

import Commands.Command;
import Commands.GenericQMP;

/**
 * A parser. It exchanges the command in CMD format to a JSON format that QEMU
 * understands.
 *
 * @author Manuel Meireles ( mjcdm@isep.ipp.pt )
 */
public class CMDtoJSON implements Parser {

    /**
     * The JSON start of the command.
     */
    private static final String JSON_KEY = "{\"execute\":\"";

    /**
     * The JSON end of the command.
     */
    private static final String JSON_END = "\"}";

    /**
     * It changes the command in CMD format to JSON.
     * 
     * @param command the original command.
     * @return the transformed command.
     */
    @Override
    public Command parse(Command command) {
        return GenericQMP.create(JSON_KEY + command.instruction() + JSON_END);
    }

}
