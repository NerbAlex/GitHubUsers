package ru.inc.myapplication.ui.image

import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import ru.inc.myapplication.extensions.test
import ru.inc.myapplication.mvp.model.image.IImageLoader
import java.util.logging.Logger

class GlideImageLoader : IImageLoader<ImageView> {

    val LOG = Logger.getLogger(GlideImageLoader::class.java.name)

    override fun loadInto(url: String, container: ImageView) {
        Glide.with(container.context)
            .asBitmap()
            .load(url)
            .listener(object : RequestListener<Bitmap> {

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    isFirstResource: Boolean
                ): Boolean {

                    //Обработка провала загрузки
                    return false
                }

                override fun onResourceReady(
                    bitmap: Bitmap?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {

                    //Save to cache
                    return false
                }
            })
            .into(container)


    }
}