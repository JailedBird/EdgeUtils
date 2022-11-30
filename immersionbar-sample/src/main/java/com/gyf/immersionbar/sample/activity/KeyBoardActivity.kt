package com.gyf.immersionbar.sample.activity

import android.view.LayoutInflater
import android.view.View
import android.widget.SimpleAdapter
import cn.jailedbird.edgeutils.marginBottomImeSystemWindowInsets
import cn.jailedbird.edgeutils.paddingTopSystemWindowInsets
import com.gyf.immersionbar.sample.R
import com.gyf.immersionbar.sample.databinding.ActivityKeyBoardBinding

class KeyBoardActivity : BaseViewBindingActivity<ActivityKeyBoardBinding>() {

    private var mapList: MutableList<Map<String, Any?>> = mutableListOf()
    override fun getLayoutId(): Int {
        return R.layout.activity_key_board
    }

    override fun initData() {
        mapList.clear()
        for (i in 1..20) {
            mapList.add(mapOf("desc" to "我是假数据$i"))
        }
    }

    override fun initView() {
        binding.toolbarWrapper.paddingTopSystemWindowInsets()
        binding.root.marginBottomImeSystemWindowInsets()

        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.listView.adapter = SimpleAdapter(
            this,
            mapList,
            R.layout.item_simple,
            arrayOf("desc"),
            intArrayOf(R.id.text)
        )
    }

    override fun setListener() {
        binding.toolbar.setNavigationOnClickListener { v: View? -> finish() }
    }

    override val inflate: (LayoutInflater) -> ActivityKeyBoardBinding
        get() = ActivityKeyBoardBinding::inflate
}