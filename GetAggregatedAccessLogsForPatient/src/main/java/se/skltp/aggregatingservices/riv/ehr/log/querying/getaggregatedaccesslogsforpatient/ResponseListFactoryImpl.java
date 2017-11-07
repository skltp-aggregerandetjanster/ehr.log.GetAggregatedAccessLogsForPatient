/**
 * Copyright (c) 2014 Inera AB, <http://inera.se/>
 *
 * This file is part of SKLTP.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
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

        for (Object object : aggregatedResponseList) {
            GetAccessLogsForPatientResponseType response = (GetAccessLogsForPatientResponseType) object;
            aggregatedResponse.getAccessLogsResultType()
                    .addAll(response.getAccessLogsResultType());
        }

        if (log.isInfoEnabled()) {
            log.info("Returning {} aggregated access logs",
                    aggregatedResponse.getAccessLogsResultType().size());
        }

        // Since the class GetAccessLogsForPatientResponseType don't have an @XmlRootElement annotation
        // we need to use the ObjectFactory to add it.
        return jaxbUtil.marshal(OF.createGetAccessLogsForPatientResponse(aggregatedResponse));
    }
}
