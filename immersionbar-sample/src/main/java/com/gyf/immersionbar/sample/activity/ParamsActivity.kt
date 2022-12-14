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
            "?????????StatusBar(Api?????????, ?????????(View.post)?????????????????????0):" + EdgeUtils.hasStatusBar(this@ParamsActivity)
        )
        binding.mTvStatus.text = getText(
            "StatusBar Height(Api?????????, ?????????(View.post)?????????????????????0):" + EdgeUtils.statusBarHeight(
                this@ParamsActivity
            )
        )
        binding.mTvStatusHide.text = getText(
            "StatusBar Height(??????????????????)(Api?????????, ?????????(View.post)?????????????????????0):" + EdgeUtils.statusBarHeightIgnoringVisibility(
                this@ParamsActivity
            )
        )
        binding.mTvHasNav.text = getText(
            "?????????NavigationBar(Api?????????, ?????????(View.post)?????????????????????0):" + edgeHasNavigationBar()
        )
        binding.mTvNav.text = getText(
            "NavigationBar Height:(Api?????????, ?????????(View.post)?????????????????????0):" + edgeNavigationBarHeight()
        )
        binding.mTvNavHide.text = getText(
            "NavigationBar Height(??????????????????):(Api?????????, ?????????(View.post)?????????????????????0):" + edgeNavigationBarHeightIgnoringVisibility()
        )
    }

    @SuppressLint("SetTextI18n")
    override fun setListener() {
        super.setListener()
        ViewCompat.setOnApplyWindowInsetsListener(binding.mTvInsets) { _: View?, windowInsetsCompat: WindowInsetsCompat ->
            binding.mTvInsets.text =
                getText(
                    "WindowInsetTop???" + ViewCompat.getRootWindowInsets(window.decorView)
                        ?.getInsets(WindowInsetsCompat.Type.systemBars())
                )
            windowInsetsCompat
        }
        BarUtils.getStatusBarHeight()
        /* ????????????????????????*/
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
        /* ???????????????????????? ??????????????????????????? ???????????????????????? ???????????????*/
        binding.mTvNavigationDark.setOnClickListener {
            // ??????????????????????????????2????????? ??????????????? ??? ??????????????????
            if (edgeIsNavigationBarLight()) {
                edgeSetNavigationBarLight(false)
            } else {
                edgeSetNavigationBarLight(true)
            }
            lifecycleScope.launch {
                initViewAgain()
            }
        }
        /* ?????????????????????*/
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
        /* ?????????????????????*/
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

        /* ?????????????????????*/
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