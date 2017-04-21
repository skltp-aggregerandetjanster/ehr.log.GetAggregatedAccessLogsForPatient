package se.skltp.aggregatingservices.riv.ehr.log.querying.getaggregatedaccesslogsforpatient.integrationtest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import riv.ehr.log.querying.getaccesslogsforpatientresponder.v1.GetAccessLogsForPatientResponseType;
import riv.ehr.log.querying.v1.AccessLogType;
import riv.ehr.log.querying.v1.AccessLogsResultType;
import riv.ehr.log.querying.v1.AccessLogsType;
import riv.ehr.log.querying.v1.ResultType;
import riv.ehr.log.v1.ResultCodeType;
import se.skltp.agp.test.producer.TestProducerDb;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;

public class AccessLogsForPatientTestProducerDb extends TestProducerDb {

    private static final Logger log = LoggerFactory.getLogger(AccessLogsForPatientTestProducerDb.class);

    @Override
    public Object createResponse(Object... responseItems) {
        log.debug("Creates a response with {} items", responseItems);

        GetAccessLogsForPatientResponseType response = new GetAccessLogsForPatientResponseType();

        for (int i = 0; i < responseItems.length; i++) {
            response.getAccessLogsResultType().add((AccessLogsResultType) responseItems[i]);
        }

        return response;
    }

    private GetAccessLogsForPatientResponseType createGetAccessLogsForPatientResponseType() {
        GetAccessLogsForPatientResponseType response = new GetAccessLogsForPatientResponseType();
        response.getAccessLogsResultType().add(new AccessLogsResultType());
        response.getAccessLogsResultType().get(0).setAccesssLogs(new AccessLogsType());

        return response;
    }

    @Override
    public Object createResponseItem(String logicalAddress, String registeredResidentId, String businessObjectId, String time) {
        log.debug("Created one response item for logical-address {}, registeredResidentId {} and businessObjectId {}",
                new Object[]{logicalAddress, registeredResidentId, businessObjectId});

        XMLGregorianCalendar cal = null;
        try {
			cal = javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar(2017, 04, 21, 0, 0, 0, 0, 0);
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        AccessLogsResultType response = new AccessLogsResultType();
        ResultType result = new ResultType();
        result.setResultText("OK");
        result.setResultCode(ResultCodeType.OK);
		response.setResult(result);
		
        AccessLogType accessLog = new AccessLogType();
        accessLog.setCareProviderId("care provider id 1 /" + registeredResidentId + " /" + logicalAddress);
        accessLog.setCareProviderName("Vårdcentralen Kusten, Kärna");
        accessLog.setCareUnitId("care unit id 1");
        accessLog.setCareUnitName("Vårdcentralen Kusten, Kärna");
        accessLog.setAccessDate(cal);
        accessLog.setPurpose("regular visit");
        accessLog.setResourceType("resource type 1");

        AccessLogsType accessLogs = new AccessLogsType();
        accessLogs.getAccessLog().add(accessLog);
		response.setAccesssLogs(accessLogs);
        return response;
    }
}
