package mx.edu.cloudservices.firebasecloud.firecrud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import mx.edu.cloudservices.databinding.ActivityDataStoreListBinding
import mx.edu.cloudservices.firebasecloud.firecrud.domain.User
import mx.edu.cloudservices.firebasecloud.firecrud.domain.UserAdapter

class DataStoreListActivity : AppCompatActivity() , UserAdapter.Events{
    lateinit var binding:ActivityDataStoreListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataStoreListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getAll()
    }

    fun getAll(){
        FirebaseFirestore.getInstance()
            .collection("usuarios")
            .get()
            .addOnSuccessListener {
                var response = it.map { fireUser ->
                    User(
                        fireUser.reference.id,
                        fireUser["nombre"].toString(),
                        fireUser["paterno"].toString(),
                        fireUser["materno"].toString(),
                        fireUser["edad"].toString().toInt(),
                        fireUser["genero"].toString()
                    )
                }
                setData(response)

            }
            .addOnFailureListener {
                Toast.makeText(this, "[ERROR]: Ocurrio un error", Toast.LENGTH_SHORT).show()
            }
    }

    fun setData (data:List<User>){
        binding.dataStoreRecycler.layoutManager = LinearLayoutManager(this)
        var adapter = UserAdapter(this, this)
        binding.dataStoreRecycler.adapter = adapter
        adapter?.submitList(data)
        adapter.notifyDataSetChanged()
    }

    override fun onItemUpdate(element: User, index: Int) {

    }

    override fun onItemDelete(element: User, index: Int) {
        FirebaseFirestore.getInstance()
            .collection("usuarios")
            .document(element.id)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(this, "Se ha eliminado: ${element.name}", Toast.LENGTH_SHORT).show()
                getAll()
            }
            .addOnFailureListener {
                Toast.makeText(this, "[Error] no pudo eliminar: ${element.name}", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onItemClick(element: User) {
        startActivity(Intent())
    }
}