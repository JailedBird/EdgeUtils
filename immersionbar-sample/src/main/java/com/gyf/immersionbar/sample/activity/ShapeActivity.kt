package com.gyf.immersionbar.sample.activity

import android.view.LayoutInflater
import cn.jailedbird.edgeutils.EdgeUtils
import cn.jailedbird.edgeutils.paddingTopSystemWindowInsets
import com.gyf.immersionbar.sample.R
import com.gyf.immersionbar.sample.databinding.ActivityShapeBinding

class ShapeActivity : BaseViewBindingActivity<ActivityShapeBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_shape
    }

    override fun initView() {
        binding.toolbarWrapper.paddingTopSystemWindowInsets()
        EdgeUtils.setNavigationBarColor(this, R.color.shape1)
    }

    override val inflate: (LayoutInflater) -> ActivityShapeBinding
        get() = ActivityShapeBinding::inflate
}