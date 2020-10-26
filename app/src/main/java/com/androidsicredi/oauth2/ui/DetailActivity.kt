package com.androidsicredi.oauth2.ui

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.androidsicredi.oauth2.R
import com.androidsicredi.oauth2.model.Event
import com.androidsicredi.oauth2.model.helper.ConstantsEvents
import com.androidsicredi.oauth2.model.helper.Utils.date
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {

    private var mEventPhoto: ImageView? = null
    private var mName: TextView? = null
    private var mTxtDate: TextView? = null

    private var mDescription: TextView? = null
    private var mDetailPrice: TextView? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)


        val intent = intent
        val event = intent.getSerializableExtra(ConstantsEvents.REFERENCE.EVENT) as Event


        configViews()

        val urlPhoto: String? = event.image
        Picasso.with(applicationContext).load(urlPhoto).into(mEventPhoto)

        mName!!.text = event.title
        mTxtDate!!.text = date(event.date)
        mDetailPrice!!.text = String.format("R$%.2f", event.price)
        mDescription!!.text = event.description.toString()



    }



    private fun configViews() {

        mEventPhoto = findViewById<View>(R.id.eventPhoto) as ImageView
        mName = findViewById<View>(R.id.eventDetailTitle) as TextView
        mTxtDate = findViewById<View>(R.id.txtDate) as TextView

        mDetailPrice = findViewById<View>(R.id.eventDetailPrice) as TextView
        mDescription = findViewById<View>(R.id.txtDescription) as TextView


    }
}