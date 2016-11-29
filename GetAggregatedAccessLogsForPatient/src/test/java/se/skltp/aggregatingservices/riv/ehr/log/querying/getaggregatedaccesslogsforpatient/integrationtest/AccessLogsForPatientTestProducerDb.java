package se.skltp.aggregatingservices.riv.ehr.log.querying.getaggregatedaccesslogsforpatient.integrationtest;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import riv.ehr.log.querying.getaccesslogsforpatientresponder.v1.GetAccessLogsForPatientResponseType;
import riv.ehr.log.querying.v1.AccessLogType;
import riv.ehr.log.querying.v1.AccessLogsResultType;
import riv.ehr.log.querying.v1.AccessLogsType;
import se.skltp.agp.test.producer.TestProducerDb;

import java.util.GregorianCalendar;

public class AccessLogsForPatientTestProducerDb extends TestProducerDb {

    private static final Logger log = LoggerFactory.getLogger(AccessLogsForPatientTestProducerDb.class);

    @Override
    public Object createResponse(Object... responseItems) {
        log.debug("Creates a response with {} items", responseItems);

        GetAccessLogsForPatientResponseType response = createGetAccessLogsForPatientResponseType();

        for (int i = 0; i < responseItems.length; i++) {
            response.getAccessLogsResultType().getAccesssLogs().getAccessLog().add((AccessLogType) responseItems[i]);
        }

        return response;
    }

    private GetAccessLogsForPatientResponseType createGetAccessLogsForPatientResponseType() {
        GetAccessLogsForPatientResponseType response = new GetAccessLogsForPatientResponseType();
        response.setAccessLogsResultType(new AccessLogsResultType());
        response.getAccessLogsResultType().setAccesssLogs(new AccessLogsType());

        return response;
    }

    @Override
    public Object createResponseItem(String logicalAddress, String registeredResidentId, String businessObjectId, String time) {
        log.debug("Created one response item for logical-address {}, registeredResidentId {} and businessObjectId {}",
                new Object[]{logicalAddress, registeredResidentId, businessObjectId});

        AccessLogType response = new AccessLogType();
        response.setCareProviderId("care provider id 1 /" + registeredResidentId + " /" + logicalAddress);
        response.setCareProviderName("Vårdcentralen Kusten, Kärna");
        response.setCareUnitId("care unit id 1");
        response.setCareUnitName("Vårdcentralen Kusten, Kärna");
        response.setAccessDate(new XMLGregorianCalendarImpl(new GregorianCalendar()));
        response.setPurpose("regular visit");
        response.setResourceType("resource type 1");

        return response;
    }
}
