package mx.edu.cloudservices.practiceAuth

import android.content.Context

class SharedStorage(context:Context) {
val STORAGE_DOC = "mx.edu.cloudservices"
    val EMAIL_FIELD  = "email"

    var storage = context.getSharedPreferences(STORAGE_DOC, 0)

    fun saveUser(email:String){
        storage.edit().putString(EMAIL_FIELD, email).apply()
    }

    fun getSessionEmail():String{
        return storage.getString(EMAIL_FIELD, "")!!
    }

    fun deleteSession(){
        storage.edit().clear().apply()
    }
}