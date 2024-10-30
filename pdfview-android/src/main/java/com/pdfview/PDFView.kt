package com.pdfview

import android.content.ContentResolver.SCHEME_ANDROID_RESOURCE
import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import androidx.annotation.RawRes
import com.pdfview.subsamplincscaleimageview.ImageSource
import com.pdfview.subsamplincscaleimageview.SubsamplingScaleImageView
import java.io.File

class PDFView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : SubsamplingScaleImageView(context, attrs) {

    private var mUri: Uri? = null
    private var mScale: Float = 8f

    init {
        setMinimumTileDpi(120)
        setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_START)
    }

    fun fromAsset(assetFileName: String): PDFView {
        mUri = Uri.parse("file:///android_asset/$assetFileName")
        return this
    }

    fun fromFile(file: File): PDFView {
        mUri = Uri.fromFile(file)
        return this
    }

    fun fromFile(filePath: String): PDFView {
        mUri = Uri.fromFile(File(filePath))
        return this
    }

    fun fromResource(@RawRes resource: Int): PDFView {
        mUri = Uri.parse(SCHEME_ANDROID_RESOURCE + "://" + context.packageName + "/" + resource)
        return this
    }

    fun fromUri(uri: Uri): PDFView {
        mUri = uri
        return this
    }

    fun scale(scale: Float): PDFView {
        mScale = scale
        return this
    }

    fun show() {
        val source = ImageSource.uri(mUri!!)
        setRegionDecoderFactory { PDFRegionDecoder(view = this, scale = mScale) }
        setImage(source)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        this.recycle()
    }
}