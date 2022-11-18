package mx.edu.cloudservices.realtimechat

data class MessageModel(

    var mensaje: String,
    var usuario:String
){
    companion object{
        val PASAJERO = "pasajero"
        val CONDUCTOR = "conductor"
    }
}