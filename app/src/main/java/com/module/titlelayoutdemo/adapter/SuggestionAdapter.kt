package com.module.titlelayoutdemo.adapter

import android.R
import android.content.Context
import android.database.Cursor
import android.database.MatrixCursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CursorAdapter
import android.widget.TextView
import java.util.Locale


class SuggestionsAdapter(context: Context?, adapter: ArrayAdapter<String?>) :
    CursorAdapter(context, null, false) {
    private val suggestions: List<String> = arrayListOf()

    init {
        val count = adapter.count
        for (i in 0 until count){
            suggestions.plus(adapter.getItem(i))
        }
    }

    override fun newView(context: Context, cursor: Cursor, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(context)
        return inflater.inflate(R.layout.simple_dropdown_item_1line, parent, false)
    }

    override fun bindView(view: View, context: Context, cursor: Cursor) {
        val textView = view.findViewById<TextView>(R.id.text1)
        textView.text = cursor.getString(cursor.getColumnIndexOrThrow("suggestion"))
    }

    override fun runQueryOnBackgroundThread(constraint: CharSequence): MatrixCursor? {
        if (constraint != null) {
            val query = constraint.toString().lowercase(Locale.getDefault())
            val cursor = MatrixCursor(arrayOf("_id", "suggestion"))
            var id = 0
            for (suggestion in suggestions) {
                if (suggestion.lowercase(Locale.getDefault()).contains(query)) {
                    cursor.addRow(arrayOf<Any>(id++, suggestion))
                }
            }
            return cursor
        }
        return null
    }
}
