package com.gyf.immersionbar.sample.activity

import android.view.LayoutInflater
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.gyf.immersionbar.sample.R
import com.gyf.immersionbar.sample.adapter.TabAdapter
import com.gyf.immersionbar.sample.databinding.ActivityTabLayoutBinding
import cn.jailedbird.edgeutils.EdgeUtils
import cn.jailedbird.edgeutils.heightToTopSystemWindowInsets

class TabLayoutActivity : BaseViewBindingActivity<ActivityTabLayoutBinding>() {
    private var mData: MutableList<String>? = null
    private var mAdapter: TabAdapter? = null

    override fun initData() {
        initData(1)
    }

    private fun initData(pager: Int) {
        mData = ArrayList()
        for (i in 1..49) {
            (mData as ArrayList<String>).add("pager" + pager + " 第" + i + "个item")
        }
    }

    override fun initView() {
        EdgeUtils.setNavigationBarColor(this, R.color.cool_green_normal)
        // 通过设置View的固定高度去实现沉浸式
        /*binding.view.post {
            val newLayoutParams = binding.view.layoutParams.apply {
                height = EdgeUtils.statusBarHeight(this@TabLayoutActivity)
            }
            binding.view.layoutParams = newLayoutParams
        }*/
        binding.view.heightToTopSystemWindowInsets()
        //设置ToolBar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = ""
        //setSupportActionBar(toolbar);//替换系统的actionBar

        //设置TabLayout
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        for (i in 1..6) {
            tabLayout.addTab(tabLayout.newTab().setText("TAB$i"))
        }
        //TabLayout的切换监听
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                //切换的时候更新RecyclerView
                initData(tab.position + 1)
                mAdapter!!.setNewData(mData)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        //设置RecycleView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        mAdapter = TabAdapter()
        recyclerView.adapter = mAdapter
        mAdapter!!.setNewData(mData)
    }

    override val inflate: (LayoutInflater) -> ActivityTabLayoutBinding
        get() = ActivityTabLayoutBinding::inflate

    override fun getLayoutId(): Int {
        return R.layout.activity_tab_layout
    }
}