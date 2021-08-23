package com.example.getcontents.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.getcontents.R
import com.example.getcontents.databinding.ActivityContentsDetailBinding
import com.example.getcontents.databinding.ActivityContentsListBinding
import com.example.getcontents.network.dto.UnitsDto

class ContentsDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContentsDetailBinding
    private lateinit var title:String
    private lateinit var img : String
    private lateinit var type :String
    private lateinit var section : String
    private lateinit var detail : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContentsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        img = intent.getStringExtra(EXTRA_IMAGE).toString()
        title = intent.getStringExtra(EXTRA_TITLE).toString()
        type = intent.getStringExtra(EXTRA_TYPE).toString()
        section = intent.getStringExtra(EXTRA_SECTION).toString()
        detail = intent.getStringExtra(EXTRA_DETAIL).toString()
        Log.e("title", title.toString())
        Log.e("img", img.toString())
        binding.toolbar.title = title
        Glide.with(this)
            .load(img)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(15)))
            .thumbnail(0.5f)
            .into(binding.imgView)
        binding.imgView.clipToOutline = true
        Log.e("type", type.toString())
        Log.e("section", section.toString())
        Log.e("detail", detail.toString())
    }
//    private fun setData(){
//        binding.toolbar.title = title
//        binding.desTxtView.text = detail
//
//        Glide.with(context!!)
//            .load(img)
//            .apply(RequestOptions.bitmapTransform(RoundedCorners(15)))
//            .thumbnail(0.5f)
//            .into(binding.imgView)
//        binding.imgView.clipToOutline = true
//
//    }
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
        private const val EXTRA_DETAIL = "EXTRA_DETAIL"

    }
}