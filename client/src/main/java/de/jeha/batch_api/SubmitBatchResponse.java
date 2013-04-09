package de.jeha.batch_api;

import de.jeha.batch_api.generated.model.Batch;

/**
 * @author jns
 */
public class SubmitBatchResponse {

    private final int statusCode;
    private final Batch response;

    public SubmitBatchResponse(int statusCode, Batch response) {
        this.statusCode = statusCode;
        this.response = response;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Batch getResponse() {
        return response;
    }

}
