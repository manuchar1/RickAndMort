package com.example.rickandmort.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickandmort.data.models.Character
import com.example.rickandmort.databinding.CharactersRowLayoutBinding


class CharactersAdapter : RecyclerView.Adapter<CharactersAdapter.CharacterViewHolder>() {

    inner class CharacterViewHolder(private val binding: CharactersRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val character = differ.currentList[absoluteAdapterPosition]

            itemView.apply {
                Glide.with(this).load(character.image).into(binding.ivCharacter)
                binding.tvName.text = character.name
                binding.tvLocationText.text = character.location?.name
                binding.tvStatus.text = character.status
               /* if(binding.tvStatus.text =="alive"){

                }*/


                setOnClickListener {
                    onItemClickListener?.let { it(character) }
                }

            }
        }
    }


    private val differCallback = object : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(
            CharactersRowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind()

    }

    private var onItemClickListener: ((Character) -> Unit)? = null

    fun setOnItemClickListener(listener: (Character) -> Unit) {
        onItemClickListener = listener

    }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }


}