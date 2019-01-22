package com.nctest.photoviewer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView

import com.nctest.photoviewer.R
import com.nctest.photoviewer.model.Photo
import com.squareup.picasso.Picasso

import java.util.ArrayList

class PhotoAdapter(context: Context, photos: List<Photo>) : BaseAdapter() {

    val data = ArrayList<Photo>()
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    init {
        this.data.addAll(photos)
    }

    fun setData(photos: List<Photo>) {
        this.data.clear()
        this.data.addAll(photos)
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val v: View
        if (convertView == null) {
            v = inflater.inflate(R.layout.photo_cell, null)
        } else {
            v = convertView
        }

        Picasso.get().load(data[position].thumbnail).into(v.findViewById<View>(R.id.thumbnail) as ImageView)

        return v
    }
}
