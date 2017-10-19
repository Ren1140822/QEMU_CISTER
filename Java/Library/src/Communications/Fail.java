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
 * An execution result from a devious execution of a command. It holds the
 * reason why it fail and possibly a description of the issue.
 *
 * @see Commands.Command
 * @see ExecutionResult
 * @author Manuel Meireles ( mjcdm@isep.ipp.pt )
 */
public class Fail implements ExecutionResult {

    /**
     * The possible reasons of a devious execution of a command.
     */
    public enum Reason {
        FILE_UNAVAILABLE,
        IO_EXCEPTION,
        NULL_ARGUMENT,
        QEMU_INSTANCE_IS_OFF,
        UNKNOWN_ID,
        WRONG_EXEC
    }

    /**
     * A separator between the reason and the description to be used in the
     * textual representation.
     */
    private static final String TO_STRING_SEPARATOR = " | ";

    /**
     * The reason that originate the failure.
     */
    private final Reason reason;

    /**
     * A description of the failure. It may be null.
     */
    private final String description;

    /**
     * A private constructor that initializes the reason and the description.
     * Despite no validation is made, it is not suppose to contain a null
     * reason. The description, on the other hand, may be null.
     *
     * @param theReason
     * @param theDescription
     */
    private Fail(Reason theReason, String theDescription) {
        reason = theReason;
        description = theDescription;
    }

    /**
     * It creates a Fail due to the given reason. No description is provided.
     * Despite no validation is made, the reason should not be null.
     *
     * @param reason the reason of the failure.
     * @return the built Fail.
     */
    public static ExecutionResult because(Reason reason) {
        return new Fail(reason, null);
    }

    /**
     * It creates a Fail due to the given reason. The description is provided.
     * Despite no validation is made, the reason should not be null.
     *
     * @param reason the reason of the failure.
     * @param description a description of the reason that created this specific
     * failure.
     * @return the built Fail.
     */
    public static ExecutionResult because(Reason reason, String description) {
        return new Fail(reason, description);
    }

    /**
     * A textual representation of the result.
     *
     * @return the reason as text and, if there is a description, a separation
     * mark followed by the description.
     */
    @Override
    public String description() {
        StringBuilder builder = new StringBuilder();
        builder.append(reason.toString());
        if (description != null) {
            builder.append(TO_STRING_SEPARATOR);
            builder.append(description);
        }
        return builder.toString();
    }

    /**
     * An indicator to assess if the result was successful or not. The failure
     * is always false.
     *
     * @return false.
     */
    @Override
    public boolean wasSuccessful() {
        return false;
    }

    /**
     * The textual representation of the result.
     *
     * @return the {@link Fail#description()} result.
     */
    @Override
    public String toString() {
        return description();
    }

}
