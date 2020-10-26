package com.androidsicredi.oauth2.ui

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidsicredi.oauth2.R
import com.androidsicredi.oauth2.api.WebServiceOAuth
import com.androidsicredi.oauth2.api.WebServiceOAuthApi
import com.androidsicredi.oauth2.model.Event
import com.androidsicredi.oauth2.model.adapter.EventAdapter
//import com.androiddesdecero.oauth2.model.adapter.GitAdapter
import com.androidsicredi.oauth2.model.callback.EventFetchListener
//import com.androiddesdecero.oauth2.model.callback.GitFetchListener
import com.androidsicredi.oauth2.model.database.EventDatabase
import com.androidsicredi.oauth2.model.helper.ConstantsEvents
import com.androidsicredi.oauth2.model.helper.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URL

class EventActivity: AppCompatActivity(), EventAdapter.EventClickListener, EventFetchListener {

    private var mRecyclerView: RecyclerView? = null

    private var mEventAdapter: EventAdapter? = null
    private var mDatabase: EventDatabase? = null

    private var mDialog: ProgressDialog? = null

    private var activity: Activity? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        configViews()

        mDatabase = EventDatabase(this)

        loadEventsFeed()


        activity = this


    }


    private fun configViews() {


        mRecyclerView = findViewById<View>(R.id.recyclerView) as RecyclerView
        mRecyclerView!!.setHasFixedSize(true)
        mRecyclerView!!.setRecycledViewPool(RecyclerView.RecycledViewPool())
        mRecyclerView!!.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)

        mEventAdapter = EventAdapter(this)

        mRecyclerView!!.adapter = mEventAdapter

    }

    val networkAvailability: Boolean
        get() = Utils.isNetworkAvailable(applicationContext)

    private fun loadEventsFeed() {

        mDialog = ProgressDialog(this@EventActivity)
        mDialog!!.setMessage("Loading Event Data...")
        mDialog!!.setCancelable(true)
        mDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        mDialog!!.isIndeterminate = true
        mEventAdapter!!.reset()
        mDialog!!.show()
        if (networkAvailability) {

            feedEvents
        } else {
            feedFromDatabase
        }
    }


    val feedEvents: Unit
        get() {

            val call = WebServiceOAuth.instance
                    ?.createService(WebServiceOAuthApi::class.java)
                    ?.getListEvent()

            call!!.enqueue(object : Callback<List<Event?>> {
                override fun onFailure(call: Call<List<Event?>>, t: Throwable) {


                    mDialog!!.dismiss()
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()

                }

                override fun onResponse(call: Call<List<Event?>>, response: Response<List<Event?>>) {

                    try {

                        if (response.isSuccessful) {

                            Log.d("EVENTS_OK", "EVENTS_OK")


                            val eventList = response.body()!!
                            var event: Event? = null

                            for (i in eventList.indices) {


                                event = eventList[i]

                                Log.i("EVENT", "EVENT: " + event?.id + "-" + event?.title + "-" + event?.date)

                                val task = SaveIntoDatabase()
                                task.execute(event)


                                mEventAdapter!!.addEvents(event!!)

                            }



                        } else {
                            val sc = response.code()
                            when (sc) {
                                400 -> Log.e("Error 400", "Bad Request")
                                404 -> Log.e("Error 404", "Not Found")
                                else -> Log.e("Error", "Generic Error")
                            }
                        }

                        mDialog!!.dismiss()

                    } catch (e: Exception) {
                        Log.e("ERROR", "ERRO: " + e.message.toString())
                    }




                }
            })

        }



    inner class SaveIntoDatabase : AsyncTask<Event?, Void?, Void?>() {
        private val TAG = SaveIntoDatabase::class.java.simpleName
        override fun onPreExecute() {
            super.onPreExecute()
        }

        protected override fun doInBackground(vararg params: Event?): Void? {
            val event = params[0]
            try {
                val urlPhoto = event?.image
                val stream = URL(urlPhoto).openStream()
                val bitmap = BitmapFactory.decodeStream(stream)

                event?.picture = bitmap
                event?.let { mDatabase!!.addEvents(it) }
            } catch (e: Exception) {
                //Log.d(TAG, e.message)
            }
            return null
        }
    }

    private val feedFromDatabase: Unit
        private get() {
            mDatabase!!.fetchEvents(this)
        }



    override fun onClick(position: Int) {
        val selectedEvent = mEventAdapter!!.getSelectedEvents(position)
        val intent = Intent(this@EventActivity, DetailActivity::class.java)
        intent.putExtra(ConstantsEvents.REFERENCE.EVENT, selectedEvent)
        startActivity(intent)
    }


    override fun onDeliverAllEvent(events: List<Event?>?) {}

    override fun onDeliverEvent(events: Event?) {


        mEventAdapter!!.addEvents(events!!)

    }

    override fun onHideDialog() {
        mDialog!!.dismiss()
    }
}