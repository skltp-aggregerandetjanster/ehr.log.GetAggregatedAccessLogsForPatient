package se.skltp.aggregatingservices.riv.ehr.log.querying.getaggregatedaccesslogsforpatient.integrationtest;

import static se.skltp.agp.test.producer.TestProducerDb.TEST_RR_ID_ONE_HIT;

import javax.xml.ws.Holder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import riv.ehr.log.querying.getaccesslogsforpatientrequest.v1.GetAccessLogsForPatientResponderInterface;
import riv.ehr.log.querying.getaccesslogsforpatientresponder.v1.GetAccessLogsForPatientRequestType;
import riv.ehr.log.querying.getaccesslogsforpatientresponder.v1.GetAccessLogsForPatientResponseType;
import se.skltp.aggregatingaccesslogsforpatient.AccessLogsForPatientMuleServer;
import se.skltp.agp.riv.interoperability.headers.v1.ProcessingStatusType;
import se.skltp.agp.test.consumer.AbstractTestConsumer;
import se.skltp.agp.test.consumer.SoapHeaderCxfInterceptor;

public class AccessLogsForPatientTestConsumer extends AbstractTestConsumer<GetAccessLogsForPatientResponderInterface>{

    private static final Logger log = LoggerFactory.getLogger(AccessLogsForPatientTestConsumer.class);

    public static void main(String[] args) {
        log.info("URL: " + AccessLogsForPatientMuleServer.getAddress("SERVICE_INBOUND_URL"));
        String serviceAddress = AccessLogsForPatientMuleServer.getAddress("SERVICE_INBOUND_URL");
        String personnummer = TEST_RR_ID_ONE_HIT;

        AccessLogsForPatientTestConsumer consumer = new AccessLogsForPatientTestConsumer(serviceAddress, SAMPLE_SENDER_ID, SAMPLE_ORIGINAL_CONSUMER_HSAID, SAMPLE_CORRELATION_ID);
        Holder<GetAccessLogsForPatientResponseType> responseHolder = new Holder<GetAccessLogsForPatientResponseType>();
        Holder<ProcessingStatusType> processingStatusHolder = new Holder<ProcessingStatusType>();
        long now = System.currentTimeMillis();
        consumer.callService("logical-adress", personnummer, processingStatusHolder, responseHolder);
        log.info("Returned #care contact = " + responseHolder.value.getAccessLogsResultType().getAccesssLogs().getAccessLog().size() + " in " + (System.currentTimeMillis() - now) + " ms.");
    }

    public AccessLogsForPatientTestConsumer(String serviceAddress, String senderId, String originalConsumerHsaId, String correlationId) {
        // Setup a web service proxy for communication using HTTPS with Mutual Authentication
        super(GetAccessLogsForPatientResponderInterface.class, serviceAddress, senderId, originalConsumerHsaId, correlationId);
    }

    public void callService(String logicalAddress, String id, Holder<ProcessingStatusType> processingStatusHolder, Holder<GetAccessLogsForPatientResponseType> responseHolder) {
        log.debug("Calling GetAccessLogsForPatient-soap-service with id = {}", id);

        GetAccessLogsForPatientRequestType request = new GetAccessLogsForPatientRequestType();
        request.setPatientId(id);

        GetAccessLogsForPatientResponseType response = _service.getAccessLogsForPatient(logicalAddress, request);
        responseHolder.value = response;

        processingStatusHolder.value = SoapHeaderCxfInterceptor.getLastFoundProcessingStatus();
    }
}
