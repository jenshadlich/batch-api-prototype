package de.jeha.batch_api.util;

import de.jeha.batch_api.BatchApiJAXBContextHolder;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * @author jns
 */
public class UnmarshallingHelper {

    /**
     * Private constructor.
     */
    private UnmarshallingHelper() {
    }

    public static Unmarshaller getUnmarshaller() throws IllegalArgumentException {
        try {
            return BatchApiJAXBContextHolder.getContext().createUnmarshaller();
        } catch (JAXBException e) {
            throw new RuntimeException("Failed with creating an Unmarshaller parser instance", e);
        }
    }

}
