package se.skltp.aggregatingservices.riv.ehr.log.querying.getaggregatedaccesslogsforpatient

import se.skltp.agp.testnonfunctional.TPPatientsAbstract

/**
 * Test GetAggregatedReferralOutcome using test cases defined in patients.csv (or patients-override.csv)
 */
class TPPatients extends TPPatientsAbstract with CommonParameters {
  setUp(setUpAbstract(serviceName, urn, responseElement, responseItem, baseUrl))
}
