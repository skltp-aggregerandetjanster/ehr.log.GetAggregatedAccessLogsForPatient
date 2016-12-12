package se.skltp.aggregatingservices.riv.ehr.log.querying.getaggregatedaccesslogsforpatient.integrationtest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import riv.ehr.log.querying.getaccesslogsforpatientrequest.v1.GetAccessLogsForPatientResponderInterface;
import riv.ehr.log.querying.getaccesslogsforpatientresponder.v1.GetAccessLogsForPatientResponseType;
import se.skltp.agp.test.producer.TestProducerDb;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(serviceName = "GetAccessLogsForPatientResponderService", portName = "GetAccessLogsForPatientResponderPort",
        targetNamespace = "urn:riv:ehr.log.querying:GetAccessLogsForPatientResponder:1:rivtabp21", name = "GetAccessLogsForPatientInteraction")
public class AccessLogsForPatientTestProducer implements GetAccessLogsForPatientResponderInterface {

    private static final Logger log = LoggerFactory.getLogger(AccessLogsForPatientTestProducer.class);

    private TestProducerDb testDb;

    public void setTestDb(TestProducerDb testDb) {
        this.testDb = testDb;
    }

    @WebMethod(operationName = "GetAccessLogsForPatient", action = "urn:riv:ehr:log:querying:GetAccessLogsForPatientResponder:1:GetAccessLogsForPatient")
    @WebResult(name = "GetAccessLogsForPatientResponse", targetNamespace = "urn:riv:ehr:log:querying:GetAccessLogsForPatientResponder:1", partName = "parameters")
    public riv.ehr.log.querying.getaccesslogsforpatientresponder.v1.GetAccessLogsForPatientResponseType getAccessLogsForPatient(
            @WebParam(partName = "LogicalAddress", name = "LogicalAddress", targetNamespace = "urn:riv:itintegration:registry:1", header = true)
                    java.lang.String logicalAddress,
            @WebParam(partName = "parameters", name = "GetAccessLogsForPatientRequest", targetNamespace = "urn:riv:ehr:log:querying:GetAccessLogsForPatientResponder:1")
                    riv.ehr.log.querying.getaccesslogsforpatientresponder.v1.GetAccessLogsForPatientRequestType request) {

        log.info("### Virtual service for GetAccessLogsForPatient call the source system with logical address: {} and patientId: {}", logicalAddress, request.getPatientId());

        GetAccessLogsForPatientResponseType response = (GetAccessLogsForPatientResponseType) testDb.processRequest(logicalAddress, request.getPatientId());
        if (response == null) {
            // Return an empty response object instead of null if nothing is found
            log.info("### Virtual service got {} documents in the reply from the source system with logical address: {} and patientId: {}",
                    new Object[]{0, logicalAddress, request.getPatientId()});
            throw new RuntimeException("Service returned no documents in the reply from the source system");
        } else {

        	log.info("### Virtual service got {} documents in the reply from the source system with logical address: {} and patientId: {}",
        			new Object[]{response.getAccessLogsResultType().size(), logicalAddress, request.getPatientId()});
        }
        // We are done
        return response;
    }
}
