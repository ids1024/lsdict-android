package com.ids1024.lsdict

import android.content.Context
import android.database.Cursor
import android.widget.TextView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.text.Html
import android.support.v7.widget.RecyclerView

import android.util.Log

class LSSearchAdapter(cursor: Cursor) : RecyclerView.Adapter<LSSearchAdapter.ViewHolder>() {
    val cursor = cursor
	
    override fun getItemCount(): Int {
        Log.w("words", "count: ${cursor.getCount()}")
        return cursor.getCount()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        cursor.moveToPosition(position)
        Log.w("words", "At pos $position: ${cursor.getString(3)}")
        val html = cursor.getString(3)
        holder.text_view.text = Html.fromHtml(html)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.result, null)
        return ViewHolder(view)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val text_view: TextView = view.findViewById(R.id.result_text)
    }
}
