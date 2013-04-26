package de.jeha.batch_api.dtos;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jns
 */
@XmlRootElement(name = "operation")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class OperationDTO {

    @XmlElement
    private String id;
    @XmlElement(required = true)
    private String method;
    @XmlElement(required = true)
    private String url;
    @XmlElementWrapper(name = "headers")
    @XmlElement(name = "header")
    private List<Header> headers;
    @XmlElement
    private String body;
    @XmlElement
    private Boolean executed;
    @XmlElement
    private StatusLine statusLine;

    public OperationDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Header> getHeader() {
        if (headers == null) {
            headers = new ArrayList<Header>();
        }
        return headers;
    }

    public void setHeaders(List<Header> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Boolean getExecuted() {
        return executed;
    }

    public void setExecuted(Boolean executed) {
        this.executed = executed;
    }

    public StatusLine getStatusLine() {
        return statusLine;
    }

    public void setStatusLine(StatusLine statusLine) {
        this.statusLine = statusLine;
    }

    @XmlAccessorType(value = XmlAccessType.FIELD)
    public static class Header {

        @XmlElement(required = true)
        private String name;
        @XmlElement(required = true)
        private String value;

        public Header() {
        }

        public Header(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    @XmlAccessorType(value = XmlAccessType.FIELD)
    public static class StatusLine {

        @XmlElement(required = true)
        private int statusCode;
        @XmlElement
        private String reasonPhrase;

        public StatusLine() {
        }

        public StatusLine(int statusCode, String reasonPhrase) {
            this.statusCode = statusCode;
            this.reasonPhrase = reasonPhrase;
        }

        public int getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(int statusCode) {
            this.statusCode = statusCode;
        }

        public String getReasonPhrase() {
            return reasonPhrase;
        }

        public void setReasonPhrase(String reasonPhrase) {
            this.reasonPhrase = reasonPhrase;
        }
    }
}
