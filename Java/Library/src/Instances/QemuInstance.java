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

import Commands.BuildQemuInstance;
import Commands.CapabilitiesQMP;
import Commands.Command;
import Communications.Fail;
import Communications.Fail.Reason;
import Communications.ExecutionResult;
import Communications.Success;
import Configurations.Settings;
import TCP.Client;
import TCP.QMPClient;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * An instance of QEMU.
 *
 * @see Instance
 * @author Manuel Meireles ( mjcdm@isep.ipp.pt )
 */
public class QemuInstance implements Instance {

    /**
     * The id. It must be unique and final.
     */
    private final InstanceID id;

    /**
     * The IP address in which QEMU will be operating.
     */
    private final String ip;

    /**
     * The port number in which QEMU will be listening.
     */
    private final int port;

    /**
     * The instruction to start the QEMU instance. It must be valid and comply
     * with the host system. It must not have any mention to the «-qmp»
     * parameter.
     */
    private final String options;

    /**
     * The process in which QEMU is running.
     */
    private Process process;

    /**
     * The TCP client to communicate with the QEMU instance.
     */
    private Client qmp;

    /**
     * The QEMU instance constructor. It initializes the attributes without any
     * validation. The data is presumed to be correct.
     *
     * @param thIP the IP address in which the QEMU will be operating.
     * @param thePort the port number in which the QEMU will be listening.
     * @param theOptions the options to be used on the call to the
     * {@link Runtime#exec(java.lang.String)} method.
     */
    private QemuInstance(String thIP, int thePort, String theOptions) {
        id = QemuInstanceID.create();
        ip = thIP;
        port = thePort;
        options = addQMPServerCreation(theOptions, ip, port);
        process = null;
        qmp = null;
    }

    /**
     * It builds a QEMU instance and sets it ready to be started base on the
     * given options. No mention to the «-qmp» parameter should be given.
     *
     * @param options the options to be used on the call to the
     * {@link Runtime#exec(java.lang.String)} method.
     * @return the built QEMU instance.
     */
    public static QemuInstance create(Command options) {
        if (options instanceof BuildQemuInstance) {
            BuildQemuInstance info = (BuildQemuInstance) options;
            return new QemuInstance(info.ip(), info.port(), info.instruction());
        }
        return null;
    }

    /**
     * A getter of the unique and final identification.
     *
     * @return the {@link QemuInstance#id}.
     */
    @Override
    public InstanceID id() {
        return id;
    }

    /**
     * It creates and starts the process of the instance.
     *
     * @return <ul><li>a {@link Success} if everything goes well;</li>
     * <li>a {@link Fail} due to {@link Reason#WRONG_EXEC} if the process fails
     * to be created;</li></ul>
     */
    @Override
    public ExecutionResult start() {
        Settings.logger().entering(getClass().getName(), "start");
        ExecutionResult result;
        try {
            process = Runtime.getRuntime().exec(options);
            if (process.isAlive()) {
                result = Success.achieved(options);
                Settings.logger().exiting(getClass().getName(), "start", result);
                return result;
            }
            result = Fail.because(Reason.WRONG_EXEC);
            Settings.logger().exiting(getClass().getName(), "start", result);
            return result;
        } catch (IOException ex) {
            Logger.getLogger(QemuInstance.class.getName()).log(Level.SEVERE, null, ex);
            result = Fail.because(Reason.WRONG_EXEC);
            Settings.logger().exiting(getClass().getName(), "start", result);
            return result;
        }
    }

    /**
     * It executes the command through the
     * {@link QMPClient#send(Commands.Command)}.
     *
     * @param command the command to be executed.
     * @return <ul><li>the {@link ExecutionResult} given from the
     * {@link QMPClient#send(Commands.Command)};</li>
     * <li>a {@link Fail} due to {@link Reason#QEMU_INSTANCE_IS_OFF} if the
     * process is not alive;</li></ul>
     *
     */
    @Override
    public ExecutionResult execute(Command command) {
        Settings.logger().entering(getClass().getName(), "execute", command);
        ExecutionResult result;
        if (process == null || !process.isAlive()) {
            result = Fail.because(Reason.QEMU_INSTANCE_IS_OFF);
            Settings.logger().exiting(getClass().getName(), "execute", result);
            return result;
        }
        if (qmp == null) {
            ExecutionResult creationResult = createClient();
            if (!creationResult.wasSuccessful()) {
                Settings.logger().exiting(getClass().getName(), "execute", creationResult);
                return creationResult;
            }
        }
        result = qmp.send(command);
        Settings.logger().exiting(getClass().getName(), "execute", result);
        return result;
    }

    /**
     * It appends the «-qmp» parameter to the options.
     *
     * @param options the options to which the «-qmp» parameter will be appended
     * at the end.
     * @param ip the IP address to be used on the «-qmp» parameter.
     * @param port the port number to be used on the «-qmp» parameter.
     * @return the options followed by the «-qmp» parameter correctly built.
     */
    private String addQMPServerCreation(String options, String ip, int port) {
        StringBuilder builder = new StringBuilder();
        builder.append(options);
        builder.append(" -qmp tcp:");
        builder.append(ip);
        builder.append(":");
        builder.append(String.valueOf(port));
        builder.append(",server,nowait");
        return builder.toString();
    }

    /**
     * It initializes the {@link QemuInstance#qmp}.
     *
     * @return <ul><li>the {@link ExecutionResult} of the
     * {@link CapabilitiesQMP};</li><li>a {@link Fail} due to
     * {@link Reason#IO_EXCEPTION} originated at
     * {@link QMPClient#create(java.lang.String, int)};</li></ul>
     */
    private ExecutionResult createClient() {
        try {
            qmp = QMPClient.create(ip, port);
            return execute(CapabilitiesQMP.create());
        } catch (IOException ex) {
            Settings.logger().severe(ex.toString());
            return Fail.because(Reason.IO_EXCEPTION);
        }
    }

    /**
     * It compares the {@link Instance#id()} of both instances.
     *
     * @param that the other instance to compare to.
     * @return a negative integer, zero, or a positive integer as this instance
     * is less than, equal to, or greater than the specified instance.
     */
    @Override
    public int compareTo(Instance that) {
        return this.id().compareTo(that.id());
    }

    /**
     * It provides the textual representation of the id.
     * 
     * @return the {@link InstanceID#toString() }.
     */
    @Override
    public String toString(){
        return id.toString();
    }

}
