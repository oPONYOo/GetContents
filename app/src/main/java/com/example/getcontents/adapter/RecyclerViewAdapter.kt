package com.example.getcontents.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.getcontents.R
import com.example.getcontents.databinding.ItemLoadingBinding
import com.example.getcontents.databinding.RecycleritemLayoutBinding
import com.example.getcontents.network.dto.UnitsDto
import kotlin.collections.ArrayList

class RecyclerViewAdapter(
    private var items : ArrayList<UnitsDto>) : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    companion object {
        private const val TYPE_ITEM = 0
        private const val TYPE_LOADING = 1
    }
    private var context:Context? = null
    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        val binding = RecycleritemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)

    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemHolder = holder as ItemViewHolder
        val item = items[position]
        itemHolder.bind(item)
    }
    // 아이템뷰에 프로그레스바가 들어가는 경우
    inner class LoadingViewHolder(private val binding: ItemLoadingBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    inner class ItemViewHolder(var binding: RecycleritemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UnitsDto){
            Log.e("item", item.title)
            binding.nameTxtView.text = item.title
            binding.typeTxtView.text = item.type
            if (item.progress == 100){
                binding.progressTxtView.setTextColor(Color.parseColor("#B0FFFF"))
            }
            binding.progressTxtView.text = ("${item.progress}% 완료")


            Glide.with(context!!)
                .load(item.thumbnail)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(15)))
                .thumbnail(0.5f)
                .into(binding.imgView)
            binding.imgView.clipToOutline = true

        }

    }

}