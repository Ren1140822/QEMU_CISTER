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
package Configurations;

import Communications.ExecutionResult;
import Communications.Fail;
import Communications.Fail.Reason;
import Communications.Success;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * The library configurations.
 *
 * @author Manuel Meireles ( mjcdm@isep.ipp.pt )
 */
public class Settings {

    /**
     * The reporting message of the success on loading the configuration file.
     */
    private static final String SUCCESSFUL_LOADING_MESSAGE = "The configurations were successfully loaded.";

    /**
     * The reporting message of the failure on loading the configuration file.
     */
    private static final String INSUCCESSFUL_LOADING_MESSAGE = "The configurations were not loaded (using default).";

    /**
     * The default file path and name of the configurations file.
     */
    private static final String PROPERTIES_FILE_NAME = "config.properties";

    /**
     * The name of the property key to define the path in which to execute the
     * QEMU.
     */
    private static final String PROPERTY_QEMU_PATH_KEY = "qemu_path";

    /**
     * The value of the property that defines the path in which to execute the
     * QEMU.
     */
    private static final String PROPERTY_QEMU_PATH_VALUE = "C:\\Program Files\\qemu\\";

    /**
     * The name of the property key to define the log file path and name.
     */
    private static final String PROPERTY_LOG_FILE_NAME_KEY = "log_file_name";

    /**
     * The value of the property that defines the log file path and name.
     */
    private static final String PROPERTY_LOG_FILE_NAME_VALUE = "info.log";

    /**
     * The name of the property key to define the first port number to be
     * assigned to the instances.
     */
    private static final String PROPERTY_INITIAL_PORT_NUMBER_KEY = "port_number";

    /**
     * The value of the property that defines the first port number to be
     * assigned to the instances.
     */
    private static final String PROPERTY_INITIAL_PORT_NUMBER_VALUE = "30040";

    /**
     * The singleton object of the settings.
     */
    private static Settings singleton;

    /**
     * The properties that store all the configurations.
     */
    private Properties properties;

    /**
     * The logger that will log occurrences to the file.
     */
    private Logger logger;

    /**
     * A private constructor to ensure the singleton pattern. It loads the
     * configurations from the default file.
     */
    private Settings() {
        loadProperties(PROPERTIES_FILE_NAME);
    }

    /**
     * A private getter of the singleton. The singleton should not be given
     * directly, rather their functions should be called directly.
     *
     * @return the singleton object.
     */
    private static Settings settings() {
        if (singleton == null) {
            singleton = new Settings();
        }
        return singleton;
    }

    /**
     * It loads the configuration from the given file path and name. If it
     * fails, it will load the default configurations.
     *
     * @param file the file path followed by the its name.
     * @return <ul><li>a {@link Success} if the file configurations were
     * loaded;</li><li>a {@link Fail} if the default configurations were
     * loaded;</li></ul>
     */
    public static ExecutionResult loadConfigurations(String file) {
        logger().entering(Settings.class.getName(), "loadConfigurations", file);
        ExecutionResult result;
        if (settings().loadProperties(file)) {
            result = Success.achieved(SUCCESSFUL_LOADING_MESSAGE);
        } else {
            result = Fail.because(Reason.FILE_UNAVAILABLE);
        }
        logger().exiting(Settings.class.getName(), "loadConfigurations", result);
        return result;
    }

    /**
     * A getter to the logger object.
     *
     * @return the logger that logs into the file.
     */
    public static Logger logger() {
        return settings().logger;
    }

    /**
     * It provides the path in which the QEMU is installed and can be executed,
     * i.e. the value store ate the property
     * {@link Settings#PROPERTY_QEMU_PATH_KEY}.
     *
     * @return the QEMU path.
     */
    public static String getQemuPath() {
        return settings().getProperty(PROPERTY_QEMU_PATH_KEY);
    }

    /**
     * It provides the starting port number to be assigned to the instances,
     * i.e. the value store ate the property
     * {@link Settings#PROPERTY_INITIAL_PORT_NUMBER_KEY}.
     *
     * @return the starting port number to be assigned.
     */
    public static int getStartingPortNumber() {
        return Integer.parseInt(settings().getProperty(PROPERTY_INITIAL_PORT_NUMBER_KEY));
    }

    /**
     * It loads the configurations from the given file path and name. If it
     * fails, it will load the default configurations.
     *
     * @param file the properties file to be loaded.
     * @return true if the properties are loaded from the file or false if the
     * default ones are loaded.
     */
    private boolean loadProperties(String file) {
        properties = new Properties();
        try {
            InputStream propertiesInputStream = new FileInputStream(new File(file));
            if (propertiesInputStream == null) {
                throw new IOException();
            }
            properties.load(propertiesInputStream);
            propertiesInputStream.close();
            loadLogger();
            logger.fine(SUCCESSFUL_LOADING_MESSAGE);
            return true;
        } catch (IOException ex) {
            loadDefaultProperties();
            logger.severe(INSUCCESSFUL_LOADING_MESSAGE + " | " + ex.toString());
            return false;
        }
    }

    /**
     * It loads the default configurations.
     */
    private void loadDefaultProperties() {
        properties = new Properties();
        properties.setProperty(PROPERTY_QEMU_PATH_KEY, PROPERTY_QEMU_PATH_VALUE);
        properties.setProperty(PROPERTY_LOG_FILE_NAME_KEY, PROPERTY_LOG_FILE_NAME_VALUE);
        properties.setProperty(PROPERTY_INITIAL_PORT_NUMBER_KEY, PROPERTY_INITIAL_PORT_NUMBER_VALUE);

        try {
            loadLogger();
        } catch (IOException ex) {
            Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * It builds the logger in order to log to the file.
     *
     * @throws IOException due to
     * {@link FileHandler#FileHandler(java.lang.String)}.
     */
    private void loadLogger() throws IOException {
        String logFileName = getProperty(PROPERTY_LOG_FILE_NAME_KEY);
        FileHandler fileHandler = new FileHandler(logFileName);
        SimpleFormatter formatter = new SimpleFormatter();
        fileHandler.setFormatter(formatter);
        logger = Logger.getLogger(logFileName);
        logger.setUseParentHandlers(false);
        logger.addHandler(fileHandler);
        logger.setLevel(Level.FINEST);
    }

    /**
     * A getter of a property.
     * 
     * @param propertyName the key name of the property to get.
     * @return the property value.
     */
    private String getProperty(String propertyName) {
        return properties.getProperty(propertyName);
    }

}
