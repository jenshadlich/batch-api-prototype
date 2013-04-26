package de.jeha.batch_api;

import de.jeha.batch_api.core.BatchProcessor;
import de.jeha.batch_api.dtos.BatchDTO;
import org.restlet.ext.wadl.WadlServerResource;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.io.IOException;

/**
 * @author jns
 */
@Path("/batch")
@Component
public class BatchResource extends WadlServerResource {

    @POST
    @Consumes("application/xml")
    @Produces("application/xml")
    public BatchDTO submitBatch(BatchDTO batchRequest) throws IOException {
        return BatchProcessor.process(batchRequest);
    }

}