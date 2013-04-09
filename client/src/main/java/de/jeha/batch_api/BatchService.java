package de.jeha.batch_api;

import de.jeha.batch_api.generated.model.Batch;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;

/**
 * @author jns
 */
public class BatchService {

    private static final Logger LOG = LoggerFactory.getLogger(BatchService.class);

    private String serviceUrl;

    public BatchService(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public SubmitBatchResponse submitBatch(SubmitBatchRequest request) {
        String requestUrl = serviceUrl + request.getResourcePart();

        Batch result = null;
        final int statusCode;
        try {
            ByteArrayOutputStream batchContentStream = new ByteArrayOutputStream();
            getMarshaller().marshal(request.getBatch(), batchContentStream);
            LOG.info("request = {}", IOUtils.toString(batchContentStream.toByteArray(), "UTF-8"));

            HttpResponse response = doPost(requestUrl, batchContentStream);
            statusCode = response.getStatusLine().getStatusCode();
            LOG.info(response.getStatusLine().toString());

            if (statusCode == 200) {

                for (org.apache.http.Header header : response.getAllHeaders()) {
                    LOG.info(header.getName() + ": " + header.getValue());
                }

                if (response.getEntity() != null) {
                    String responseXml = IOUtils.toString(response.getEntity().getContent(), Charset.forName("UTF-8"));
                    LOG.info(responseXml);

                    result = (Batch) UnmarshallingHelper.getUnmarshaller().unmarshal(new StringReader(responseXml));
                    LOG.info("result = {}", result);
                }
            }

        } catch (Exception e) {
            // TODO: handle me :)
            throw new RuntimeException(e);
        }

        return new SubmitBatchResponse(statusCode, result);
    }

    private HttpResponse doPost(String url, ByteArrayOutputStream batchContentStream) throws IOException {
        HttpPost post = new HttpPost(url);
        post.setEntity(new ByteArrayEntity(batchContentStream.toByteArray()));
        post.setHeader("Content-Type", "application/xml");

        HttpClient client = new DefaultHttpClient();
        return client.execute(post);
    }

    private Marshaller getMarshaller() throws JAXBException {
        Marshaller marshaller = BatchApiJAXBContextHolder.getContext().createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        return marshaller;
    }
}
