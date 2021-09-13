package com.example.getcontents.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.getcontents.activity.ContentsDetailActivity
import com.example.getcontents.databinding.ItemLoadingBinding
import com.example.getcontents.databinding.RecycleritemLayoutBinding
import com.example.getcontents.network.dto.UnitsDto
import kotlin.collections.ArrayList

class RecyclerViewAdapter(items: ArrayList<UnitsDto?>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable
{
    companion object {
        private const val TYPE_ITEM = 0
        private const val TYPE_LOADING = 1
    }
    private var context:Context? = null
    private var unFilteredList = items
    private var filteredList = items
    override fun getItemCount(): Int {
        return if (filteredList == null){
            Log.e("zero", "z")
            0
        }else{
            Log.e("nozero", "$filteredList?.size")
            filteredList?.size!!
        }

    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint.toString()
                filteredList = if (charString.isEmpty()) {
                    unFilteredList
                } else {
                    val filteringList = ArrayList<UnitsDto?>()
                    for (item in unFilteredList!!) {
                        if (item!!.type == charString) filteringList.add(item)
                    }
                    filteringList
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as ArrayList<UnitsDto?>?
                notifyDataSetChanged()
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        Log.e("filter", "${filteredList}")
        Log.e("posss", "${filteredList?.get(position)} ")
        Log.e("size", "${filteredList?.size}")
        Log.e("position", "$position")
        return when (filteredList?.get(position)) {
            null -> TYPE_LOADING
            else -> TYPE_ITEM

        }
    }

    fun updateItem(list:ArrayList<UnitsDto?>?){
        this.filteredList = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        Log.e("viewT", "$viewType")
        if (viewType == TYPE_ITEM){
            val binding = RecycleritemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ItemViewHolder(binding)
        }else{
            val binding = ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return LoadingViewHolder(binding)
        }


    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder){
            val item = filteredList?.get(position)
            val itemHolder = holder as ItemViewHolder
            itemHolder.bind(item!!)
        }else if (holder is LoadingViewHolder){

        }
    }
    // 아이템뷰에 프로그레스바가 들어가는 경우
    inner class LoadingViewHolder(var binding: ItemLoadingBinding) :
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

            itemView.setOnClickListener{
                val intent = Intent(context, ContentsDetailActivity::class.java)
                intent.putExtra("EXTRA_IMAGE", item.thumbnail)
                intent.putExtra("EXTRA_TITLE", item.title)
                intent.putExtra("EXTRA_TYPE", item.type)
                intent.putExtra("EXTRA_SECTION", item.section)
                intent.putExtra("EXTRA_DETAIL", item.sectionDetail)

                context!!.startActivity(intent)
            }
        }

    }


}
