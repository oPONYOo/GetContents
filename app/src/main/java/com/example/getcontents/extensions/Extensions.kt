package com.example.getcontents.extensions

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity

object Extensions {

    fun AppCompatActivity.startActivity(dst: Class<*>) {
        startActivity(Intent(this, dst).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
    }

    fun AppCompatActivity.startActivityWithFinish(dst: Class<*>) {
        startActivity(Intent(this, dst).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        this.finish()
    }


    fun View.showing(show: Boolean) {
        visibility = if(show) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    fun View.show() {
        visibility = View.VISIBLE
    }

    fun View.hide() {
        visibility = View.INVISIBLE
    }

    fun View.gone() {
        visibility = View.GONE
    }

    fun ImageView.glide(fileName: String) {
        Glide
            .with(context)
            .load(fileName)
            .skipMemoryCache(false)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .fitCenter()
            .thumbnail(0.5f)
            .into(this)
    }

    fun ImageView.glide(fileName: Int) {
        Glide
            .with(context)
            .load(fileName)
            .skipMemoryCache(false)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .fitCenter()
            .thumbnail(0.5f)
            .into(this)
    }
}