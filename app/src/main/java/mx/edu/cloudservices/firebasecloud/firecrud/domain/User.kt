package mx.edu.cloudservices.firebasecloud.firecrud.domain

import java.io.Serializable

class User(
    var id:String,
    var name :String,
    var lastname:String,
    var secondname:String,
    var age:Int,
    var gender:String
):Serializable {
}