/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.datamodel.ws;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import util.entity.LogEntity;

/**
 *
 * @author berni
 */
@XmlRootElement
@XmlType(name = "retrieveAllLogsRsp", propOrder = {
    "logs"
})
public class RetrieveAllLogsRsp {
    private static final long serialVersionUID = 1L;
    private List<LogEntity> logs;

    public RetrieveAllLogsRsp(List<LogEntity> logs) {
        this.logs = logs;
    }

    public RetrieveAllLogsRsp() {
    }

    public List<LogEntity> getLogs() {
        return logs;
    }

    public void setLogs(List<LogEntity> logs) {
        this.logs = logs;
    }

}
