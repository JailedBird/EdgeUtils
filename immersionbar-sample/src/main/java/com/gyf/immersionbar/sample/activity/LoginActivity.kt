package com.gyf.immersionbar.sample.activity

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import cn.jailedbird.edgeutils.marginBottomImeSystemWindowInsets
import cn.jailedbird.edgeutils.paddingTopSystemWindowInsets
import com.gyf.immersionbar.sample.R
import com.gyf.immersionbar.sample.databinding.ActivityLoginBinding
import cn.jailedbird.edgeutils.EdgeUtils.edgeSetNavigationBarColor
import cn.jailedbird.edgeutils.EdgeUtils.edgeSetNavigationBarColorInt
import cn.jailedbird.edgeutils.EdgeUtils.edgeSetNavigationBarLight
import cn.jailedbird.edgeutils.paddingBottomImeSystemWindowInsets

class LoginActivity : BaseViewBindingActivity<ActivityLoginBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        binding.toolbarWrapper.paddingTopSystemWindowInsets()
        binding.root.paddingBottomImeSystemWindowInsets(true)
        // edgeSetNavigationBarColorInt(Color.parseColor("#db586f"))
        edgeSetNavigationBarLight(true)
        ViewCompat.setOnApplyWindowInsetsListener(binding.mTvInsetsNavigation) { _: View?, windowInsetsCompat: WindowInsetsCompat ->
            binding.mTvInsetsNavigation.text =
                "navigation insets:" + ViewCompat.getRootWindowInsets(window.decorView)
                    ?.getInsets(WindowInsetsCompat.Type.systemBars())
            windowInsetsCompat
        }
        ViewCompat.setOnApplyWindowInsetsListener(binding.mTvInsetsIme) { _: View?, windowInsetsCompat: WindowInsetsCompat ->
            binding.mTvInsetsIme.text =
                "ime insets:" + ViewCompat.getRootWindowInsets(window.decorView)
                    ?.getInsets(WindowInsetsCompat.Type.ime())
            windowInsetsCompat
        }
        ViewCompat.setOnApplyWindowInsetsListener(binding.mTvInsetsNavigationAndIme) { _: View?, windowInsetsCompat: WindowInsetsCompat ->
            binding.mTvInsetsNavigationAndIme.text =
                "navigation and ime insets:" + ViewCompat.getRootWindowInsets(window.decorView)
                    ?.getInsets(WindowInsetsCompat.Type.ime() or WindowInsetsCompat.Type.systemBars())
            windowInsetsCompat
        }
    }

    /*@WindowInsetsCompat.Type.InsetsType
    private fun getTT(): Int {
        val ime = WindowInsetsCompat.Type.ime()
        val bar = WindowInsetsCompat.Type.systemBars()
        return ime|bar
    }*/

    override val inflate: (LayoutInflater) -> ActivityLoginBinding
        get() = ActivityLoginBinding::inflate
}