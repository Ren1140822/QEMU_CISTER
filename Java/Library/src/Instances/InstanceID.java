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

/**
 * The interface of a unique identity of an instance. It should be comparable in order to allow sorting of instances.
 * 
 * @see Instance
 * @author Manuel Meireles ( mjcdm@isep.ipp.pt )
 */
public interface InstanceID extends Comparable<InstanceID> {
    
}
