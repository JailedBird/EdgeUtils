package com.gyf.immersionbar.sample.activity

import android.os.Bundle
import android.view.LayoutInflater
import cn.jailedbird.barutils.EdgeUtils
import cn.jailedbird.barutils.paddingTopSystemWindowInsets
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gyf.immersionbar.sample.R
import com.gyf.immersionbar.sample.databinding.ActivityPicBinding
import com.gyf.immersionbar.sample.utils.Utils

/**
 * @author gyf
 * @date 2016/10/24
 */
class PicActivity : BaseViewBindingActivity<ActivityPicBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Glide.with(this).asBitmap().load(Utils.getFullPic())
            .apply(RequestOptions().placeholder(R.drawable.pic_all))
            .into(binding.mIv)
    }

    override fun initView() {
        EdgeUtils.hideStatusBar(this)
        binding.toolbarWrapper.paddingTopSystemWindowInsets()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_pic
    }

    override val inflate: (LayoutInflater) -> ActivityPicBinding
        get() = ActivityPicBinding::inflate
}