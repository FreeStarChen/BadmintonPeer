package com.mark.badmintonpeer

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.Shape
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.mark.badmintonpeer.chatroom.ChatroomChatAdapter
import com.mark.badmintonpeer.data.Chat
import com.mark.badmintonpeer.groupdetail.GroupDetailCircleAdapter
import com.mark.badmintonpeer.groupdetail.GroupDetailImageAdapter
import com.mark.badmintonpeer.network.LoadApiStatus
import com.mark.badmintonpeer.util.Util.getColor

/**
 * Uses the Glide library to load an image by URL into an [ImageView]
 */
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {

    val drawable = CircularProgressDrawable(imgView.context)
    drawable.setColorSchemeColors(
        R.color.court_green,
        R.color.teal_200,
    )
    drawable.centerRadius = 50f
    drawable.strokeWidth = 10f
    drawable.start()

    imgUrl?.let {
        val imgUri = it.toUri().buildUpon().build()
        GlideApp.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(drawable)
                    .error(R.drawable.ic_error)
            )
            .into(imgView)
    }
}

@BindingAdapter("imageUrlTransform")
fun bindImageTransform(imgView: ImageView, imgUrl: String?) {

    val drawable = CircularProgressDrawable(imgView.context)
    drawable.setColorSchemeColors(
        R.color.court_green,
        R.color.teal_200,
    )
    drawable.centerRadius = 50f
    drawable.strokeWidth = 10f
    drawable.start()

    imgUrl?.let {
        val imgUri = it.toUri().buildUpon().build()
        GlideApp.with(imgView.context)
            .load(imgUri)
            .centerCrop()
//            .transform(CenterCrop(), RoundedCorners(25))
            .apply(
                RequestOptions()
//                    .transform(CenterCrop(), RoundedCorners(25))
                    .placeholder(drawable)
                    .error(R.drawable.ic_error)
            )
            .into(imgView)
    }
}

@BindingAdapter("imageUrlWithCircleCrop")
fun bindImageWithCircleCrop(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = it.toUri().buildUpon().build()
        GlideApp.with(imgView.context)
            .load(imgUri)
            .circleCrop()
            .apply(
                RequestOptions()
                    .circleCrop()
                    .placeholder(R.drawable.ig_loading)
                    .error(R.drawable.ic_badminton_two_color)
            )
            .into(imgView)
    }
}

@BindingAdapter("images")
fun bindRecyclerViewWithImages(recyclerView: RecyclerView, images: List<String>?) {
    images?.let {
        recyclerView.adapter?.apply {
            when (this) {
                is GroupDetailImageAdapter -> {
                    submitImages(it)
                }
            }
        }
    }
}

/**
 * Adds decoration to [RecyclerView]
 */
@BindingAdapter("addDecoration")
fun bindDecoration(recyclerView: RecyclerView, decoration: RecyclerView.ItemDecoration?) {
    decoration?.let { recyclerView.addItemDecoration(it) }
}

@BindingAdapter("count")
fun bindRecyclerViewByCount(recyclerView: RecyclerView, count: Int?) {
    count?.let {
        recyclerView.adapter?.apply {
            when (this) {
                is GroupDetailCircleAdapter -> {
                    submitCount(it)
                }
            }
        }
    }
}

@BindingAdapter("circleStatus")
fun bindDetailCircleStatus(imageView: ImageView, isSelected: Boolean = false) {
    imageView.background = ShapeDrawable(object : Shape() {
        override fun draw(canvas: Canvas, paint: Paint) {

            paint.color = getColor(R.color.white)
            paint.isAntiAlias = true

            when (isSelected) {
                true -> {
                    paint.style = Paint.Style.FILL
                }
                false -> {
                    paint.style = Paint.Style.STROKE
                    paint.strokeWidth = MainApplication.instance.resources
                        .getDimensionPixelSize(R.dimen.edge_detail_circle).toFloat()
                }
            }

            canvas.drawCircle(
                this.width / 2, this.height / 2,
                MainApplication.instance.resources
                    .getDimensionPixelSize(R.dimen.radius_detail_circle).toFloat(),
                paint
            )
        }
    })
}

@BindingAdapter("circleStatusGrey")
fun bindNewsCircleStatus(imageView: ImageView, isSelected: Boolean = false) {
    imageView.background = ShapeDrawable(object : Shape() {
        override fun draw(canvas: Canvas, paint: Paint) {

            paint.color = getColor(R.color.gray_888888)
            paint.isAntiAlias = true

            when (isSelected) {
                true -> {
                    paint.style = Paint.Style.FILL
                }
                false -> {
                    paint.style = Paint.Style.STROKE
                    paint.strokeWidth = MainApplication.instance.resources
                        .getDimensionPixelSize(R.dimen.edge_detail_circle).toFloat()
                }
            }

            canvas.drawCircle(
                this.width / 2, this.height / 2,
                MainApplication.instance.resources
                    .getDimensionPixelSize(R.dimen.radius_detail_circle).toFloat(),
                paint
            )
        }
    })
}

/**
 * According to [LoadApiStatus] to decide the visibility of [ProgressBar]
 */
@BindingAdapter("setupApiStatus")
fun bindApiStatus(view: ProgressBar, status: LoadApiStatus?) {
    when (status) {
        LoadApiStatus.LOADING -> view.visibility = View.VISIBLE
        LoadApiStatus.DONE, LoadApiStatus.ERROR -> view.visibility = View.GONE
    }
}

/**
 * According to [message] to decide the visibility of [ProgressBar]
 */
@BindingAdapter("setupApiErrorMessage")
fun bindApiErrorMessage(view: TextView, message: String?) {
    when (message) {
        null, "" -> {
            view.visibility = View.GONE
        }
        else -> {
            view.text = message
            view.visibility = View.VISIBLE
        }
    }
}

//@BindingAdapter("chats")
//fun bindRecyclerView(recyclerView: RecyclerView, chatItems: List<Chat>?) {
//    chatItems?.let {
//        recyclerView.adapter?.apply {
//            when (this) {
//                is ChatroomChatAdapter ->
//            }
//        }
//    }
//}