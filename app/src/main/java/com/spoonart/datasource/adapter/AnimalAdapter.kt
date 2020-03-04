package com.spoonart.datasource.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.spoonart.datasource.R
import com.spoonart.datasource.model.Animal
import com.spoonart.datasource.source.AnimalDataSource
import kotlinx.android.synthetic.main.adapter_animal.view.*

class AnimalAdapter : PagedListAdapter<Animal, RecyclerView.ViewHolder>(diffCallback) {

    private val viewTypeLoading = 0x12
    private val viewTypeAnimal = 1
    private var state: AnimalDataSource.State? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == viewTypeAnimal) {
            ViewHolder(inflater.inflate(R.layout.adapter_animal, parent, false))
        } else {
            LoadingViewHolder(inflater.inflate(R.layout.adapter_loading, parent, false))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1){
            viewTypeLoading
        }else{
            viewTypeAnimal
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    private fun hasExtraRow(): Boolean {
        return state != null && state!! != AnimalDataSource.State.FINISH
    }

    fun setState(newState: AnimalDataSource.State) {
        val previousState = this.state
        val hadExtraRow = hasExtraRow()
        this.state = newState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount() + 1)
            } else {
                notifyItemInserted(super.getItemCount() + 1)
            }
        } else if (hasExtraRow && previousState != newState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            getItem(position)?.let {
                holder.bind(it)
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val label: TextView = view.tvAnimal

        @SuppressLint("SetTextI18n")
        fun bind(animal: Animal) {
            label.text = "${animal.name} - ${animal.animalCode}"
        }
    }

    inner class LoadingViewHolder(view: View) : RecyclerView.ViewHolder(view)

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Animal>() {
            override fun areItemsTheSame(oldItem: Animal, newItem: Animal): Boolean {
                return oldItem.animalCode == newItem.animalCode
            }

            override fun areContentsTheSame(oldItem: Animal, newItem: Animal): Boolean {
                return oldItem == newItem
            }

        }
    }
}