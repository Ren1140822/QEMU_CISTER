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

import java.util.Collection;
import java.util.Iterator;

/**
 * An execution result that contains a collection of objects from a given type
 * (it may be other execution results).
 *
 * @see ExecutionResult
 * @author Manuel Meireles ( mjcdm@isep.ipp.pt )
 * @param <T> The type of the objects within the collection.
 */
public class ListResult<T> implements ExecutionResult {

    /**
     * The collection of objects.
     */
    private final Collection<T> list;

    /**
     * The interpretation of the execution result success.
     */
    private final boolean success;

    /**
     * A private constructor that initializes the collection. The success result
     * is dependent on the number of items in the collection, i.e. an empty
     * collection is considered a failure.
     *
     * @param theList the collection of objects from the result.
     */
    private ListResult(Collection<T> theList) {
        list = theList;
        success = (list.size() > 0);
    }

    /**
     * It creates a list of results. It initializes the collection as well as
     * the success interpretation based on the collection size (empty implies
     * failure).
     *
     * @param <T> the object type of the collection. It may or may not be other
     * execution results.
     * @param list the collection of objects from the result.
     * @return the built list result.
     */
    public static <T> ExecutionResult create(Collection<T> list) {
        return new ListResult(list);
    }

    /**
     * A textual representation of the result.
     *
     * @return the result of the {@link Object#toString()} on each object of the
     * collection separated by a line separator.
     */
    @Override
    public String description() {
        StringBuilder builder = new StringBuilder();
        Iterator<T> iterator = list.iterator();
        while (iterator.hasNext()) {
            T item = iterator.next();
            builder.append(item.toString());
            if (iterator.hasNext()) {
                builder.append(System.lineSeparator());
            }
        }
        return builder.toString();
    }

    /**
     * An indicator to assess if the result was successful or not.
     *
     * @return the {@link ListResult#success}.
     */
    @Override
    public boolean wasSuccessful() {
        return success;
    }

    /**
     * The textual representation of the result.
     *
     * @return the {@link ListResult#description()} result.
     */
    @Override
    public String toString() {
        return description();
    }

}
