package com.example.progressbarprac

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fun  View.hideKeyboard(){
            val inputManager=context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(windowToken,0)
        }

        //animation
        val animone= AnimationUtils.loadAnimation(this,R.anim.translation)
        val animedittext=AnimationUtils.loadAnimation(this,R.anim.stb)
        val animbtn=AnimationUtils.loadAnimation(this,R.anim.translationbtn1)
        val animbtn2=AnimationUtils.loadAnimation(this,R.anim.translationbtn2)
        val animbtn3=AnimationUtils.loadAnimation(this,R.anim.translationbtn3)
        val header= findViewById<TextView>(R.id.textView2)
        val text2= findViewById<TextView>(R.id.textView3)
        val text3= findViewById<TextView>(R.id.textView4)
        val text4= findViewById<TextView>(R.id.textView5)
        val text5= findViewById<TextView>(R.id.textView6)
        header.startAnimation(animone)
        text3.startAnimation(animone)
        text4.startAnimation(animone)
        text5.startAnimation(animone)
        //animation Edittext
        val edit1=findViewById<EditText>(R.id.editText)
        val edit2=findViewById<EditText>(R.id.editText3)
        val edit3=findViewById<EditText>(R.id.editText4)
        val edit4=findViewById<EditText>(R.id.editText5)
        edit1.startAnimation(animedittext)
        edit2.startAnimation(animedittext)
        edit3.startAnimation(animedittext)
        edit4.startAnimation(animedittext)
        //animate btn
        val btn1=findViewById<Button>(R.id.button1)
        val btn2=findViewById<Button>(R.id.button2)
        val btn3=findViewById<Button>(R.id.btnsave)
        btn1.startAnimation(animbtn)
        btn2.startAnimation(animbtn2)
        btn3.startAnimation(animbtn3)
        NetworkTask(this).execute()
        //val name=editText.text.toString()
        //val plate=editText4.text.toString()
        //val phone=Integer.parseInt(editText3.text.toString())
        //val time=Integer.parseInt(editText5.text.toString())
        button2.setOnClickListener {
            val builder=AlertDialog.Builder(this)
            builder.setTitle("Alert")
            builder.setMessage("Do you want to cancel and exit")
            builder.setPositiveButton("yes"){
                dialog, which ->
                val intent=intent
                finish()
            }
            builder.setNegativeButton("No"){
                dialog, which ->
                Toast.makeText(applicationContext,"canceling..",Toast.LENGTH_SHORT).show()
            }
            val AlertDialog:AlertDialog=builder.create()
            AlertDialog.setCancelable(true)
            AlertDialog.show()
        }
        btnsave.setOnClickListener {
            val intent=Intent(applicationContext,MapsActivity::class.java)
            startActivity(intent)
        }

        button1.setOnClickListener {
            savedata()
            val intent= Intent(this,secondactivity::class.java)


            intent.putExtra("time",editText5.text.toString())
            intent.putExtra("client",editText.text.toString())
            intent.putExtra("Plates",editText4.text.toString())
            intent.putExtra("Phone",editText3.text.toString())

            startActivity(intent)
        }

    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus!=null){
            val imm=getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken,0)
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun savedata() {
        val name=editText.text.toString()
        val plate=editText4.text.toString()
       val phone=editText3.text.toString()
        val time=editText5.text.toString()
        val ref = FirebaseDatabase.getInstance().getReference("PARKINGDATA")
        val carid=ref.push().key
        //val data= Parkdata(carid,Name,Plate,Phone,time)
        val data=Parkdata(carid,name,phone,plate,time)
        if (carid!=null){
            ref.child(carid).setValue(data).addOnCompleteListener{
            Toast.makeText(this,"Data has been send reciept being processed",Toast.LENGTH_LONG).show()
                val name=editText.text.clear()
                val plate=editText4.text.clear()
                val phone=editText3.text.clear()
                val time=editText5.text.clear()
                finish()
            }
        }else{
            Toast.makeText(applicationContext,"DATA NOT SAVED",Toast.LENGTH_LONG).show()
        }
    }

    class NetworkTask(var activity:MainActivity): AsyncTask<Void, Void, Void>() {
        var dialog = Dialog(activity,android.R.style.Theme_Translucent_NoTitleBar)
        override fun onPreExecute() {
            val view=activity.layoutInflater.inflate(R.layout.progressphase,null)
            dialog.setContentView(view )
            dialog.setCancelable(false)
            dialog.show()
            super.onPreExecute()
        }
        override fun doInBackground(vararg params: Void?): Void? {
            Thread.sleep(2000)
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            dialog.dismiss()
        }
    }


}