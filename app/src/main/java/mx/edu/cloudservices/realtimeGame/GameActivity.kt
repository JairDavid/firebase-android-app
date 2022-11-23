package mx.edu.cloudservices.realtimeGame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import mx.edu.cloudservices.databinding.ActivityGameBinding
import mx.edu.cloudservices.realtimechat.MessageModel

class GameActivity : AppCompatActivity() {
    lateinit var binding:ActivityGameBinding
    var gameMovements = mutableListOf<GameModel>()
    var players = mutableListOf<String>()
    var currentPlayer = ""
    var canSelect = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.jugadorX.setOnClickListener {
            setPlayerFirebase("X")
        }

        binding.jugadorO.setOnClickListener {
            setPlayerFirebase("O")
        }





        binding.c1.setOnClickListener {
            sendMovementToFirebase(GameModel(currentPlayer, "c1"))
        }
        binding.c2.setOnClickListener {
            sendMovementToFirebase(GameModel(currentPlayer, "c2"))
        }
        binding.c3.setOnClickListener {
            sendMovementToFirebase(GameModel(currentPlayer, "c3"))
        }
        binding.c4.setOnClickListener {
            sendMovementToFirebase(GameModel(currentPlayer, "c4"))
        }
        binding.c5.setOnClickListener {
            sendMovementToFirebase(GameModel(currentPlayer, "c5"))
        }
        binding.c6.setOnClickListener {
            sendMovementToFirebase(GameModel(currentPlayer, "c6"))
        }
        binding.c7.setOnClickListener {
            sendMovementToFirebase(GameModel(currentPlayer, "c7"))
        }
        binding.c8.setOnClickListener {
            sendMovementToFirebase(GameModel(currentPlayer, "c8"))
        }
        binding.c9.setOnClickListener {
            sendMovementToFirebase(GameModel(currentPlayer, "c9"))
        }

