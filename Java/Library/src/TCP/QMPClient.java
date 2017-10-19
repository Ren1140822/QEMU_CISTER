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
package TCP;

import Commands.Command;
import Communications.Fail;
import Communications.Fail.Reason;
import Commands.ShutdownQMP;
import Communications.ExecutionResult;
import Communications.Reply;
import Configurations.Settings;
import Parsers.CMDtoJSON;
import Parsers.Parser;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A client of the QemuInstance.
 *
 * @see Client
 * @see QemuInstance
 * @author Manuel Meireles ( mjcdm@isep.ipp.pt )
 */
public class QMPClient implements Client {

    /**
     * The socket to be used in the communication. It should bind to the IP
     * address and port number given to the instance through the «-qmp»
     * parameter.
     */
    private Socket sock;

    /**
     * The output stream to the socket.
     */
    private BufferedWriter writer;

    /**
     * The input stream to the socket.
     */
    private BufferedReader reader;

    /**
     * A parser capable of changing the received command to a structure that can
     * be understood by the QEMU.
     */
    private final Parser parser;

    /**
     * The client constructor. It initializes the attributes without any
     * validation. The data is presumed to be correct. The parser to be used it
     * defines the parser as {@link CMDtoJSON}. If the necessity arises,
     * multiple parsers might be used through the use of handlers.
     *
     * @param ip the IP address of the QEMU.
     * @param port the port number in which the QEMU is listening.
     * @throws IOException due to {@link Socket#Socket(java.lang.String, int) }.
     */
    private QMPClient(String ip, int port) throws IOException {
        sock = new Socket(ip, port);
        writer = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
        reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        parser = new CMDtoJSON();
    }

    /**
     * It builds a QMP client and sets it ready to communicate. The input data
     * should be same given through the «-qmp» parameter of the instance.
     *
     * @param ip the IP address of the QEMU.
     * @param port the port number in which the QEMU is listening.
     * @return the built QMP client.
     * @throws IOException due to {@link Socket#Socket(java.lang.String, int) }.
     */
    public static QMPClient create(String ip, int port) throws IOException {
        return new QMPClient(ip, port);
    }

    /**
     * It sends a command to the instance. It uses the {@link QMPClient#parser}
     * to parse the received command to JSON format that QEMU recognizes and
     * then writes it to the socket. The received reply is then trapped, in
     * order to check if any action should be taken by the client, and returned.
     *
     * @param command the command to be sent. It is parsed within.
     * @return a {@link Reply} of the QEMU to the command.
     */
    @Override
    public ExecutionResult send(Command command) {
        Settings.logger().entering(getClass().getName(), "send", command);
        ExecutionResult result;
        try {
            Command json = parser.parse(command);

            writer.write(json.instruction());
            writer.flush();
            Settings.logger().fine("SEND " + json);

            String reply = reader.readLine();
            Settings.logger().fine("RECEIVE " + reply);

            result = Reply.create(reply, interpretSuccessOf(command, reply));

            trapCommand(command, result);

            Settings.logger().exiting(getClass().getName(), "send", result);
            return result;
        } catch (IOException ex) {
            Logger.getLogger(QMPClient.class.getName()).log(Level.SEVERE, null, ex);
            result = Fail.because(Reason.IO_EXCEPTION);
            Settings.logger().exiting(getClass().getName(), "send", result);
            return result;
        }
    }

    /**
     * It interprets the reply of the QEMU and decides if the command request
     * was successful.
     *
     * @param request
     * @param reply
     * @return true if the reply implies success or false otherwise.
     */
    private boolean interpretSuccessOf(Command request, String reply) {
        //TODO  this will require an analysis of the possible requests and
        //      outcomes of them in order to be able to decide. It will not
        //      be implemented for the time being.
        return true;
    }

    /**
     * It checks if any action is needed to be taken by this client based on the
     * command and reply made. Traps mounted are:<ul><li>Shutdown: it requires
     * the client to close the socket and streams connections</li></ul>
     *
     * @param command the issued command.
     * @param result the reply gotten from the QEMU.
     */
    private void trapCommand(Command command, ExecutionResult result) {
        if (command instanceof ShutdownQMP && result.wasSuccessful()) {
            close();
        }
    }

    /**
     * It closes the socket as well as the I/O streams.
     */
    private void close() {
        try {
            if (writer != null) {
                writer.close();
                writer = null;
            }
            if (reader != null) {
                reader.close();
                reader = null;
            }
            if (sock != null) {
                sock.close();
                sock = null;
            }
        } catch (IOException ex) {
            Settings.logger().severe(ex.toString());
        }
    }

}
