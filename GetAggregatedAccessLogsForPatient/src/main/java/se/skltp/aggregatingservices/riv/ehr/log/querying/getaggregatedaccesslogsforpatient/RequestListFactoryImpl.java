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

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import riv.ehr.log.querying.getaccesslogsforpatientresponder.v1.GetAccessLogsForPatientRequestType;
import se.skltp.agp.riv.itintegration.engagementindex.findcontentresponder.v1.FindContentResponseType;
import se.skltp.agp.service.api.QueryObject;
import se.skltp.agp.service.api.RequestListFactory;

public class RequestListFactoryImpl implements RequestListFactory {

    private static final Logger log = LoggerFactory.getLogger(RequestListFactoryImpl.class);

    /**
     * Filtrera svarsposter från engagemangsindexet baserat parametrar i GetAccessLogsForPatientRequestType requestet.
     * Följande villkor måste vara sanna för att en svarspost från EI skall tas med i svaret:
     * <p>
     * 1. request.fromDate <= ei-engagement.mostRecentContent <= reqest.toDate
     * 2. request.careUnitId.size == 0 or request.careUnitId.contains(ei-engagement.logicalAddress)
     * <p>
     * Svarsposter från engagemangsindexet som passerat filtreringen grupperas på fältet sourceSystem
     * samt postens fält logicalAddress (producenter-enhet) samlas i listan careUnitId per varje sourceSystem
     * <p>
     * Ett anrop görs per funnet sourceSystem med följande värden i anropet:
     * <p>
     * 1. logicalAddress = sourceSystem (systemadressering)
     * 2. subjectOfCareId = orginal-request.subjectOfCareId
     * 3. careUnitId = listan av producenter som returnerats från engagemangsindexet för aktuellt source system
     * 4. fromDate = orginal-request.fromDate
     * 5. toDate = orginal-request.toDate
     */
    @Override
    public List<Object[]> createRequestList(QueryObject qo, FindContentResponseType findContentResponse) {
    	return null; //Not used
    }


	@Override
	public List<Object[]> createRequestList(QueryObject qo, List<String> src) {
        GetAccessLogsForPatientRequestType request = (GetAccessLogsForPatientRequestType) qo.getExtraArg();

        // Prepare the result of the transformation as a list of request-payloads,
        // one payload for each unique logical-address (e.g. source system since we are using system addressing),
        // each payload built up as an object-array according to the JAX-WS signature for the method in the service interface
        List<Object[]> reqList = new ArrayList<Object[]>();

		for (String sourceSystem : src) {
            log.info("Calling source system using logical address {} for subject of care id {}", sourceSystem, request.getPatientId());
            Object[] reqArr = new Object[]{sourceSystem, request};
            reqList.add(reqArr);
        }

        log.debug("Transformed payload: {}", reqList);
        return reqList;
	}
}
