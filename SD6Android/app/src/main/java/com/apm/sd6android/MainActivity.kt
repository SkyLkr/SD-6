package com.apm.sd6android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val mqttConnection = MQTTConnection()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        connectButton.setOnClickListener(View.OnClickListener {
            val ip = ipText.text.toString()

            mqttConnection.connect(this, ip) {
                if (it) {
                    Toast.makeText(this, "Conectado ao broker com sucesso", Toast.LENGTH_SHORT).show();
                    val intent = Intent(this, Main2Activity::class.java);
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Erro na conexão ao broker", Toast.LENGTH_SHORT).show();
                }
            }
        })
    }
}
