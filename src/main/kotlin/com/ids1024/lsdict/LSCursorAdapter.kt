package com.ids1024.lsdict

import android.content.Context
import android.database.Cursor
import android.widget.CursorAdapter
import android.widget.TextView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.text.Html

public class LSCursorAdapter : CursorAdapter {
    constructor(context: Context, c: Cursor, flags: Int) : super(context, c, flags)
    override public fun newView(context: Context, cursor: Cursor, parent: ViewGroup) : View {
        return LayoutInflater.from(context).inflate(R.layout.result, parent, false)
    }
    override public fun bindView(view: View, context: Context, cursor: Cursor) {
        val tv = view.findViewById(R.id.result) as TextView
        val html = cursor.getString(1)
        tv.text = Html.fromHtml(html)
    }
}
