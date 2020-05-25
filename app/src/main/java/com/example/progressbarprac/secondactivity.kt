package com.example.progressbarprac

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_secondactivity.*

class secondactivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_secondactivity)
        val info:Bundle?=intent.extras
        val Name=info!!.getString("client")
        val Plates=info!!.getString("Plates")
        val Phone=info!!.getString("Phone")
        val Time=info!!.getString("time")
        showname.text=Name
        showplates.text=Plates
        showcost.text=Time+" dollars"
        showphone.text=Phone

btnexit.setOnClickListener {
    val intent = Intent(this,MainActivity::class.java)
    finish()
}


    }


}
