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
 * An execution result from the expected execution of a command. It may hold a
 * description of the success.
 *
 * @see Commands.Command
 * @see ExecutionResult
 * @author Manuel Meireles ( mjcdm@isep.ipp.pt )
 */
public class Success implements ExecutionResult {

    /**
     * The default description to be used.
     */
    private static final String DEFAULT_DESCRIPTION = "Success";

    /**
     * A description of the success.
     */
    private final String description;

    /**
     * A private constructor that initializes the description. Despite no
     * validations are made, the parameter should not be null.
     *
     * @param theDescription the description of the result success.
     */
    private Success(String theDescription) {
        description = theDescription;
    }

    /**
     * It builds a Success execution result with the default description
     * message.
     *
     * @return the built Success.
     */
    public static ExecutionResult achieved() {
        return new Success(DEFAULT_DESCRIPTION);
    }

    /**
     * It builds a Success execution result with the given description message.
     * Despite no validations are made, the parameter should not be null.
     *
     * @param description the description message from the success.
     * @return the built Success.
     */
    public static ExecutionResult achieved(String description) {
        return new Success(description);
    }

    /**
     * A textual representation of the result description.
     *
     * @return the description as text.
     */
    @Override
    public String description() {
        return description;
    }

    /**
     * An indicator to assess if the result was successful or not. The success
     * is always true.
     *
     * @return true.
     */
    @Override
    public boolean wasSuccessful() {
        return true;
    }

    /**
     * The textual representation of the result.
     *
     * @return the {@link Success#description()} result.
     */
    @Override
    public String toString() {
        return description();
    }

}
