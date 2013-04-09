package de.jeha.batch_api;

import de.jeha.batch_api.generated.model.Batch;
import de.jeha.batch_api.generated.model.Header;
import de.jeha.batch_api.generated.model.ObjectFactory;
import de.jeha.batch_api.generated.model.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author jns
 */
public class BatchClient {

    private static final Logger LOG = LoggerFactory.getLogger(BatchClient.class);

    public static void main(String[] args) {
        Batch batch = new Batch();
        batch.setOperations(new ObjectFactory().createBatchOperations());

        {
            Operation operation = new Operation();
            operation.setId(UUID.randomUUID().toString());
            operation.setMethod("GET");
            operation.setUrl("http://api.spreadshirt.net/api/v1/serverTime");
            batch.getOperations().getOperations().add(operation);
        }

        {
            Operation operation = new Operation();
            operation.setId(UUID.randomUUID().toString());
            operation.setMethod("GET");
            operation.setUrl("http://api.spreadshirt.net/api/v1/currencies/1?mediaType=json");
            batch.getOperations().getOperations().add(operation);
        }

        {
            Operation operation = new Operation();
            operation.setId(UUID.randomUUID().toString());
            operation.setMethod("GET");
            operation.setUrl("http://api.spreadshirt.net/api/v1/currencies/1?mediaType=xml");
            batch.getOperations().getOperations().add(operation);
        }

        {
            Operation operation = new Operation();
            operation.setId(UUID.randomUUID().toString());
            operation.setMethod("GET");
            operation.setUrl("http://api.spreadshirt.net/api/v1/currencies/nonExisting");
            batch.getOperations().getOperations().add(operation);
        }

        SubmitBatchResponse response =
                new BatchService("http://localhost:9080/batch-api").submitBatch(new SubmitBatchRequest(batch));

        if (response.getStatusCode() == 200) {
            for (Operation operation : response.getResponse().getOperations().getOperations()) {
                Map<String, String> headerMap = new HashMap<String, String>();
                for (Header header : operation.getHeaders().getHeaders()) {
                    headerMap.put(header.getName(), header.getValue());
                }

                LOG.info("\n" +
                        "Code         = {}\n" +
                        "Reason       = {}\n" +
                        "Content-Type = {}\n" +
                        "Body         = {}",
                        new Object[]{
                                operation.getStatusLine().getStatusCode(),
                                operation.getStatusLine().getReasonPhrase(),
                                headerMap.get("Content-Type"),
                                operation.getBody()});
            }
        }
    }

}