package se.skltp.aggregatingservices.riv.ehr.log.querying.getaggregatedlogsforpatient

trait CommonParameters {
  val serviceName:String     = "AccessLog"
  val urn:String             = "urn:riv:ehr:log:querying:GetAccessLogsForPatientResponder:1"
  val responseElement:String = "GetAccessLogsForPatientResponseType"
  val responseItem:String    = "accessLog"
  var baseUrl:String         = if (System.getProperty("baseUrl") != null && !System.getProperty("baseUrl").isEmpty()) {
                                   System.getProperty("baseUrl")
                               } else {
                                   "http://33.33.33.33:8081/GetAggregatedLogsForPatient/service/v2"
                               }
}
