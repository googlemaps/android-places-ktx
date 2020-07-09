package com.google.places.android.ktx.demo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val listView = ListView(this).also {
            it.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            it.adapter = DemoAdapter(this, Demo.values())
            it.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->
                val demo = parent.adapter.getItem(position) as? Demo
                demo?.let {
                    startActivity(Intent(this, demo.activity))
                }
            }
        }
        setContentView(listView)
    }

    private class DemoAdapter(context: Context, demos: Array<Demo>) :
        ArrayAdapter<Demo>(context, R.layout.item_demo, demos) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val demoView = (convertView as? DemoItemView) ?: DemoItemView(context)
            return demoView.also {
                val demo = getItem(position)
                it.title.setText(demo?.title ?: 0)
                it.description.setText(demo?.description ?: 0)
            }
        }
    }

    private class DemoItemView(context: Context) : LinearLayout(context) {

        val title: TextView by lazy { findViewById<TextView>(R.id.textViewTitle) }

        val description: TextView by lazy { findViewById<TextView>(R.id.textViewDescription) }

        init {
            LayoutInflater.from(context)
                .inflate(R.layout.item_demo, this)
        }
    }
}