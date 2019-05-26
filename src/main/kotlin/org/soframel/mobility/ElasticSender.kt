package org.soframel.mobility

import org.apache.http.HttpHost
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestClient
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.common.xcontent.XContentType
import java.time.Instant


class ElasticSender {

    var client: RestHighLevelClient

    init{
        client = RestHighLevelClient(RestClient.builder(HttpHost("localhost", 9200, "http")))
    }


    /**
     * send whole JSOn to elastic search
     */
    fun sendToElastic(json: String, parkingName: String?){
        val instant= Instant.now()
        val formattedTime=instant.toString()

        val request = IndexRequest("parkings")
        request.id(parkingName+"_"+formattedTime)
        val jsonString = "{" +
                "\"user\":\"admin\"," +
                "\"postDate\":\""+formattedTime+"\"," +
                "\"parkingName\":\""+parkingName+"\"," +
                "\"message\":"+json +
                "}"
        request.source(jsonString, XContentType.JSON)

        println("sending JSON:"+jsonString)

        val indexResponse = client.index(request, RequestOptions.DEFAULT)
        println("elastic response="+indexResponse)
    }
}