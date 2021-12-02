package app.christopher.cakeslistapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import app.christopher.cakeslistapp.OnItemClickListener
import app.christopher.cakeslistapp.model.CakesModel
import cakeslistapp.databinding.ItemCakesBinding
import com.bumptech.glide.Glide

class CakesAdapter(private val listener: OnItemClickListener) : RecyclerView.Adapter<CakesAdapter.CakesViewHolder>() {


    inner class CakesViewHolder(var binding: ItemCakesBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener{

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION){
                listener.onItemClick(position)
            }
        }
    }
        private val diffCallback = object : DiffUtil.ItemCallback<CakesModel>() {
            override fun areItemsTheSame(oldItem: CakesModel, newItem: CakesModel): Boolean {
                return oldItem.title == newItem.title
            }
            override fun areContentsTheSame(oldItem: CakesModel, newItem: CakesModel): Boolean {
                return oldItem == newItem
            }
        }

    private val differ = AsyncListDiffer(this, diffCallback)
    var cakesModels: List<CakesModel>
        get() = differ.currentList
        set(value) {differ.submitList(value) }

    //Returns the size of our List
    override fun getItemCount() : Int = cakesModels.size

    override fun onBindViewHolder(holder: CakesViewHolder, position: Int) {
        holder.binding.apply {
            val cakes = cakesModels[position]
            cakeTitle.text = cakes.title
            cakeDescription.text = cakes.desc
            Glide.with(holder.itemView).load(cakes.image).into(cakeImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CakesViewHolder {
        return CakesViewHolder(ItemCakesBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ))
    }

}