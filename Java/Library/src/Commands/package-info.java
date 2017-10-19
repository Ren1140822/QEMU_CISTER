/**
 * The package contains classes related to the concept of a Command. A {@link Commands.Command}
 * is an instruction that can be executed regardless of the executor.
 * <br/><br/>
 * For the time being all the commands are to be execute by an
 * {@link Instances.Instance}. They are:<ul>
 * <li>{@link Commands.CapabilitiesQMP}: it should be executed after a
 * {@link Instances.QemuInstance} actually start in order to allow it to execute
 * other commands;</li>
 * <li>{@link Commands.ContinueQMP}: it sends a command to the
 * {@link Instances.QemuInstance} resume its execution;</li>
 * <li>{@link Commands.SuspendQMP}: it sends a command to the
 * {@link Instances.QemuInstance} stop its execution;</li>
 * <li>{@link Commands.ShutdownQMP}: it sends a command to the
 * {@link Instances.QemuInstance} shutdown;</li>
 * <li>{@link Commands.GenericQMP}: it sends any command to the
 * {@link Instances.QemuInstance};</li>
 * <li>{@link Commands.BuildQemuInstance}: it sends a command to the
 * {@link Instances.QemuInstance} class in order for it to provide a new
 * instance;</li>
 * </ul>
 */
package Commands;
