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

import java.util.Iterator;
import java.util.Map;

/**
 * An execution result that contains a map between key and value objects (it may
 * be other execution results).
 *
 * @see ExecutionResult
 * @author Manuel Meireles ( mjcdm@isep.ipp.pt )
 * @param <K> the class type of the key objects.
 * @param <V> the class type of the value objects.
 */
public class MapResult<K, V> implements ExecutionResult {

    /**
     * The map of objects.
     */
    private final Map<K, V> map;

    /**
     * The interpretation of the execution result success.
     */
    private final boolean success;

    /**
     * A private constructor that initializes the map. The success result is
     * dependent on the number of items in the map, i.e. an empty map is
     * considered a failure.
     *
     * @param theMap the map of objects from the result.
     */
    private MapResult(Map<K, V> theMap) {
        map = theMap;
        success = (map.size() > 0);
    }

    /**
     * It creates a list of results. It initializes the collection as well as
     * the success interpretation based on the collection size (empty implies
     * failure).
     *
     * @param <K> the class type of the key objects.
     * @param <V> the class type of the value objects.
     * @param map the map of objects from the result.
     * @return the built map result.
     */
    public static <K, V> ExecutionResult create(Map<K, V> map) {
        return new MapResult(map);
    }

    /**
     * A textual representation of the result.
     *
     * @return the result of the {@link Object#toString()} on each pair
     * Key/Value of the map separated by a line separator.
     */
    @Override
    public String description() {
        StringBuilder builder = new StringBuilder();
        Iterator<Map.Entry<K, V>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<K, V> item = iterator.next();
            builder.append(item.getKey());
            builder.append(System.lineSeparator());
            builder.append(item.getValue());
            if (iterator.hasNext()) {
                builder.append(System.lineSeparator());
            }
        }
        return builder.toString();
    }

    /**
     * An indicator to assess if the result was successful or not.
     *
     * @return the {@link MapResult#success}.
     */
    @Override
    public boolean wasSuccessful() {
        return success;
    }

    /**
     * The textual representation of the result.
     *
     * @return the {@link MapResult#description()} result.
     */
    @Override
    public String toString() {
        return description();
    }

}
