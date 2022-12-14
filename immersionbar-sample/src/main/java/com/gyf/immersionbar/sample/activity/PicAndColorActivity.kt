package com.gyf.immersionbar.sample.activity

import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import cn.jailedbird.edgeutils.EdgeUtils
import cn.jailedbird.edgeutils.EdgeUtils.edgeHasStatusBar
import cn.jailedbird.edgeutils.EdgeUtils.edgeHideStatusBar
import cn.jailedbird.edgeutils.EdgeUtils.edgeShowStatusBar
import cn.jailedbird.edgeutils.paddingTopSystemWindowInsets
import com.blankj.utilcode.util.ColorUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gyf.immersionbar.sample.R
import com.gyf.immersionbar.sample.databinding.ActivityPicColorBinding
import com.gyf.immersionbar.sample.utils.Utils
import kotlinx.coroutines.launch

class PicAndColorActivity : BaseViewBindingActivity<ActivityPicColorBinding>(),
    OnSeekBarChangeListener {

    override fun getLayoutId(): Int {
        return R.layout.activity_pic_color
    }

    override fun initView() {
        super.initView()
        // 这里显然Toolbar是透明的 直接边距最好使用binding.toolbarWrapper.marginTopSystemWindowInsets()
        // 但是因为后续存在变色环节 连同顶部的status bar一起变化 这才使用paddingTop而非marginTop
        binding.toolbarWrapper.paddingTopSystemWindowInsets()
        Glide.with(this).asBitmap().load(Utils.getPic())
            .apply(RequestOptions().placeholder(R.mipmap.test))
            .into(binding.mIv)
    }

    override fun setListener() {
        binding.seekBar.setOnSeekBarChangeListener(this)
    }

    override fun initEvent() {
        binding.btnStatusColor.setOnClickListener {
            EdgeUtils.setStatusBarColor(this, R.color.colorAccent)
        }

        binding.btnNavigationColor.setOnClickListener {
            lifecycleScope.launch {
                if (EdgeUtils.hasNavigationBar(this@PicAndColorActivity)) {
                    EdgeUtils.setNavigationBarColor(this@PicAndColorActivity, R.color.colorAccent)
                } else {
                    Toast.makeText(this@PicAndColorActivity, "当前设备没有导航栏", Toast.LENGTH_SHORT).show()
                }
            }

        }

        binding.btnColor.setOnClickListener {
            EdgeUtils.setStatusBarColor(this, android.R.color.transparent)
            EdgeUtils.setNavigationBarColor(this, android.R.color.transparent)
            binding.toolbarWrapper.background =
                ColorDrawable(ColorUtils.getColor(android.R.color.transparent))
        }

        binding.btnStatusControl.setOnClickListener {
            lifecycleScope.launch {
                if (edgeHasStatusBar()) {
                    edgeHideStatusBar()
                } else {
                    edgeShowStatusBar()
                }
            }
        }
    }

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        binding.toolbarWrapper.background = ColorDrawable(ColorUtils.getRandomColor(true))
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {}
    override fun onStopTrackingTouch(seekBar: SeekBar) {}
    override val inflate: (LayoutInflater) -> ActivityPicColorBinding
        get() = ActivityPicColorBinding::inflate
}