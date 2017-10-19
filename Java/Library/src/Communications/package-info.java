/**
 * The package contains classes related to the concept of Execution Result. The
 * {@link Communications.ExecutionResult} represents the outcome of a
 * {@link Commands.Command}. An {@link Communications.ExecutionResult} may contain other {@link Communications.ExecutionResult}s.
 * <br/><br/>
 * For the time being there are the following results:<ul>
 * <li>{@link Communications.Creation}: the result of a {@link Commands.BuildQemuInstance} command. It contains the {@link Instances.InstanceID} of the new instance;</li>
 * <li>{@link Communications.Fail}: the result of a devious execution of a command;</li>
 * <li>{@link Communications.ListResult}: the result is a list, i.e. the result of {@link Managers.InstanceManager#listInstances};</li>
 * <li>{@link Communications.MapResult}: the result is a map, i.e. the result of {@link Managers.InstanceManager#executeCommands};</li>
 * <li>{@link Communications.Reply}: the result of a {@link Commands.GenericQMP} send to the QEMU;</li>
 * <li>{@link Communications.Success}: the result of the expected execution of a command;</li>
 * </ul>
 */
package Communications;
