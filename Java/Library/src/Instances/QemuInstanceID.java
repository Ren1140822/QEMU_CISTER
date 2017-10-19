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

/**
 * A QEMU instance identity. It matches an unique long number that will be
 * sequentially distributed on instance creation.
 *
 * @see QemuInstance
 * @see InstanceID
 * @author Manuel Meireles ( mjcdm@isep.ipp.pt )
 */
public class QemuInstanceID implements InstanceID {

    /**
     * The long number to be given in the next ID.
     */
    private static long NEXT_AVAILABLE_ID = 1;

    /**
     * The unique identity number.
     */
    private final long id;

    /**
     * A private constructor that initializes the identity number.
     *
     * @param theID the identity number.
     */
    private QemuInstanceID(long theID) {
        id = theID;
    }

    /**
     * A protected constructor to assure that only QemuIntances generate unique
     * IDs, i.e. it assigns a long number and changes the next available long
     * number.
     *
     * @return the built QemuInstanceID.
     */
    protected static QemuInstanceID create() {
        QemuInstanceID id = new QemuInstanceID(NEXT_AVAILABLE_ID);
        NEXT_AVAILABLE_ID++;
        return id;
    }

    /**
     * It transforms the long number into a QemuInstanceID. It allows the
     * creation of ad-hoc identities to be inserted in the commands. It is not
     * possible to use them on an {@link Instance} creation.
     *
     * @param id the long number to be transformed into a QemuInstanceID
     * @return an identity based on the long number provided.
     */
    public static QemuInstanceID valueOf(long id) {
        return new QemuInstanceID(id);
    }

    /**
     * It checks if the identities contain the same long number.
     *
     * @param that the other object to be compared.
     * @return true if that object is not null, of the class
     * {@link QemuInstanceID} and have the same long number as identity.
     */
    @Override
    public boolean equals(Object that) {
        if (that == null || !(that instanceof QemuInstanceID)) {
            return false;
        }
        QemuInstanceID thatID = (QemuInstanceID) that;
        return this.id == thatID.id;
    }

    /**
     * The hash-code generator.
     *
     * @return the hash-code.
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    /**
     * It provides the textual representation of long number on the identity .
     *
     * @return the long number as string.
     */
    @Override
    public String toString() {
        return String.valueOf(id);
    }

    /**
     * It compares both identities through their hash-code since no other
     * suitable attribute exists.
     *
     * @param that the other id to be compared to.
     * @return a negative integer, zero, or a positive integer as this instance
     * is less than, equal to, or greater than the specified instance.
     */
    @Override
    public int compareTo(InstanceID that) {
        return this.hashCode() - that.hashCode();
    }

}