        fetchWhoIam()
        fetchPlayers()
        fetchMovements()
        fetchLastMovement()

    }



    fun setPlayerClient(player:String){
        binding.jugadorX.isEnabled = false
        binding.jugadorO.isEnabled = false
        binding.c1.isEnabled =true
        binding.c2.isEnabled =true
        binding.c3.isEnabled =true
        binding.c4.isEnabled =true
        binding.c5.isEnabled =true
        binding.c6.isEnabled =true
        binding.c7.isEnabled =true
        binding.c8.isEnabled =true
        binding.c9.isEnabled =true
        canSelect = true
        currentPlayer = player
        players.add(player)
    }

    fun setPlayerFirebase(player:String){
        setPlayerClient(player)
        FirebaseDatabase
            .getInstance()
            .getReference("jugadores")
            .child("juego1")
            .setValue(players)
            .addOnSuccessListener {
                Toast.makeText(applicationContext, "Eres el jugador: ${currentPlayer}", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(applicationContext, "Error al seleccionar usuario", Toast.LENGTH_SHORT).show()
            }
    }

    fun fetchPlayers(){
        FirebaseDatabase.getInstance().getReference("jugadores").child("juego1").get()
            .addOnSuccessListener {
                if(it.exists()){
                    for (item in it.children) {
                        val jugador = item.value.toString()
                        players.add(jugador)
                    }
                }
            }
    }

    fun fetchWhoIam(){
            FirebaseDatabase
                .getInstance()
                .getReference("jugadores")
                .child("juego1")
                .limitToLast(1)
                .addChildEventListener(object : ChildEventListener {
                    override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                        val jugador = snapshot.value.toString()
                        if(currentPlayer == "" && jugador =="X"){
                            setPlayerFirebase("O")
                            binding.jugadorX.isEnabled = false
                            binding.jugadorO.isEnabled = false
                            Toast.makeText(applicationContext, "Se te asignó el turno: ${currentPlayer}", Toast.LENGTH_SHORT).show()
                        }

                        if(currentPlayer == "" && jugador =="O"){
                            setPlayerFirebase("X")
                            binding.jugadorX.isEnabled = false
                            binding.jugadorO.isEnabled = false
                            Toast.makeText(applicationContext, "Se te asignó el turno: ${currentPlayer}", Toast.LENGTH_SHORT).show()
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

    fun sendMovementToFirebase(gameModel: GameModel){
        sendMovementToClient(gameModel)
        FirebaseDatabase.getInstance().getReference("movimientos").child("juego1").setValue(gameMovements)
            .addOnSuccessListener {
                Toast.makeText(this, "Elegiste ${gameModel.casilla}", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

    fun sendMovementToClient(gameModel: GameModel){
        gameMovements.add(gameModel)
        when(gameModel.casilla){
            "c1"->{
                binding.c1.text = gameModel.turno
                binding.c1.isEnabled = false
            }
            "c2"->{
                binding.c2.text = gameModel.turno
                binding.c2.isEnabled = false
            }
            "c3"->{
                binding.c3.text = gameModel.turno
                binding.c3.isEnabled = false
            }
            "c4"->{
                binding.c4.text = gameModel.turno
                binding.c4.isEnabled = false
            }
            "c5"->{
                binding.c5.text = gameModel.turno
                binding.c5.isEnabled = false
            }
            "c6"->{
                binding.c6.text = gameModel.turno
                binding.c6.isEnabled = false
            }
            "c7"->{
                binding.c7.text = gameModel.turno
                binding.c7.isEnabled = false
            }
            "c8"->{
                binding.c8.text = gameModel.turno
                binding.c8.isEnabled = false
            }
            "c9"->{
                binding.c9.text = gameModel.turno
                binding.c9.isEnabled = false
            }
            else->{
                Toast.makeText(applicationContext, "No existente", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun fetchMovements(){
      FirebaseDatabase.getInstance().getReference("movimientos").child("juego1").get()
          .addOnSuccessListener {
              if(it.exists()){
                  for (item in it.children) {
                      val casilla = item.child("casilla").value.toString()
                      val turno = item.child("turno").value.toString()
                      gameMovements.add(GameModel(turno, casilla))
                  }
              }
          }
    }

    fun fetchLastMovement(){
        FirebaseDatabase
            .getInstance()
            .getReference("movimientos")
            .child("juego1")
            .limitToLast(1) // Nos manda el ultimo mensaje de la lista de firebase dependiendo el rol
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    val casilla = snapshot.child("casilla").value.toString()
                    val turno = snapshot.child("turno").value.toString()



                    if (turno != currentPlayer){

                        gameMovements.add(GameModel(turno, casilla))
                        when(casilla){
                            "c1"->{
                                binding.c1.isEnabled = false
                                binding.c1.text = turno
                            }
                            "c2"->{
                                binding.c2.isEnabled = false
                                binding.c2.text = turno
                            }
                            "c3"->{
                                binding.c3.isEnabled = false
                                binding.c3.text = turno
                            }
                            "c4"->{
                                binding.c4.isEnabled = false
                                binding.c4.text = turno
                            }
                            "c5"->{
                                binding.c5.isEnabled = false
                                binding.c5.text = turno
                            }
                            "c6"->{
                                binding.c6.isEnabled = false
                                binding.c6.text = turno
                            }
                            "c7"->{
                                binding.c7.isEnabled = false
                                binding.c7.text = turno
                            }
                            "c8"->{
                                binding.c8.isEnabled = false
                                binding.c8.text = turno
                            }
                            "c9"->{
                                binding.c9.isEnabled = false
                                binding.c9.text = turno
                            }
                            else->{
                                Toast.makeText(applicationContext, "No existente", Toast.LENGTH_SHORT).show()
                            }
                        }
                        winner()
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

    fun winner(){
        if(binding.c1.text.equals("X") && binding.c2.text.equals("X") && binding.c3.equals("X")){
            Toast.makeText(applicationContext, "Gano el jugador X!!!!", Toast.LENGTH_SHORT).show()
        }
        if(binding.c4.text.equals("X") && binding.c5.text.equals("X") && binding.c6.equals("X")){
            Toast.makeText(applicationContext, "Gano el jugador X!!!!", Toast.LENGTH_SHORT).show()
        }
        if(binding.c7.text.equals("X") && binding.c8.text.equals("X") && binding.c9.equals("X")){
            Toast.makeText(applicationContext, "Gano el jugador X!!!!", Toast.LENGTH_SHORT).show()
        }


        if(binding.c1.text.equals("X") && binding.c4.text.equals("X") && binding.c7.equals("X")){
            Toast.makeText(applicationContext, "Gano el jugador X!!!!", Toast.LENGTH_SHORT).show()
        }
        if(binding.c2.text.equals("X") && binding.c5.text.equals("X") && binding.c8.equals("X")){
            Toast.makeText(applicationContext, "Gano el jugador X!!!!", Toast.LENGTH_SHORT).show()
        }
        if(binding.c3.text.equals("X") && binding.c6.text.equals("X") && binding.c8.equals("X")){
            Toast.makeText(applicationContext, "Gano el jugador X!!!!", Toast.LENGTH_SHORT).show()
        }

        if(binding.c1.text.equals("X") && binding.c5.text.equals("X") && binding.c9.equals("X")){
            Toast.makeText(applicationContext, "Gano el jugador X!!!!", Toast.LENGTH_SHORT).show()
        }
        if(binding.c3.text.equals("X") && binding.c5.text.equals("X") && binding.c7.equals("X")){
            Toast.makeText(applicationContext, "Gano el jugador X!!!!", Toast.LENGTH_SHORT).show()
        }


    }

    fun resetGame(){
        FirebaseDatabase.getInstance().getReference("").removeValue()
    }

}