package com.example.moviesphere.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesphere.R
import com.example.moviesphere.beans.response.Person

class PersonAdapter() :
    RecyclerView.Adapter<PersonAdapter.PersonViewHolder>() {

    private val people = mutableListOf<Person>()

    fun submitList(newList: List<Person>) {
        people.clear()
        people.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_person, parent, false)
        return PersonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.bind(people[position])
    }

    override fun getItemCount(): Int = people.size

    class PersonViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(person: Person) {
            itemView.apply {
                findViewById<TextView>(R.id.name).text = person.name
                findViewById<TextView>(R.id.department).text = person.known_for_department
                Glide.with(context).load("https://image.tmdb.org/t/p/w500${person.profile_path}")
                    .into(findViewById(R.id.profile_image))
            }
        }
    }
}