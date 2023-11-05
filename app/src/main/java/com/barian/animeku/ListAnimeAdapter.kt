package com.barian.animeku

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.barian.animeku.databinding.ItemRowAnimeBinding
import com.bumptech.glide.Glide

class ListAnimeAdapter(private val listAnime: ArrayList<Anime>):
    RecyclerView.Adapter<ListAnimeAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(var binding: ItemRowAnimeBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowAnimeBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listAnime.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (title, poster, rating, status, release) = listAnime[position]
        holder.binding.tvAnimeTitle.text = title
        holder.binding.tvRating.text = rating.toString()
        Glide.with(holder.itemView.context)
            .load(poster)
            .into(holder.binding.imgPoster)
        holder.binding.tvAnimeStatus.text = status
        holder.binding.tvAnimeRelease.text = release
        holder.itemView.setOnClickListener{ onItemClickCallback.onItemClicked(listAnime[position]) }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Anime)
    }
}