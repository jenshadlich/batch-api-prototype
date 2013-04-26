package de.jeha.batch_api.core;

import de.jeha.batch_api.dtos.BatchDTO;
import de.jeha.batch_api.dtos.OperationDTO;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
            OperationDTO operationResult = new OperationAction(operationInput).process();
            batchResponse.getOperations().add(operationResult);
        }

        return batchResponse;
    }

}
