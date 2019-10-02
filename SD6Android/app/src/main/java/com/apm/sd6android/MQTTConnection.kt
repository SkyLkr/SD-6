package com.apm.sd6android

import android.content.Context
import android.util.Log
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttException
import org.eclipse.paho.client.mqttv3.MqttMessage
import java.lang.Exception

class MQTTConnection {
    private lateinit var mqttAndroidClient: MqttAndroidClient

    fun connect(context: Context, brokerIp: String, callback: (success: Boolean) -> Unit) {
        mqttAndroidClient = MqttAndroidClient(context.applicationContext, "tcp://$brokerIp:1883", "AndroidClient")

        try {
            val token = mqttAndroidClient.connect()
            token.actionCallback = object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    callback(true)
                    Log.i("Connection", "sucess ")
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    callback(false)
                    Log.i("Connection", "faliure ")
                    exception?.printStackTrace()
                }
            }
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    fun subscribe(topic: String) {
        val qos = 2
        try {
            mqttAndroidClient.subscribe(topic, qos, null, object: IMqttActionListener{
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    exception?.printStackTrace()
                }
            })
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    fun unSubscribe(topic: String) {
        try {
            val unsubToken = mqttAndroidClient.unsubscribe(topic)
            unsubToken.actionCallback = object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    exception?.printStackTrace()
                }
            }
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    fun publish(topic: String, data: String) {
        val encodedPayload: ByteArray

        try {
            encodedPayload = data.toByteArray(charset("UTF-8"))
            val message = MqttMessage(encodedPayload)
            message.qos = 2
            message.isRetained = false
            mqttAndroidClient.publish(topic, message)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}