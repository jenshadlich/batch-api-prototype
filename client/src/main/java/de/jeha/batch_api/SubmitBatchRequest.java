package de.jeha.batch_api;

import de.jeha.batch_api.generated.model.Batch;

/**
 * @author jns
 */
public class SubmitBatchRequest {

    private final Batch batch;

    public SubmitBatchRequest(Batch batch) {
        this.batch = batch;
    }

    public String getResourcePart() {
        return "/batch";
    }

    public Batch getBatch() {
        return batch;
    }
}
