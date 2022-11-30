package com.gyf.immersionbar.sample.activity

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.gyf.immersionbar.sample.AppManager
import cn.jailedbird.edgeutils.EdgeUtils.edgeToEdge

abstract class BaseViewBindingActivity<T : ViewBinding> : AppCompatActivity() {
    private var _binding: T? = null
    val binding get() = _binding!!


    protected var mTag: String = this.javaClass.simpleName
    protected var mActivity: Activity? = null

    protected fun useViewBinding(): Boolean {
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.getInstance().addActivity(this)
        edgeToEdge()
        mActivity = this
        _binding = inflate.invoke(layoutInflater)
        setContentView(binding.root)

        //初始化沉浸式
        /*initImmersionBar();*/
        //初始化数据
        initData()
        //view与数据绑定
        initView()
        initEvent()
        //设置监听
        setListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        AppManager.getInstance().removeActivity(this)
    }


    /**
     * 子类设置布局Id
     *
     * @return the layout id
     */
    protected abstract fun getLayoutId(): Int

    /**
     * 初始化沉浸式
     * Init immersion bar.
     */
    protected open fun initImmersionBar() {
        //设置共同沉浸式样式
        /*ImmersionBar.with(this).navigationBarColor(R.color.colorPrimary).init();*/
    }

    protected open fun initData() {}
    protected open fun initView() {}
    protected open fun initEvent() {}
    protected open fun setListener() {}


    abstract val inflate: (LayoutInflater) -> T

}

