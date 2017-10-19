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
 * An execution result from any command (such as
 * {@link Commmands.GenericQMP}) that creates a reply.
 *
 * @see ExecutionResult
 * @author Manuel Meireles ( mjcdm@isep.ipp.pt )
 */
public class Reply implements ExecutionResult {

    /**
     * The reply message.
     */
    private final String reply;

    /**
     * The interpretation of the execution result success.
     */
    private final boolean success;

    /**
     * A private constructor that initializes the reply and the success
     * interpretation. Despite no validations are made, none of the parameters
     * should be null.
     *
     * @param theReply the message from the reply.
     * @param wasSuccessful the indicator of the reply success.
     */
    private Reply(String theReply, boolean wasSuccessful) {
        reply = theReply;
        success = wasSuccessful;
    }

    /**
     * It builds a Reply execution result with the reply message and a success
     * interpretation. Despite no validations are made, none of the parameters
     * should be null.
     *
     * @param reply the message from the reply.
     * @param wasSuccessful the indicator of the reply success.
     * @return the built Reply.
     */
    public static Reply create(String reply, boolean wasSuccessful) {
        return new Reply(reply, wasSuccessful);
    }

    /**
     * A textual representation of the result.
     *
     * @return the reply as text.
     */
    @Override
    public String description() {
        return reply;
    }

    /**
     * An indicator to assess if the result was successful or not.
     *
     * @return the {@link Reply#success}.
     */
    @Override
    public boolean wasSuccessful() {
        return success;
    }

    /**
     * The textual representation of the result.
     *
     * @return the {@link Reply#description()} result.
     */
    @Override
    public String toString() {
        return description();
    }

}
