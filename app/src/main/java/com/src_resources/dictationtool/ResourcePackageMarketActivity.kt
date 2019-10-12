package com.src_resources.dictationtool

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class ResourcePackageMarketActivity : AppCompatActivity() {

    private class MyAdapter(val outerClassObj: ResourcePackageMarketActivity, val titleData: List<String>,
                            val introductionData: List<String>, val btnDownloadOnClickCallback: (Int, View) -> Unit)
            : BaseAdapter() {

        private val mInflater = LayoutInflater.from(outerClassObj)

        /**
         * Get a View that displays the data at the specified position in the data set. You can either
         * create a View manually or inflate it from an XML layout file. When the View is inflated, the
         * parent View (GridView, ListView...) will apply default layout parameters unless you use
         * [android.view.LayoutInflater.inflate]
         * to specify a root view and to prevent attachment to the root.
         *
         * @param position The position of the item within the adapter's data set of the item whose view
         * we want.
         * @param convertView The old view to reuse, if possible. Note: You should check that this view
         * is non-null and of an appropriate type before using. If it is not possible to convert
         * this view to display the correct data, this method can create a new view.
         * Heterogeneous lists can specify their number of view types, so that this View is
         * always of the right type (see [.getViewTypeCount] and
         * [.getItemViewType]).
         * @param parent The parent that this view will eventually be attached to
         * @return A View corresponding to the data at the specified position.
         */
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
            var itemView = convertView
            if (itemView == null) {
                itemView = mInflater.inflate(R.layout.adapter__simple, null) as View
            }
            val tvContent = itemView.findViewById<TextView>(R.id.tvContent)
            tvContent.text = titleData[position]
            val tvIntroduction = itemView.findViewById<TextView>(R.id.tvIntroduction)
            tvIntroduction.text = introductionData[position]
            val btnDownload = itemView.findViewById<Button>(R.id.btnDownload)
            btnDownload.setOnClickListener {
                btnDownloadOnClickCallback(position, it)
            }

            return itemView
        }

        /**
         * Get the data item associated with the specified position in the data set.
         *
         * @param position Position of the item whose data we want within the adapter's
         * data set.
         * @return The data at the specified position.
         */
        override fun getItem(position: Int): Any? {
            return null
        }

        /**
         * Get the row id associated with the specified position in the list.
         *
         * @param position The position of the item within the adapter's data set whose row id we want.
         * @return The id of the item at the specified position.
         */
        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        /**
         * How many items are in the data set represented by this Adapter.
         *
         * @return Count of items.
         */
        override fun getCount(): Int {
            return titleData.size
        }
    }

    private lateinit var lvMarket: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resource_package_market)

        lvMarket = findViewById(R.id.lvMarket)
//        val adapter = ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, getData())
        val adapter = MyAdapter(this, getTitleData(), getIntroductionData()) { position, view ->
            Toast.makeText(this, "Test $position download clicked", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ResourcePackageDownloadingActivity::class.java)
            intent.putExtra(ResourcePackageDownloadingActivity.INTENT_EXTRA__DOWNLOADING_NAME, getTitleData()[position])
            startActivity(intent)
        }
        lvMarket.adapter = adapter
    }

    private fun getTitleData(): ArrayList<String> {
        val data = ArrayList<String>()
        for (i in 0 .. 49) {
            data.add("资源包 $i")
        }

        return data
    }

    private fun getIntroductionData(): ArrayList<String> {
        val data = ArrayList<String>()
        for (i in 0 .. 49) {
            data.add("这里是资源包 $i 的介绍")
        }

        return data
    }
}
