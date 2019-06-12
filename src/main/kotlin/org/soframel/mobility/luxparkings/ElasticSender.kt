package org.soframel.mobility.luxparkings

import org.apache.http.HttpHost
import org.apache.logging.log4j.LogManager
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestClient
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.common.xcontent.XContentType
import org.soframel.mobility.luxparkings.exceptions.PasswordMissingException
import java.time.Clock
import java.time.Instant
import java.time.LocalTime
import java.time.format.DateTimeFormatter
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
        if(pwd==null || pwd.equals("")){
            throw PasswordMissingException("no password for user $username")
        }

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
        //date is interpreted by elasticsewarch, leave in UTC
        val formattedDate=instant.toString()
        //time is not, convert to local time
        val time= LocalTime.now(Clock.systemDefaultZone())
        //val formattedTime=time.format(DateTimeFormatter.ISO_LOCAL_TIME)
        val formattedTime=this.formatTime(time)

        val request = IndexRequest("parkings")
        request.id(parkingName+"_"+formattedDate)
        //removed                 "\"user\":\"admin\"," +
        val jsonString = "{" +
                "\"user\":\""+username+"\"," +
                "\"postDate\":\""+formattedDate+"\"," +
                "\"parkingName\":\""+parkingName+"\"," +
                "\"time\":\""+formattedTime+"\"," +
                "\"message\":"+json +
                "}"
        request.source(jsonString, XContentType.JSON)

        logger.debug("sending JSON:"+jsonString)

        val indexResponse = client.index(request, options)
        logger.info("elastic response="+indexResponse)
    }

    fun formatTime(time: LocalTime): String{
        //String=time.hour.toString()+time.minute.toString()
        var hour: String=time.hour.toString()
        if(time.hour<10){
            hour="0"+hour
        }
        var minutes: String=time.minute.toString()
        if(time.minute<10){
            minutes="0"+minutes
        }
        return hour+minutes
    }
}