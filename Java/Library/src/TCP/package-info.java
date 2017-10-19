/**
 * The package contains classes related to the Transmission Control Protocol
 * communication. It provides a {@link TCP.Client} that is used by the
 * {@link Instances.Instance}s to communicate with the Virtual Machine process.
 * <br/><br/>
 * For the time being only QEMU Machine Protocol(QMP) clients exist, i.e. the only
 * existing class is the {@link TCP.QMPClient}.
 */
package TCP;
