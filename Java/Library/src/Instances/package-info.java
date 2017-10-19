/**
 * The package contains classes related to the concept of an Instance.
 * An {@link Instances.Instance} can be understood as a reference to a Virtual
 * Machine process that is managed by an {@link Managers.InstanceManager}. Each
 * {@link Instances.Instance} is identified by its unique
 * {@link Instances.InstanceID}.
 * <br/><br/>
 * For the time being only QEMU instances are being managed, i.e. the only
 * existing instance is the {@link Instances.QemuInstance} with its
 * {@link Instances.QemuInstanceID}.
 */
package Instances;
