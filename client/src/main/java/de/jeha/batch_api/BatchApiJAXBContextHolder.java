package de.jeha.batch_api;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

/**
 * @author jns
 */
public class BatchApiJAXBContextHolder {

    private static final String API_GENERATED_CLASSES_PACKAGE = "de.jeha.batch_api.generated.model";
    private static JAXBContext API_JAXB_CONTEXT = null;

    /**
     * Get the JAXB context.
     *
     * @return JAXB Context
     * @throws javax.xml.bind.JAXBException
     */
    public static synchronized JAXBContext getContext() throws JAXBException {
        if (API_JAXB_CONTEXT == null) {
            API_JAXB_CONTEXT = JAXBContext.newInstance(API_GENERATED_CLASSES_PACKAGE);
        }
        return API_JAXB_CONTEXT;
    }
}
