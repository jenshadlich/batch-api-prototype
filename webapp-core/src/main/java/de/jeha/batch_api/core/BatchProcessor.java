package de.jeha.batch_api.core;

import de.jeha.batch_api.dtos.BatchDTO;
import de.jeha.batch_api.dtos.OperationDTO;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author jns
 */

public class BatchProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(BatchProcessor.class);

    public static BatchDTO process(BatchDTO batch) {
        LOG.info("Submit batch request: {}", batch);

        BatchDTO batchResponse = new BatchDTO();

        for (OperationDTO operationInput : batch.getOperations()) {
            OperationDTO operationResult = new OperationDTO();
            operationResult.setId(operationInput.getId());
            operationResult.setMethod(operationInput.getMethod());
            operationResult.setUrl(operationInput.getUrl());

            final String method = operationInput.getMethod().toUpperCase();
            if ("GET".equals(method)) {
                HttpUriRequest get = new HttpGet(operationInput.getUrl());
                if (operationInput.getHeader() != null) {
                    for (OperationDTO.Header header : operationInput.getHeader()) {
                        get.addHeader(header.getName(), header.getValue());
                    }
                }

                HttpClient client = new DefaultHttpClient();
                HttpResponse response = null;
                try {
                    response = client.execute(get);
                } catch (IOException e) {
                    operationResult.setBody(e.toString());
                    operationResult.setStatusLine(new OperationDTO.StatusLine(500, "Internal Server Error"));
                }

                if (response != null) {
                    LOG.info(response.getStatusLine().toString());

                    final int statusCode = response.getStatusLine().getStatusCode();
                    final String reasonPhrase = response.getStatusLine().getReasonPhrase();
                    operationResult.setStatusLine(new OperationDTO.StatusLine(statusCode, reasonPhrase));

                    if (response.getEntity() != null) {
                        String responseBody = null;
                        try {
                            responseBody = IOUtils.toString(response.getEntity().getContent(), Charset.forName("UTF-8"));
                        } catch (IOException e) {
                            responseBody = e.getMessage();
                            operationResult.setStatusLine(new OperationDTO.StatusLine(500, "Internal Server Error"));
                        }
                        LOG.info(responseBody);
                        operationResult.setBody(responseBody);
                    }
                    if (response.getAllHeaders() != null) {
                        for (Header header : response.getAllHeaders()) {
                            operationResult.getHeader().add(new OperationDTO.Header(header.getName(), header.getValue()));
                        }
                    }
                }
            } else {
                operationResult.setStatusLine(new OperationDTO.StatusLine(400, "Bad request"));
                operationResult.setBody(String.format("Method %s not supported!", method));
            }

            batchResponse.getOperations().add(operationResult);
        }

        return batchResponse;
    }
}
