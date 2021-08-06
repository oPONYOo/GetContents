package com.example.getcontents.activity

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.getcontents.R
import com.example.getcontents.adapter.RecyclerViewAdapter
import com.example.getcontents.databinding.ActivityContentsListBinding
import com.example.getcontents.listener.OnSingleItemTouchListener
import com.example.getcontents.network.dto.UnitsDto

class ContentsListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContentsListBinding
    private var dtoList :ArrayList<UnitsDto> = ArrayList()
    private lateinit var mMapLayoutManager: GridLayoutManager
    private lateinit var mListAdapter: RecyclerViewAdapter
    private var totalCount = 0 // 전체 아이템 개수
    private var isNext = false // 다음 페이지 유무
    private var page = 0       // 현재 페이지
    private var limit = 10     // 한 번에 가져올 아이템 수

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContentsListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            lifecycleOwner = this@ContentsListActivity
            activity = this@ContentsListActivity
        }
        dtoList= intent.getSerializableExtra(EXTRA_TITLE) as ArrayList<UnitsDto>
        Log.e("dto", "$dtoList")
        mMapLayoutManager = GridLayoutManager(this, 1)
        mListAdapter = RecyclerViewAdapter(dtoList)
        binding.recyclerView.adapter = mListAdapter
        binding.toolbar.title = "과제 리스트"
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition = (recyclerView.layoutManager as GridLayoutManager?)!!.findLastCompletelyVisibleItemPosition() // 화면에 보이는 마지막 아이템의 position
                val itemTotalCount = recyclerView.adapter!!.itemCount - 1 // 어댑터에 등록된 아이템의 총 개수 -1

                // 스크롤이 끝에 도달했는지 확인
                if (lastVisibleItemPosition == itemTotalCount) {
                    Log.e("finish", "$lastVisibleItemPosition")
                }
            }
        })

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