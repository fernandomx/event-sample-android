package com.androidsicredi.oauth2.model.database

import android.content.ContentValues
import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.androidsicredi.oauth2.model.Event
import com.androidsicredi.oauth2.model.callback.EventFetchListener
//import com.androiddesdecero.oauth2.model.callback.GitFetchListener
import com.androidsicredi.oauth2.model.helper.ConstantsEvents
import com.androidsicredi.oauth2.model.helper.Utils
import java.util.*

class EventDatabase(context: Context?) : SQLiteOpenHelper(context, ConstantsEvents.DATABASE.DB_NAME, null, ConstantsEvents.DATABASE.DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        try {
            db.execSQL(ConstantsEvents.DATABASE.CREATE_TABLE_QUERY)
        } catch (ex: SQLException) {
            Log.d(TAG, ex.message)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(ConstantsEvents.DATABASE.DROP_QUERY)
        onCreate(db)
    }

    fun addEvents(events: Event) {
        Log.d(TAG, "Values Got " + events.title)

        val db = this.writableDatabase
        val values = ContentValues()
        values.put(ConstantsEvents.DATABASE.ID, events.id)
        values.put(ConstantsEvents.DATABASE.TITLE, events.title)
        values.put(ConstantsEvents.DATABASE.DESCRIPTION, events.description)
        values.put(ConstantsEvents.DATABASE.PRICE, events.price)

        values.put(ConstantsEvents.DATABASE.IMAGE, events.image)
        values.put(ConstantsEvents.DATABASE.PHOTO, Utils.getPictureByteOfArray(events?.picture!!,events?.image!! ))
        //val mDate = Date(events.date?.times(1000L))
        //values.put(ConstantsEvents.DATABASE.DATE, mDate.time)
        values.put(ConstantsEvents.DATABASE.DATE, events.date)

        try {
            db.insert(ConstantsEvents.DATABASE.TABLE_NAME, null, values)

        } catch (e: Exception) {
        }
        db.close()
    }




    fun fetchEvents(listener: EventFetchListener) {
        val fetcher = EventFetcher(listener, this.writableDatabase)
        fetcher.start()
    }

    inner class EventFetcher(private val mListener: EventFetchListener, private val mDb: SQLiteDatabase) : Thread() {
        override fun run() {
            val cursor = mDb.rawQuery(ConstantsEvents.DATABASE.GET_EVENT_QUERY, null)
            val eventList: MutableList<Event> = ArrayList()
            if (cursor.count > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        val event = Event()
                        event.id = cursor.getString(cursor.getColumnIndex(ConstantsEvents.DATABASE.ID)).toString()
                        event.title = cursor.getString(cursor.getColumnIndex(ConstantsEvents.DATABASE.TITLE))
                        event.description = cursor.getString(cursor.getColumnIndex(ConstantsEvents.DATABASE.DESCRIPTION))
                        event.price = cursor.getString(cursor.getColumnIndex(ConstantsEvents.DATABASE.PRICE)).toDouble()
                        event.date = cursor.getString(cursor.getColumnIndex(ConstantsEvents.DATABASE.DATE)).toLong()

                        event.picture = Utils.getBitmapFromByte(cursor.getBlob(cursor.getColumnIndex(ConstantsEvents.DATABASE.PHOTO)))

                        eventList.add(event)
                        publishEvent(event)
                    } while (cursor.moveToNext())
                }
            }
            val handler = Handler(Looper.getMainLooper())
            handler.post {
                mListener.onDeliverAllEvent(eventList)
                mListener.onHideDialog()
            }
        }

        fun publishEvent(events: Event?) {
            val handler = Handler(Looper.getMainLooper())
            handler.post { mListener.onDeliverEvent(events) }
        }

    }

    companion object {
        private val TAG = EventDatabase::class.java.simpleName
    }
}