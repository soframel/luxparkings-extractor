package org.soframel.mobility.luxparkings

import org.apache.logging.log4j.LogManager
import org.json.JSONArray
import org.json.JSONObject
import org.soframel.mobility.luxparkings.exceptions.IncorrectDataException

class VDLJSONExtractor {
    val url= LuxParkingsProperties.getProperty("luxparkings.data.url")

    val logger = LogManager.getLogger(VDLJSONExtractor::class.java!!.getName())

    @Throws(IncorrectDataException::class)
    fun getParkingJSON(): String{
        val response=khttp.get(url!!)

        if(response!=null && response.statusCode==200){
            logger.debug("Fetching data: got response from server")
            return response.text
        }
        else{
            throw IncorrectDataException("incorrect response: " + response)
        }
    }

    fun parseParkingsData(s: String): List<JSONObject>{
        var result= mutableListOf<JSONObject>()
        val obj=JSONObject(s)
        val parking=obj["parking"]
        if(parking is JSONArray) {
            for(p in parking) {
                if (p is JSONObject) {
                    result.add(p)
                }
            }
        }
        return result
    }
}