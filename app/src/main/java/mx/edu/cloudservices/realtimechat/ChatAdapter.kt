package mx.edu.cloudservices.realtimechat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import mx.edu.cloudservices.R

class ChatAdapter(context:Context): BaseAdapter() {

    var mensajes = ArrayList<MessageModel>()
    var contextAdapter = context

    fun addMessageToClientList(messageObject:MessageModel){
        mensajes.add(messageObject)
        notifyDataSetChanged()
    }

    override fun getCount(): Int = mensajes.size

    override fun getItem(p0: Int): Any {
        return mensajes[p0]
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
        var viewHolder =MessageViewHolder()
        var myView = view
        var mensajeView = LayoutInflater.from(contextAdapter)
        var mensaje = mensajes[position].mensaje

        //solo hay que modificar la condicion dependiendo para quien es la app
        //caso 1) Conductor
        //caso 2) Pasajero

        if(mensajes[position].usuario.equals("conductor")){
            myView = mensajeView.inflate(R.layout.my_message, null)
            viewHolder.mensaje = myView.findViewById(R.id.my_message)
            viewHolder.mensaje!!.setText(mensaje)
        }else{
            myView = mensajeView.inflate(R.layout.external_message, null)
            viewHolder.mensaje = myView.findViewById(R.id.external_message)
            viewHolder.mensaje!!.setText(mensaje)
        }

        return  myView
    }

    internal class MessageViewHolder(){
        var mensaje: TextView? = null
    }
}