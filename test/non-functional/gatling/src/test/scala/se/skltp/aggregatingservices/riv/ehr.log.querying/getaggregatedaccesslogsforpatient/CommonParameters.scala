package se.skltp.aggregatingservices.riv.ehr.log.querying.getaggregatedaccesslogsforpatient

trait CommonParameters {
  val serviceName:String     = "AccessLogsForPatient"
  val urn:String             = "urn:riv:ehr:log:querying:GetAccessLogsForPatientResponder:1"
  val responseElement:String = "GetAccessLogsForPatientResponse"
  val responseItem:String    = "AccesssLog"
  var baseUrl:String         = if (System.getProperty("baseUrl") != null && !System.getProperty("baseUrl").isEmpty()) {
                                   System.getProperty("baseUrl")
                               } else {
                                   "http://33.33.33.33:8081/GetAggregatedAccessLogsForPatient/service/v1"
                               }
}
