package com.gyf.immersionbar.sample.activity

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import cn.jailedbird.edgeutils.EdgeUtils
import cn.jailedbird.edgeutils.EdgeUtils.edgeHasNavigationBar
import cn.jailedbird.edgeutils.EdgeUtils.edgeHasStatusBar
import cn.jailedbird.edgeutils.EdgeUtils.edgeHideNavigationBar
import cn.jailedbird.edgeutils.EdgeUtils.edgeHideStatusBar
import cn.jailedbird.edgeutils.EdgeUtils.edgeHideSystemBar
import cn.jailedbird.edgeutils.EdgeUtils.edgeIsNavigationBarLight
import cn.jailedbird.edgeutils.EdgeUtils.edgeNavigationBarHeight
import cn.jailedbird.edgeutils.EdgeUtils.edgeNavigationBarHeightIgnoringVisibility
import cn.jailedbird.edgeutils.EdgeUtils.edgeSetNavigationBarColor
import cn.jailedbird.edgeutils.EdgeUtils.edgeSetNavigationBarLight
import cn.jailedbird.edgeutils.EdgeUtils.edgeShowNavigationBar
import cn.jailedbird.edgeutils.EdgeUtils.edgeShowStatusBar
import cn.jailedbird.edgeutils.EdgeUtils.edgeShowSystemBar
import cn.jailedbird.edgeutils.paddingBottomSystemWindowInsets
import cn.jailedbird.edgeutils.paddingTopSystemWindowInsets
import com.blankj.utilcode.util.BarUtils
import com.gyf.immersionbar.sample.R
import com.gyf.immersionbar.sample.databinding.ActivityParamsBinding
import kotlinx.coroutines.launch

class ParamsActivity : BaseViewBindingActivity<ActivityParamsBinding>() {

    override fun initImmersionBar() {
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        super.initView()
        binding.mEdgeLayout.paddingTopSystemWindowInsets()
        binding.root.paddingBottomSystemWindowInsets()
        edgeSetNavigationBarLight(false)
        edgeSetNavigationBarColor(R.color.btn13)

        lifecycleScope.launch {
            if (edgeHasStatusBar() && edgeHasNavigationBar()) {
                edgeHideSystemBar()
            } else {
                edgeShowSystemBar()
            }
            initViewAgain()
        }
    }

    private suspend fun initViewAgain() {
        binding.mTvHasStatus.text = getText(
            "是否有StatusBar(Api不可靠, 加延时(View.post)才能获取否则为0):" + EdgeUtils.hasStatusBar(this@ParamsActivity)
        )
        binding.mTvStatus.text = getText(
            "StatusBar Height(Api不可靠, 加延时(View.post)才能获取否则为0):" + EdgeUtils.statusBarHeight(
                this@ParamsActivity
            )
        )
        binding.mTvStatusHide.text = getText(
            "StatusBar Height(不管是否隐藏)(Api不可靠, 加延时(View.post)才能获取否则为0):" + EdgeUtils.statusBarHeightIgnoringVisibility(
                this@ParamsActivity
            )
        )
        binding.mTvHasNav.text = getText(
            "是否有NavigationBar(Api不可靠, 加延时(View.post)才能获取否则为0):" + edgeHasNavigationBar()
        )
        binding.mTvNav.text = getText(
            "NavigationBar Height:(Api不可靠, 加延时(View.post)才能获取否则为0):" + edgeNavigationBarHeight()
        )
        binding.mTvNavHide.text = getText(
            "NavigationBar Height(不管是否隐藏):(Api不可靠, 加延时(View.post)才能获取否则为0):" + edgeNavigationBarHeightIgnoringVisibility()
        )
    }

    @SuppressLint("SetTextI18n")
    override fun setListener() {
        super.setListener()
        ViewCompat.setOnApplyWindowInsetsListener(binding.mTvInsets) { _: View?, windowInsetsCompat: WindowInsetsCompat ->
            binding.mTvInsets.text =
                getText(
                    "WindowInsetTop：" + ViewCompat.getRootWindowInsets(window.decorView)
                        ?.getInsets(WindowInsetsCompat.Type.systemBars())
                )
            windowInsetsCompat
        }
        BarUtils.getStatusBarHeight()
        /* 调整状态栏前景色*/
        binding.mTvStatusDark.setOnClickListener {
            if (EdgeUtils.isStatusBarLight(this)) {
                EdgeUtils.setStatusBarLight(this, false)
            } else {
                EdgeUtils.setStatusBarLight(this, true)
            }
            lifecycleScope.launch {
                initViewAgain()
            }
        }
        /* 调整导航栏前景色 手势导航模式下无效 导航条会自动变色 形成反差色*/
        binding.mTvNavigationDark.setOnClickListener {
            // 在全面屏模式系，存在2种变体 存在导航条 和 不存在导航条
            if (edgeIsNavigationBarLight()) {
                edgeSetNavigationBarLight(false)
            } else {
                edgeSetNavigationBarLight(true)
            }
            lifecycleScope.launch {
                initViewAgain()
            }
        }
        /* 显示隐藏状态栏*/
        binding.mBtnStatus.setOnClickListener {
            lifecycleScope.launch {
                if (edgeHasStatusBar()) {
                    edgeHideStatusBar()
                } else {
                    edgeShowStatusBar()
                }
                initViewAgain()
            }

        }
        /* 显示隐藏导航栏*/
        binding.mBtnNav.setOnClickListener {
            lifecycleScope.launch {
                if (edgeHasNavigationBar()) {
                    edgeHideNavigationBar()
                } else {
                    edgeShowNavigationBar()
                }
                initViewAgain()
            }
        }

        /* 显示隐藏系统栏*/
        binding.mBtnSystemBar.setOnClickListener {

            lifecycleScope.launch {
                if (edgeHasStatusBar() && edgeHasNavigationBar()) {
                    edgeHideSystemBar()
                } else {
                    edgeShowSystemBar()
                }
                initViewAgain()
            }
        }

    }

    private fun getText(text: String): String {
        return text.ifEmpty {
            "Not impl"
        }
    }

    override val inflate: (LayoutInflater) -> ActivityParamsBinding
        get() = ActivityParamsBinding::inflate

    override fun getLayoutId(): Int {
        return R.layout.activity_params
    }

}