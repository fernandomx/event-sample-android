package com.androidsicredi.oauth2.model.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.androidsicredi.oauth2.R
import com.androidsicredi.oauth2.model.Event
import com.androidsicredi.oauth2.model.helper.Utils.date
import com.squareup.picasso.Picasso
import java.time.Instant
import java.time.ZoneId
import java.util.*

class EventAdapter(listener: EventClickListener) : RecyclerView.Adapter<EventAdapter.Holder>() {
    private val mListener: EventClickListener
    private val mEvents: MutableList<Event>



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val row = LayoutInflater.from(parent.context).inflate(R.layout.row_item, null, false)
        return Holder(row)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val currEvent = mEvents[position]

        holder.mDate.text = date(currEvent.date)
        val urlPhoto: String? = currEvent.image
        holder.mName.text = currEvent.title
        holder.mPrice.text = String.format("R$%.2f", currEvent.price)
        Picasso.with(holder.itemView.context).load(urlPhoto).into(holder.mPhoto)


    }

    override fun getItemCount(): Int {
        return mEvents.size

    }

    fun addEvents(events: Event) {
        mEvents.add(events)
        notifyDataSetChanged()
    }



    /**
     * @param position
     * @return
     */

    fun getSelectedEvents(position: Int): Event {
        return mEvents[position]
    }



    fun reset() {
        mEvents.clear()
        notifyDataSetChanged()
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val mPhoto: ImageView
        val mDate: TextView
        val mPrice: TextView
        val mName: TextView

        override fun onClick(v: View) {
            mListener.onClick(layoutPosition)
        }

        init {
            mPhoto = itemView.findViewById<View>(R.id.eventPhoto) as ImageView
            mDate = itemView.findViewById<View>(R.id.eventDate) as TextView
            mPrice = itemView.findViewById<View>(R.id.eventPrice) as TextView
            mName = itemView.findViewById<View>(R.id.eventTitle) as TextView



            itemView.setOnClickListener(this)
        }
    }

    interface EventClickListener {
        fun onClick(position: Int)
    }

    companion object {
        private val TAG = EventAdapter::class.java.simpleName
    }

    init {
        mEvents = ArrayList()
        mListener = listener
    }
}