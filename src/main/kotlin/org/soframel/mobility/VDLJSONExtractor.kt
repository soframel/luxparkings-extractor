package org.soframel.mobility

import org.json.JSONArray
import org.json.JSONObject
import org.soframel.mobility.exceptions.IncorrectDataException

class VDLJSONExtractor {
    val url="https://www.vdl.lu/parking/data.json"

    @Throws(IncorrectDataException::class)
    fun getParkingJSON(): String{
        val response=khttp.get(url)

        if(response!=null && response.statusCode==200){
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