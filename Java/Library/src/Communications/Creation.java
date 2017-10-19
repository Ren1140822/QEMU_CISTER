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

import Instances.InstanceID;

/**
 * An execution result from a creation command (such as
 * {@link Commmands.BuildQemuInstance}) that holds the identity of the created
 * object.
 *
 * @see ExecutionResult
 * @author Manuel Meireles ( mjcdm@isep.ipp.pt )
 */
public class Creation implements ExecutionResult {

    /**
     * The identity of the instance that was created.
     */
    private final InstanceID id;

    /**
     * The interpretation of the execution result success.
     */
    private final boolean success;

    /**
     * A private constructor that initializes the identity and the success
     * interpretation. Despite no validations are made, none of the parameters
     * should be null.
     *
     * @param theID the identity of the new instance.
     * @param wasSuccessful the success interpretation.
     */
    private Creation(InstanceID theID, boolean wasSuccessful) {
        id = theID;
        success = wasSuccessful;
    }

    /**
     * It builds a Creation execution result with the new instance identity and
     * a success interpretation. Despite no validations are made, none of the
     * parameters should be null.
     *
     * @param id the identity of the freshly created instance.
     * @param wasSuccessful the indicator of the creation success.
     * @return the built Creation.
     */
    public static Creation create(InstanceID id, boolean wasSuccessful) {
        return new Creation(id, wasSuccessful);
    }

    /**
     * A textual representation of the result.
     *
     * @return the identity as text.
     */
    @Override
    public String description() {
        return id.toString();
    }

    /**
     * An indicator to assess if the result was successful or not.
     *
     * @return the {@link Creation#success}.
     */
    @Override
    public boolean wasSuccessful() {
        return success;
    }

    /**
     * A getter of the new instance identity.
     *
     * @return the {@link Creation#id}.
     */
    public InstanceID id() {
        return id;
    }

    /**
     * The textual representation of the result.
     *
     * @return the {@link Creation#description()} result.
     */
    @Override
    public String toString() {
        return description();
    }

}
