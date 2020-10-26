package com.androidsicredi.oauth2.model.callback

import com.androidsicredi.oauth2.model.Event


interface EventFetchListener {


    fun onDeliverAllEvent(events: List<Event?>?)
    fun onDeliverEvent(events: Event?)
    fun onHideDialog()

}