package com.gyf.immersionbar.sample.activity

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import cn.jailedbird.edgeutils.EdgeUtils
import cn.jailedbird.edgeutils.marginTopSystemWindowInsets
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.gyf.immersionbar.sample.R
import com.gyf.immersionbar.sample.databinding.ActivityCoordinatorBinding
import com.gyf.immersionbar.sample.utils.Utils

class CoordinatorActivity : BaseViewBindingActivity<ActivityCoordinatorBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_coordinator
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        super.initView()
        ToastUtils.showShort("这个动态效果比较复杂、对CollapsingToolbarLayout不清楚，我暂时没想出来; 效果看起来有点怪")
        EdgeUtils.setStatusBarColor(this, android.R.color.transparent)
        binding.toolbarLayout.marginTopSystemWindowInsets()
        setSupportActionBar(binding.detailToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.text.text = "关于Snackbar在4.4和emui3.1上高度显示不准确的问题是由于沉浸式使用了系统的" +
                "WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS或者WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION" +
                "属性造成的，目前尚不知有什么解决办法"
        Glide.with(this).asBitmap().load(Utils.getPic())
            .apply(RequestOptions().placeholder(R.mipmap.test))
            .into(binding.mIv)
    }

    override fun setListener() {
        binding.fab.setOnClickListener { view: View? ->
            Snackbar.make(
                view!!, "我是Snackbar", Snackbar.LENGTH_LONG
            ).show()
        }
        //toolbar返回按钮监听
        binding.detailToolbar.setNavigationOnClickListener { v: View? -> finish() }
    }

    override val inflate: (LayoutInflater) -> ActivityCoordinatorBinding
        get() = ActivityCoordinatorBinding::inflate
}