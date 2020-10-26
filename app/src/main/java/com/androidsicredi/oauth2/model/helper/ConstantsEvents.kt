package com.androidsicredi.oauth2.model.helper

class ConstantsEvents {


    object DATABASE {
        const val DB_NAME = "events.db"
        const val DB_VERSION = 1

        //events
        const val TABLE_NAME = "event"
        const val DROP_QUERY = "DROP TABLE IF EXIST $TABLE_NAME"
        const val GET_EVENT_QUERY = "SELECT * FROM $TABLE_NAME"
        const val ID = "id"
        const val TITLE = "title"
        const val DESCRIPTION = "description"
        const val PRICE = "price"
        const val DATE = "date"
        const val IMAGE = "image"
        const val PHOTO = "photo"
        const val CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + "" +
                "(" + ID + " INTEGER PRIMARY KEY ," +
                TITLE + " TEXT , " +
                DESCRIPTION + " TEXT , " +
                PRICE + " TEXT , " +
                DATE + " NUMERIC , " +
                IMAGE + " TEXT ," +
                PHOTO + " blob )"



    }

    object REFERENCE {
        const val EVENT = Config.PACKAGE_NAME + "Event"
    }

    object Config {
        const val PACKAGE_NAME = "com.androidsicredi.oauth2.model."
    }
}