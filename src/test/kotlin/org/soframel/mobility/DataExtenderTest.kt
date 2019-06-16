package org.soframel.mobility

import org.json.JSONObject
import org.junit.Test
import org.soframel.mobility.luxparkings.DataExtender
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DataExtenderTest {

    var dataExtender=DataExtender()

    @Test
    fun testCombineLuxembourgSudAandB(){
        val a="{\"total\":811,\"actuel\":768,\"ouvert\":1,\"tendance\":0,\"id\":\"25\",\"complet\":0,\"title\":\"Luxembourg Sud A\",\"coords\":{\"latitude\":49.5775,\"longitude\":6.12833}}"
        val b="{\"total\":560,\"actuel\":551,\"ouvert\":1,\"tendance\":0,\"id\":\"12\",\"complet\":0,\"title\":\"Luxembourg Sud B\",\"coords\":{\"latitude\":0,\"longitude\":0}}"

        val result=dataExtender.combineLuxembourgSudAandB(JSONObject(a), JSONObject(b))
        val expected="{\"total\":1371,\"actuel\":1319,\"ouvert\":1,\"tendance\":0,\"id\":\"25_12\",\"complet\":0,\"title\":\"Luxembourg Sud A+B\",\"coords\":{\"latitude\":49.5775,\"longitude\":6.12833}}"
        val expectedObject=JSONObject(expected)
        assertTrue(expectedObject.similar(result))
    }

}
