package com.example.getcontents.activity

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.getcontents.adapter.RecyclerViewAdapter
import com.example.getcontents.databinding.ActivityContentsListBinding
import com.example.getcontents.listener.EndlessRecyclerViewScrollListener
import com.example.getcontents.network.dto.UnitsDto
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.*
import java.lang.Runnable


class ContentsListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContentsListBinding
    private var dtoList :ArrayList<UnitsDto> = ArrayList()
    private var items: ArrayList<UnitsDto?> = ArrayList()
    private lateinit var mMapLayoutManager: LinearLayoutManager
    private lateinit var mListAdapter: RecyclerViewAdapter
    private lateinit var mRecyclerView: RecyclerView
    val mainDispatcher: CoroutineDispatcher = Dispatchers.Main
    private var isLoading =false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContentsListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            lifecycleOwner = this@ContentsListActivity
            activity = this@ContentsListActivity
        }

        setData()
        initAdapter()
        initScrollListener()
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                Log.e("TAG", "${tab!!.position}")
                when(tab.position){
                    0 ->{
                        mListAdapter.filter.filter("")
                    }
                    1 -> {
                        mListAdapter.filter.filter("LEARNING")
                    }
                    2 -> {
                        mListAdapter.filter.filter("GAME")
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab!!.view.setBackgroundColor(Color.TRANSPARENT)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })


    }
    private fun setData(){
        dtoList= intent.getSerializableExtra(EXTRA_TITLE) as ArrayList<UnitsDto>
        Log.e("dto", "$dtoList")
        if(dtoList.size<10){
            for (i in 0 until dtoList.size){
                items.add(dtoList[i])
            }
        }else{
            for (i in 0 until 10){
                items.add(dtoList[i])
            }
        }

        Log.e("items", "${items}")
    }

    private fun initAdapter(){
        mListAdapter = RecyclerViewAdapter(items)
        mMapLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = mListAdapter

    }
    private fun initScrollListener(){
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                // 스크롤이 끝에 도달했는지 확인
                if(!isLoading){
                    if ((recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition() == items.size - 1){
                        Log.e("true", "True")
                        moreItems()
                        isLoading =  true
                    }

                }
            }
        })
    }


    fun moreItems(){
        mRecyclerView = binding.recyclerView

        val runnable = Runnable {
            items.add(null)
            mListAdapter.notifyItemInserted(items.size -1)
            Log.e("null", "${items.size}")
        }
        mRecyclerView.post(runnable)

        CoroutineScope(mainDispatcher).launch {
            delay(2000)
            val runnable2=  Runnable{
                items.removeAt(items.size - 1)
                val scrollPosition = items.size
                mListAdapter.notifyItemRemoved(scrollPosition)
                var currentSize = scrollPosition
                var nextLimit = currentSize+10
                Log.e("hello", "${nextLimit}")
                if (currentSize < dtoList.size-10){
                    while (currentSize-1<nextLimit){
                        items.add(dtoList[currentSize])
                        currentSize++
                    }
                }else{
                    while (currentSize!=dtoList.size){
                        items.add(dtoList[currentSize])
                        currentSize++
                    }
                }
                Log.e("full", "${items.size}")
                mListAdapter.updateItem(items)
                isLoading = false
                Log.e("curr", "${items}")
        }
            runnable2.run()
        }
    }
    companion object {
        private const val BASE_URL = "https://api.super-brain.co.kr/"
        private const val EXTRA_PROGRESS = "EXTRA_PROGRESS"
        private const val EXTRA_IS_FINISHED = "EXTRA_IS_FINISHED"
        private const val EXTRA_SURVEY_LIST = "EXTRA_SURVEY_LIST"
        private const val EXTRA_UNIT = "EXTRA_UNIT"
        private const val EXTRA_ASSIGNMENT = "EXTRA_ASSIGNMENT"
        private const val EXTRA_SECTION = "EXTRA_SECTION"
        private const val EXTRA_TYPE = "EXTRA_TYPE"
        private const val EXTRA_TITLE = "EXTRA_TITLE"
        private const val EXTRA_IMAGE = "EXTRA_IMAGE"

    }
}