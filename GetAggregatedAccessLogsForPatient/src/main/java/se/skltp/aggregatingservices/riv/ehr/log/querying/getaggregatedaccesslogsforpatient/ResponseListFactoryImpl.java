package se.skltp.aggregatingservices.riv.ehr.log.querying.getaggregatedaccesslogsforpatient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soitoolkit.commons.mule.jaxb.JaxbUtil;
import riv.ehr.log.querying.getaccesslogsforpatientresponder.v1.GetAccessLogsForPatientResponseType;
import riv.ehr.log.querying.getaccesslogsforpatientresponder.v1.ObjectFactory;
import riv.ehr.log.querying.v1.AccessLogsResultType;
import riv.ehr.log.querying.v1.AccessLogsType;
import riv.ehr.log.querying.v1.ResultType;
import riv.ehr.log.v1.ResultCodeType;
import se.skltp.agp.riv.interoperability.headers.v1.ProcessingStatusType;
import se.skltp.agp.service.api.QueryObject;
import se.skltp.agp.service.api.ResponseListFactory;

import java.util.List;

public class ResponseListFactoryImpl implements ResponseListFactory {

    private static final Logger log = LoggerFactory.getLogger(ResponseListFactoryImpl.class);
    private static final JaxbUtil jaxbUtil = new JaxbUtil(GetAccessLogsForPatientResponseType.class, ProcessingStatusType.class);
    private static final ObjectFactory OF = new ObjectFactory();

    @Override
    public String getXmlFromAggregatedResponse(QueryObject queryObject, List<Object> aggregatedResponseList) {
        GetAccessLogsForPatientResponseType aggregatedResponse = new GetAccessLogsForPatientResponseType();
        aggregatedResponse.setAccessLogsResultType(new AccessLogsResultType());
        aggregatedResponse.getAccessLogsResultType().setAccesssLogs(new AccessLogsType());

        for (Object object : aggregatedResponseList) {
            GetAccessLogsForPatientResponseType response = (GetAccessLogsForPatientResponseType) object;
            aggregatedResponse.getAccessLogsResultType().getAccesssLogs().getAccessLog()
                    .addAll(response.getAccessLogsResultType().getAccesssLogs().getAccessLog());
        }

        aggregatedResponse.getAccessLogsResultType().setResult(new ResultType());
        aggregatedResponse.getAccessLogsResultType().getResult().setResultCode(ResultCodeType.INFO);

        if (log.isInfoEnabled()) {
            log.info("Returning {} aggregated access logs",
                    aggregatedResponse.getAccessLogsResultType().getAccesssLogs().getAccessLog().size());
        }

        // Since the class GetAccessLogsForPatientResponseType don't have an @XmlRootElement annotation
        // we need to use the ObjectFactory to add it.
        return jaxbUtil.marshal(OF.createGetAccessLogsForPatientResponse(aggregatedResponse));
    }
}
