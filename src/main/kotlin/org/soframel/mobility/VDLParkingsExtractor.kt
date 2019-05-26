package org.soframel.mobility

import java.lang.System.exit

fun main(args: Array<String>) {

    val extractor=VDLJSONExtractor()
    val jsonString=extractor.getParkingJSON()
    val list=extractor.parseParkingsData(jsonString)

    var sender=ElasticSender()

    for(p in list) {
        val name=p["title"]
        if(name!=null && name is String) {
            sender.sendToElastic(p.toString(), name)
        }
        else{
            println("parking with no name found !"+ p)
        }
    }

    exit(0)
}

