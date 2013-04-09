package de.jeha.batch_api;

import de.jeha.batch_api.dtos.BatchDTO;
import de.jeha.batch_api.dtos.OperationDTO;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author jns
 */
@Path("/batch")
@Component
@Scope("request")
public class BatchResource {

    private static final Logger LOG = LoggerFactory.getLogger(BatchResource.class);

    @POST
    @Consumes("application/xml")
    @Produces("application/xml")
    public BatchDTO submitBatch(BatchDTO batchRequest) throws IOException {
        LOG.info("Submit batch request: {}", batchRequest);

        BatchDTO batchResponse = new BatchDTO();

        for (OperationDTO operationInput : batchRequest.getOperations()) {
            OperationDTO operation = new OperationDTO();
            operation.setId(operationInput.getId());

            final String method = operationInput.getMethod().toUpperCase();
            if ("GET".equals(method)) {
                HttpGet get = new HttpGet(operationInput.getUrl());
                if (operationInput.getHeader() != null) {
                    for (OperationDTO.Header header : operationInput.getHeader()) {
                        get.addHeader(header.getName(), header.getValue());
                    }
                }

                HttpClient client = new DefaultHttpClient();
                HttpResponse response = client.execute(get);

                LOG.info(response.getStatusLine().toString());

                final int statusCode = response.getStatusLine().getStatusCode();
                final String reasonPhrase = response.getStatusLine().getReasonPhrase();
                operation.setStatusLine(new OperationDTO.StatusLine(statusCode, reasonPhrase));

                if (response.getEntity() != null) {
                    String responseBody = IOUtils.toString(response.getEntity().getContent(), Charset.forName("UTF-8"));
                    LOG.info(responseBody);
                    operation.setBody(responseBody);
                }
                if (response.getAllHeaders() != null) {
                    for (Header header : response.getAllHeaders()) {
                        operation.getHeader().add(new OperationDTO.Header(header.getName(), header.getValue()));
                    }
                }
            } else {
                operation.setStatusLine(new OperationDTO.StatusLine(400, "Bad request"));
                operation.setBody(String.format("Method %s not supported!", method));
            }

            batchResponse.getOperations().add(operation);
        }

        return batchResponse;
    }

}