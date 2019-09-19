package fi.metropolia.kari.ar_collectables_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.collection_row.view.*

class CollectionActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection)

        viewManager = LinearLayoutManager(this)
        viewAdapter = MyAdapter()

        recyclerView = findViewById<RecyclerView>(R.id.collection_recycler_view).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

        }
    }
}

class MyAdapter() : RecyclerView.Adapter<CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val element = layoutInflater.inflate(R.layout.collection_row, parent, false)
        return CustomViewHolder(element)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        //first check if discovered or not

        if(DataManager.discovered.values.toList().get(position)){
            //thing is discovered, display normal text and checkmark
            holder.view.collec_name_text_view.text = DataManager.discovered.keys.toList().get(position)
            holder.view.collec_image_view.setImageResource(R.drawable.ic_check_green_24dp)
        }else{
            holder.view.collec_name_text_view.text = "???????"
            holder.view.collec_image_view.setImageResource(R.drawable.ic_check_box_outline_blank_black_30dp)
        }

    }

    override fun getItemCount(): Int {
        return DataManager.discovered.keys.size
    }
}

class CustomViewHolder(val view: View) : RecyclerView.ViewHolder(view)