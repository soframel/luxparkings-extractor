package org.soframel.mobility.luxparkings

import java.lang.System.exit

fun main(args: Array<String>) {

    try {
        val extractor = VDLJSONExtractor()
        val jsonString = extractor.getParkingJSON()
        val list = extractor.parseParkingsData(jsonString)

        var sender = ElasticSender()

        for (p in list) {
            val name = p["title"]
            if (name != null && name is String) {
                sender.sendToElastic(p.toString(), name)
            } else {
                println("parking with no name found !" + p)
            }
        }

        exit(0)

    }catch (e: Exception){
        println("Exception occured: "+e)
        e.printStackTrace()
        exit(1)
    }
}

