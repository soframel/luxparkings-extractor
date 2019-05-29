package org.soframel.mobility.luxparkings;

import java.util.*

object LuxParkingsProperties {

    val propsFilePath="/luxparkings.properties"
    var props= Properties()

    init{
        val stream=this.javaClass.getResourceAsStream(propsFilePath)
        props.load(stream)
    }

    fun getProperty(name: String): String?{
        return props.getProperty(name)
    }
}
