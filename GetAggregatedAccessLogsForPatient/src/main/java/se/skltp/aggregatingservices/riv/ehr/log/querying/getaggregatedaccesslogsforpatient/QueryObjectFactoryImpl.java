package se.skltp.aggregatingservices.riv.ehr.log.querying.getaggregatedaccesslogsforpatient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soitoolkit.commons.mule.jaxb.JaxbUtil;
import org.w3c.dom.Node;
import riv.ehr.log.querying.getaccesslogsforpatientresponder.v1.GetAccessLogsForPatientRequestType;
import se.skltp.agp.riv.itintegration.engagementindex.findcontentresponder.v1.FindContentType;
import se.skltp.agp.service.api.QueryObject;
import se.skltp.agp.service.api.QueryObjectFactory;

public class QueryObjectFactoryImpl implements QueryObjectFactory {

    private static final Logger log = LoggerFactory.getLogger(QueryObjectFactoryImpl.class);
    private static final JaxbUtil ju = new JaxbUtil(GetAccessLogsForPatientRequestType.class);

    private String eiServiceDomain;

    public void setEiServiceDomain(String eiServiceDomain) {
        this.eiServiceDomain = eiServiceDomain;
    }

    private String eiCategorization;

    public void setEiCategorization(String eiCategorization) {
        this.eiCategorization = eiCategorization;
    }


    /**
     * Transformerar GetAccessLogsForPatient request till EI FindContent request enligt:
     * <p>
     * 1. patientId/id --> registeredResidentIdentification
     * 2. "riv:ehr:patientsummary" --> serviceDomain
     * 3. typeOfRequest --> categorization
     */
    @Override
    public QueryObject createQueryObject(Node node) {

        GetAccessLogsForPatientRequestType req = (GetAccessLogsForPatientRequestType) ju.unmarshal(node);

        log.debug("Transformed payload for pid: {}", req.getPatientId());

        FindContentType fc = new FindContentType();
        fc.setRegisteredResidentIdentification(req.getPatientId());
        
        //Set service domain to null to indicate no search in EI.
        fc.setServiceDomain(null);
        
        QueryObject qo = new QueryObject(fc, req);

        return qo;
    }
}
