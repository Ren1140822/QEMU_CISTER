/**
 * The package contains classes related to the concept of Instance Manager. An
 * {@link Managers.InstanceManager} is an object responsible for the creation,
 * interaction and destruction of {@link Instances.Instance}s. It is capable of
 * receiving {@link Commands.Command}s and, after their execution, provide
 * {@link Communications.ExecutionResult}s.
 * <br/><br/>
 * For the time being only QEMU instances managers exist, i.e. the only
 * existing instance manager is the {@link Managers.QemuInstancesManager}.
 */
package Managers;
