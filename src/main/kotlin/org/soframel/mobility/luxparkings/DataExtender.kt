package org.soframel.mobility.luxparkings

import org.json.JSONObject

class DataExtender {

    fun combineLuxembourgSudAandB(luxa: JSONObject, luxb: JSONObject): JSONObject{
        var result=luxa

        result.put("actuel", luxa.getInt("actuel")+luxb.getInt("actuel"))
        result.put("complet", (if (luxa.getInt("complet")==1 && luxb.getInt("complet")==1) 1 else 0))
        result.put("id", luxa.getString("id")+ "_"+luxb.getString("id"))
        result.put("ouvert", (if (luxa.getInt("ouvert")==1 || luxb.getInt("ouvert")==1) 1 else 0))
        result.put("tendance", (if (luxa.getInt("tendance")==1 && luxb.getInt("tendance")==1) 1 else 0))
        result.put("title", luxa.getString("title")+"+B")
        result.put("total", luxa.getInt("total")+luxb.getInt("total"))

        /**
         *  For example:
        # message.actuel 	766
        # message.complet 	0
        # message.coords.latitude 	49.578
        # message.coords.longitude 	6.128
        t message.id 	25
        # message.ouvert 	1
        # message.tendance 	0
        t message.title 	Luxembourg Sud A
        # message.total 	811
         */
        return result
    }
}