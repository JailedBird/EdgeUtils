package com.gyf.immersionbar.sample.activity

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import cn.jailedbird.barutils.EdgeUtils
import cn.jailedbird.barutils.EdgeUtils.edgeHasNavigationBar
import cn.jailedbird.barutils.EdgeUtils.edgeHasStatusBar
import cn.jailedbird.barutils.EdgeUtils.edgeHideNavigationBar
import cn.jailedbird.barutils.EdgeUtils.edgeHideStatusBar
import cn.jailedbird.barutils.EdgeUtils.edgeHideSystemBar
import cn.jailedbird.barutils.EdgeUtils.edgeIsNavigationBarLight
import cn.jailedbird.barutils.EdgeUtils.edgeSetNavigationBarColor
import cn.jailedbird.barutils.EdgeUtils.edgeSetNavigationBarLight
import cn.jailedbird.barutils.EdgeUtils.edgeShowNavigationBar
import cn.jailedbird.barutils.EdgeUtils.edgeShowStatusBar
import cn.jailedbird.barutils.EdgeUtils.edgeShowSystemBar
import cn.jailedbird.barutils.paddingBottomSystemWindowInsets
import cn.jailedbird.barutils.paddingTopSystemWindowInsets
import com.blankj.utilcode.util.BarUtils
import com.gyf.immersionbar.sample.R
import com.gyf.immersionbar.sample.databinding.ActivityParamsBinding

class ParamsActivity : BaseViewBindingActivity<ActivityParamsBinding>() {

    override fun initImmersionBar() {
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        super.initView()
        // 状态栏视觉冲突解决
        binding.mEdgeLayout.paddingTopSystemWindowInsets()
        binding.root.paddingBottomSystemWindowInsets()
        edgeSetNavigationBarLight(false)
        edgeSetNavigationBarColor(R.color.btn13)

        // 底部添加margin
        // binding.root.marginBottomSystemWindowInsets()
        // 设置状态栏颜色
        // edgeSetNavigationBarColor(R.color.btn13)
        initViewAgain()
    }

    private fun initViewAgain() {
        binding.mTvHasStatus.post {
            binding.mTvHasStatus.text = getText(
                "是否有StatusBar(Api不可靠, 加延时(View.post)才能获取否则为0):" + EdgeUtils.hasStatusBar(this)
            )
        }

        binding.mTvStatus.post {
            binding.mTvStatus.text = getText(
                "StatusBar Height(Api不可靠, 加延时(View.post)才能获取否则为0):" + EdgeUtils.statusBarHeight(
                    this
                )
            )
        }

        binding.mTvStatusHide.post {
            binding.mTvStatusHide.text = getText(
                "StatusBar Height(不管是否隐藏)(Api不可靠, 加延时(View.post)才能获取否则为0):" + EdgeUtils.statusBarHeightIgnoringVisibility(
                    this
                )
            )
        }

        binding.mTvHasNav.post {
            binding.mTvHasNav.text = getText(
                "是否有NavigationBar(Api不可靠, 加延时(View.post)才能获取否则为0):" + EdgeUtils.hasNavigationBar(
                    this
                )
            )
        }

        binding.mTvNav.post {
            binding.mTvNav.text = getText(
                "NavigationBar Height:(Api不可靠, 加延时(View.post)才能获取否则为0):" + EdgeUtils.navigationBarHeight(
                    this
                )
            )
        }

        binding.mTvNavHide.post {
            binding.mTvNavHide.text = getText(
                "NavigationBar Height(不管是否隐藏):(Api不可靠, 加延时(View.post)才能获取否则为0):" + EdgeUtils.navigationBarHeightIgnoringVisibility(
                    this
                )
            )
        }
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
        /*ImmersionBar(this).barParams*/
        BarUtils.getStatusBarHeight()
        /* 调整状态栏前景色*/
        binding.mTvStatusDark.setOnClickListener {
            if (EdgeUtils.isStatusBarLight(this)) {
                EdgeUtils.setStatusBarLight(this, false)
            } else {
                EdgeUtils.setStatusBarLight(this, true)
            }
            initViewAgain()
        }
        /* 调整导航栏前景色 手势导航模式下无效 导航条会自动变色 形成反差色*/
        binding.mTvNavigationDark.setOnClickListener {
            /*if (EdgeUtils.isNavigationBarLight(this)) {
                EdgeUtils.setNavigationBarLight(this, false)
            } else {
                EdgeUtils.setNavigationBarLight(this, true)
            }*/
            // 在全面屏模式系，存在2种变体 存在导航条 和 不存在导航条
            if (edgeIsNavigationBarLight()) {
                edgeSetNavigationBarLight(false)
            } else {
                edgeSetNavigationBarLight(true)
            }
            initViewAgain()
        }
        /* 显示隐藏状态栏*/
        binding.mBtnStatus.setOnClickListener {
            /*if (EdgeUtils.hasStatusBar(this@ParamsActivity)) {
                EdgeUtils.hideStatusBar(
                    this@ParamsActivity,
                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_BARS_BY_SWIPE
                )
            } else {
                EdgeUtils.showStatusBar(this@ParamsActivity)
            }*/
            if (edgeHasStatusBar()) {
                edgeHideStatusBar()
            } else {
                edgeShowStatusBar()
            }
            initViewAgain()
        }
        /* 显示隐藏导航栏*/
        binding.mBtnNav.setOnClickListener {
            /*if (EdgeUtils.hasNavigationBar(this)) {
                EdgeUtils.hideNavigationBar(this@ParamsActivity)
            } else {
                EdgeUtils.showNavigationBar(this@ParamsActivity)
            }*/
            if (edgeHasNavigationBar()) {
                edgeHideNavigationBar()
            } else {
                edgeShowNavigationBar()
            }
            initViewAgain()
        }

        /* 显示隐藏系统栏*/
        binding.mBtnSystemBar.setOnClickListener {
            /*if (EdgeUtils.hasStatusBar(this) && EdgeUtils.hasNavigationBar(this)) {
                EdgeUtils.hideSystemBar(this@ParamsActivity)
            } else {
                EdgeUtils.showSystemBar(this@ParamsActivity)
            }*/
            if (edgeHasStatusBar() && edgeHasNavigationBar()) {
                edgeHideSystemBar()
            } else {
                edgeShowSystemBar()
            }
            initViewAgain()
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