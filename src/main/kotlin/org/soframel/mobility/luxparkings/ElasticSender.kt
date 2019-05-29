package org.soframel.mobility.luxparkings

import org.apache.http.HttpHost
import org.apache.logging.log4j.LogManager
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestClient
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.common.xcontent.XContentType
import java.time.Instant
import java.util.*


class ElasticSender {
    val logger = LogManager.getLogger(VDLJSONExtractor::class.java!!.getName())

    var client: RestHighLevelClient
    val options: RequestOptions
    var username: String?

    init{
        //build client
        val host= LuxParkingsProperties.getProperty("luxparkings.elastic.hostname")
        val port= LuxParkingsProperties.getProperty("luxparkings.elastic.port")
        val portInt=Integer.parseInt(port)
        val scheme= LuxParkingsProperties.getProperty("luxparkings.elastic.scheme")
        logger.debug("sending to elastic at "+scheme+"://"+host+":"+port)
        client = RestHighLevelClient(RestClient.builder(HttpHost(host, portInt, scheme)))

        //build options
        username= LuxParkingsProperties.getProperty("luxparkings.elastic.username")
        val pwd=System.getProperty("elastic.password")
        val auth=username+":"+pwd
        val token= Base64.getEncoder().encodeToString(auth.toByteArray())
        val builder = RequestOptions.DEFAULT.toBuilder()
        builder.addHeader("Authorization", "Basic $token")
        options = builder.build()
    }


    /**
     * send whole JSOn to elastic search
     */
    fun sendToElastic(json: String, parkingName: String?){
        val instant= Instant.now()
        val formattedTime=instant.toString()

        val request = IndexRequest("parkings")
        request.id(parkingName+"_"+formattedTime)
        //removed                 "\"user\":\"admin\"," +
        val jsonString = "{" +
                "\"user\":\""+username+"\"," +
                "\"postDate\":\""+formattedTime+"\"," +
                "\"parkingName\":\""+parkingName+"\"," +
                "\"message\":"+json +
                "}"
        request.source(jsonString, XContentType.JSON)

        logger.debug("sending JSON:"+jsonString)

        val indexResponse = client.index(request, options)
        logger.info("elastic response="+indexResponse)
    }
}