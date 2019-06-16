package org.soframel.mobility.luxparkings

import org.json.JSONObject
import java.lang.System.exit

fun main(args: Array<String>) {

    try {
        val extractor = VDLJSONExtractor()
        val jsonString = extractor.getParkingJSON()
        val list = extractor.parseParkingsData(jsonString)

        var sender = ElasticSender()
        var extender=DataExtender()

        var luxa: JSONObject?=null
        var luxb: JSONObject?=null
        for (p in list) {
            val name = p["title"]
            if (name != null && name is String) {
                sender.sendToElastic(p.toString(), name)

                if("Luxembourg Sud A".equals(name)){
                    luxa=p
                }
                else if("Luxembourg Sud B".equals(name)){
                    luxb=p
                }

            } else {
                println("parking with no name found !" + p)
            }
        }
        if(luxa!=null && luxb!=null){
            val luxab=extender.combineLuxembourgSudAandB(luxa, luxb)
            val luxabstring=luxab.toString()
            println("combining luxa and luxb: "+luxabstring)
            sender.sendToElastic(luxabstring, luxab.getString("title"))
        }

        exit(0)

    }catch (e: Exception){
        println("Exception occured: "+e)
        e.printStackTrace()
        exit(1)
    }
}

