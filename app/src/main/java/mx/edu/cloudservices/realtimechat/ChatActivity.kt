package mx.edu.cloudservices.realtimechat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.widget.Toast
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import mx.edu.cloudservices.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {
    lateinit var binding: ActivityChatBinding
    lateinit var adapter: ChatAdapter
    var messageListClient = mutableListOf<MessageModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnEnviar.setOnClickListener {
            sendMessageToRealtimeFirebase(MessageModel(binding.txtMensaje.text.toString(),MessageModel.CONDUCTOR))
            binding.txtMensaje.text.clear()
        }

        fetchLastMessage()
        setData()
    }

    fun setData(){
        adapter = ChatAdapter(this)
        binding.lvMensajes.adapter = adapter
        adapter.notifyDataSetChanged()
        fetchMessages()
    }

    fun fetchLastMessage(){
        FirebaseDatabase
            .getInstance()
            .getReference("mensajes")
            .child("viaje1")
            .limitToLast(1) // Nos manda el ultimo mensaje de la lista de firebase dependiendo el rol
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    var mensaje = snapshot.child("mensaje").value.toString()
                    var usuario = snapshot.child("usuario").value.toString()

                    //caso 1) para la vista del conductor, debe ver solo sus mensajes en pantalla y cambiar dentro del adapter
                    //caso 2) para la vista del pasajero, debe ver solo sus mensajes en pantalla y cambiar dentro del adapter
                    if (usuario != "conductor"){
                        sendMessageToClientList(MessageModel(mensaje, usuario))
                    }
                }
                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
    }

    //Cargamos los mensajes a la lista de mensajes del lado del cliente
    fun fetchMessages(){
        FirebaseDatabase.getInstance().getReference("mensajes").child("viaje1").get()
            .addOnSuccessListener {
                if(it.exists()){
                    for (item in it.children) {
                        val mensaje = item.child("mensaje").value.toString()
                        val usuario = item.child("usuario").value.toString()
                        sendMessageToClientList(MessageModel(mensaje, usuario))
                    }
                }
            }
    }

    //Enviamos el mensaje a la lista del lado del cliente y al adapter para que pínte los datos conforme al rol
    fun sendMessageToClientList(message:MessageModel){
        adapter.addMessageToClientList(message)
        messageListClient.add(message)
        adapter.notifyDataSetChanged()
        binding.lvMensajes.setSelection(adapter.count-1)
    }

    //Enviamos el mensaje a firebase y al otro metodo para procesarlo
    fun sendMessageToRealtimeFirebase(message: MessageModel){
        sendMessageToClientList(message)
        FirebaseDatabase.getInstance().getReference("mensajes").child("viaje1").setValue(messageListClient)
            .addOnSuccessListener {
                Toast.makeText(this, "Mensaje enviado", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Erros ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }
}