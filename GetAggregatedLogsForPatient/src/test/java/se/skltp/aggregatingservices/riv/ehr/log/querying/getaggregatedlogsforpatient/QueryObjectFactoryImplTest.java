package se.skltp.aggregatingservices.riv.ehr.log.querying.getaggregatedlogsforpatient;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.w3c.dom.Document;
import riv.ehr.log.querying.getaccesslogsforpatientresponder.v1.GetAccessLogsForPatientRequestType;
import riv.ehr.log.querying.getaccesslogsforpatientresponder.v1.ObjectFactory;
import se.skltp.agp.service.api.QueryObject;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilderFactory;

import static org.junit.Assert.*;

public class QueryObjectFactoryImplTest {

    private static final String CATEGORIZATION = "cll-cs";
    private static final String SERVICE_DOMAIN = "riv:ehr.log";
    private static final String RR_ID = "1212121212";
    private static final String SOURCE_SYSTEM = "SS1";

    QueryObjectFactoryImpl objectFactory = new QueryObjectFactoryImpl();

    @Before
    public void setup() {
        objectFactory.setEiCategorization(CATEGORIZATION);
        objectFactory.setEiServiceDomain(SERVICE_DOMAIN);
    }

    @Test
    public void createQueryObject() throws Exception {
        GetAccessLogsForPatientRequestType getAccessLogsForPatientDoc = new GetAccessLogsForPatientRequestType();
        getAccessLogsForPatientDoc.setPatientId(RR_ID);

        Document doc = createDocument(getAccessLogsForPatientDoc);
        QueryObject queryObj = objectFactory.createQueryObject(doc);

        assertNotNull(queryObj.getFindContent());
        assertEquals(SERVICE_DOMAIN, queryObj.getFindContent().getServiceDomain());
        assertEquals(CATEGORIZATION, queryObj.getFindContent().getCategorization());
        assertEquals(RR_ID, queryObj.getFindContent().getRegisteredResidentIdentification());
        assertNull(queryObj.getFindContent().getSourceSystem());
    }

    @Ignore(value = "SOURCE_SYSTEM is not avai")
    @Test
    public void createQueryObject_with_source_system() throws Exception {
        GetAccessLogsForPatientRequestType getAccessLogsForPatientDoc = new GetAccessLogsForPatientRequestType();
        getAccessLogsForPatientDoc.setPatientId(RR_ID);
//        getAccessLogsForPatientDoc.setSourceSystemHSAId(SOURCE_SYSTEM);
        getAccessLogsForPatientDoc.getAny().add(SOURCE_SYSTEM);

        Document doc = createDocument(getAccessLogsForPatientDoc);
        QueryObject queryObj = objectFactory.createQueryObject(doc);

        assertNotNull(queryObj.getFindContent());
        assertEquals(SOURCE_SYSTEM, queryObj.getFindContent().getSourceSystem());
    }

    private Document createDocument(GetAccessLogsForPatientRequestType getAccessLogsForPatientRequestType) throws Exception {
        ObjectFactory of = new ObjectFactory();
        JAXBContext jaxbContext = JAXBContext.newInstance(GetAccessLogsForPatientRequestType.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        Document doc = dbf.newDocumentBuilder().newDocument();
        marshaller.marshal(of.createGetAccessLogsForPatientRequest(getAccessLogsForPatientRequestType), doc);
        return doc;
    }

}
