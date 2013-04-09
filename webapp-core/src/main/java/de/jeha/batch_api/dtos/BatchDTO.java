package de.jeha.batch_api.dtos;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jns
 */
@XmlRootElement(name = "batch")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class BatchDTO {

    @XmlElementWrapper(name = "operations")
    @XmlElement(required = true, name = "operation")
    private List<OperationDTO> operations;

    public BatchDTO() {
    }

    public List<OperationDTO> getOperations() {
        if (operations == null) {
            operations = new ArrayList<OperationDTO>();
        }
        return operations;
    }

    public void setOperations(List<OperationDTO> operations) {
        this.operations = operations;
    }

}
