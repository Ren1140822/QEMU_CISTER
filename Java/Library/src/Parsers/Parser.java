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

/**
 * The interface of a parser, i.e. an object capable of transforming the format
 * of a piece of data.
 *
 * @author Manuel Meireles ( mjcdm@isep.ipp.pt )
 */
public interface Parser {

    /**
     * It changes the command into another format.
     * 
     * @param command the original command.
     * @return the transformed command.
     */
    Command parse(Command command);

}
