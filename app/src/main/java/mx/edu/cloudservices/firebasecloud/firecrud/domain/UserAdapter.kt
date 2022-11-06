package mx.edu.cloudservices.firebasecloud.firecrud.domain

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import mx.edu.cloudservices.databinding.ItemDatastoreBinding

class UserAdapter(private val events: UserAdapter.Events, contex:Context):ListAdapter<User, UserAdapter.ViewHolder>(DiffUtilCallback){

    private val adapterContext = contex

    interface Events{
        fun onItemUpdate(element:User, index:Int)
        fun onItemDelete(element:User, index:Int)
        fun onItemClick(element: User)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var item = ItemDatastoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(item)
    }

    override fun onBindViewHolder(holder: UserAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    inner class ViewHolder (private val binding: ItemDatastoreBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item: User, position: Int) {

            binding.itemName.text = item.name
            binding.itemDeleteBtn.setOnClickListener {
                this@UserAdapter.events.onItemDelete(item, position)
            }
            binding.itemName.setOnClickListener{
                this@UserAdapter.events.onItemClick(item)
            }
        }
    }

    private object DiffUtilCallback: DiffUtil.ItemCallback<User>() {
        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return  true
        }

        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return newItem == oldItem
        }
    }

}