package com.example.nakshatratak.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.example.nakshatratak.DataClass.GridViewModal
import com.example.nakshatratak.R
import com.example.nakshatratak.recyclerviews.AstrologerRVadapter
import com.example.nakshatratak.recyclerviews.DataModel
import com.example.nakshatratak.recyclerviews.GridRVAdapter
import com.denzcoskun.imageslider.constants.ScaleTypes

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private lateinit var recyclerView: RecyclerView
private lateinit var adapter: AstrologerRVadapter
lateinit var courseGRV: GridView
lateinit var courseList: List<GridViewModal>
/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_home, container, false)


        val dataList = generateDummyData() // Replace with your actual data

        recyclerView = view.findViewById(R.id.recyclarview)
        recyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)
        adapter = AstrologerRVadapter(context, dataList)
        recyclerView.adapter = adapter




        // initializing variables of grid view with their ids.
        courseGRV = view.findViewById(R.id.idGRV)
        courseList = ArrayList<GridViewModal>()

        // on below line we are adding data to
        // our course list with image and course name.
        courseList = courseList + GridViewModal("Aries", R.raw.aries)
        courseList = courseList + GridViewModal("Taurus", R.raw.taurus)
        courseList = courseList + GridViewModal("Gemini", R.raw.gemini)
        courseList = courseList + GridViewModal("Cancer", R.raw.cancer)
        courseList = courseList + GridViewModal("Leo", R.raw.leo)
        courseList = courseList + GridViewModal("Capricon", R.raw.capricon)
        courseList = courseList + GridViewModal("Libra", R.raw.libra)
        courseList = courseList + GridViewModal("Pieces", R.raw.pieces)
        courseList = courseList + GridViewModal("Aqaurius", R.raw.aquarius)
        courseList = courseList + GridViewModal("Virgo", R.raw.virgo)
        courseList = courseList + GridViewModal("Sagittarius", R.raw.sagittarius)
        courseList = courseList + GridViewModal("Scorpio", R.raw.scorpio)



        // on below line we are initializing our course adapter
        // and passing course list and context.
        val courseAdapter = GridRVAdapter(courseList = courseList, context)

        // on below line we are setting adapter to our grid view.
        courseGRV.adapter = courseAdapter
        setGridViewHeightBasedOnChildren(courseGRV, 9)
        // on below line we are adding on item
        // click listener for our grid view.
        courseGRV.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            // inside on click method we are simply displaying
            // a toast message with course name.
            Toast.makeText(
                context, courseList[position].courseName + " selected",
                Toast.LENGTH_SHORT
            ).show()
        }





        val imageList = ArrayList<SlideModel>() // Create image list

// imageList.add(SlideModel("String Url" or R.drawable)
// imageList.add(SlideModel("String Url" or R.drawable, "title") You can add title

        imageList.add(SlideModel(R.raw.blog02, "Online Astrology: Is it safe or not? ",ScaleTypes.CENTER_CROP))
        imageList.add(SlideModel(R.raw.blog03, "Do you need a Spritual Insurance",ScaleTypes.CENTER_CROP))
        imageList.add(SlideModel(R.raw.blog03, "And people do that.",ScaleTypes.CENTER_CROP))

        val imageSlider = view.findViewById<ImageSlider>(R.id.image_slider)
        imageSlider.setImageList(imageList)




        return view
    }



    fun setGridViewHeightBasedOnChildren(gridView: GridView, columns: Int) {
        val adapter = gridView.adapter ?: return

        var totalHeight = 0
        val items = adapter.count
        val rows = (items + columns - 1) / columns

        for (i in 0 until items) {
            val listItem = adapter.getView(i, null, gridView)
            listItem.measure(
                View.MeasureSpec.makeMeasureSpec(gridView.width, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            )
            if (i % columns == 0) {
                totalHeight += listItem.measuredHeight
            }
        }

        val params = gridView.layoutParams
        params.height = totalHeight + (gridView.verticalSpacing * (rows - 1))
        gridView.layoutParams = params
        gridView.requestLayout()
    }









    private fun generateDummyData(): List<DataModel> {
        val dataList = mutableListOf<DataModel>()
        // Replace with your actual data population logic
        dataList.add(DataModel(R.drawable.astrologer01, "Name", "100/min"))
        dataList.add(DataModel(R.drawable.astrologer01, "Item 2", "100/min"))
        dataList.add(DataModel(R.drawable.astrologer01, "Item 3","100/min"))
        dataList.add(DataModel(R.drawable.astrologer01, "pandit", "100/min"))
        dataList.add(DataModel(R.drawable.astrologer01, "Rahul", "100/min"))
        dataList.add(DataModel(R.drawable.astrologer01, "Rahul", "100/min"))
        dataList.add(DataModel(R.drawable.astrologer01, "Rahul", "100/min"))
        // Add more items as needed
        return dataList



    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}