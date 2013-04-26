package de.jeha.batch_api;

import de.jeha.batch_api.core.BatchProcessor;
import de.jeha.batch_api.dtos.BatchDTO;
import org.springframework.context.annotation.Scope;
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
@Scope("request")
public class BatchResource {

    @POST
    @Consumes("application/xml")
    @Produces("application/xml")
    public BatchDTO submitBatch(BatchDTO batch) throws IOException {
        return BatchProcessor.process(batch);
    }

}