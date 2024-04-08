package com.example.finalproject_androidstudio.activities;

public class SitterAdapter(private val sitterList: List<Sitter>, private val listener: OnItemClickListener) : RecyclerView.Adapter<SitterAdapter.ViewHolder>() {

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Initialize views from item_sitter.xml
        }

interface OnItemClickListener {
    fun onItemClick(sitter: Sitter)
}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_sitter, parent, false)
        return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sitter = sitterList[position]

        // Bind data to views in the ViewHolder
        // You can use a library like Picasso or Glide to load images from URL
        // holder.imageView.loadImage(sitter.photoUrl)
        // holder.ratingBar.rating = sitter.rating.toFloat()

        holder.itemView.setOnClickListener {
        listener.onItemClick(sitter)
        }
        }

        override fun getItemCount(): Int {
        return sitterList.size
        }
        }
