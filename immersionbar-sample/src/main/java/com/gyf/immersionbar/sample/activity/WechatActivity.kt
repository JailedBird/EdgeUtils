package com.gyf.immersionbar.sample.activity

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsAnimationCompat
import androidx.core.view.WindowInsetsCompat
import cn.jailedbird.edgeutils.EdgeIme
import cn.jailedbird.edgeutils.TranslateDeferringInsetsAnimationCallback
import cn.jailedbird.edgeutils.paddingTopSystemWindowInsets
import com.blankj.utilcode.util.ToastUtils
import com.gyf.immersionbar.sample.R
import com.gyf.immersionbar.sample.databinding.ActivityWechatBinding

class WechatActivity : BaseViewBindingActivity<ActivityWechatBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_wechat
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        binding.toolbarWrapper.paddingTopSystemWindowInsets()
        binding.conversationRecyclerview.adapter = ConversationAdapter()
        // 无法直接通过这样设置 padding和动画同时作用会导致跳动 不考虑动画时 可以使用这个语句
        // binding.root.paddingBottomImeSystemWindowInsets() 这个实在动画结束时才进行ime padding的设置
        // 因此不会存在跳动
        EdgeIme.applyImeAnimation(binding.root)
        // 添加动画
        ViewCompat.setWindowInsetsAnimationCallback(
            binding.messageHolder,
            TranslateDeferringInsetsAnimationCallback(
                view = binding.messageHolder,
                persistentInsetTypes = WindowInsetsCompat.Type.systemBars(),
                deferredInsetTypes = WindowInsetsCompat.Type.ime(),
                // We explicitly allow dispatch to continue down to binding.messageHolder's
                // child views, so that step 2.5 below receives the call
                dispatchMode = WindowInsetsAnimationCompat.Callback.DISPATCH_MODE_CONTINUE_ON_SUBTREE
            )
        )
        // 注意: 对 状态栏添加Z值 否则向上滑动的时候 会暴露顶部的那一节 RecyclerView列表
        ViewCompat.setWindowInsetsAnimationCallback(
            binding.conversationRecyclerview,
            TranslateDeferringInsetsAnimationCallback(
                view = binding.conversationRecyclerview,
                persistentInsetTypes = WindowInsetsCompat.Type.systemBars(),
                deferredInsetTypes = WindowInsetsCompat.Type.ime()
            )
        )
        // TODO: 优化 获取焦点时候 首先将文本列表滑到底部 然后再进行动画
        // 这段代码貌似效果不是很好 可能是滑动和动画的时候冲突了 待办事项
        /*binding.etMessage.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                val count =
                    (binding.conversationRecyclerview.adapter as ConversationAdapter).itemCount
                // 存在焦点
                if (count > 1) {
                    binding.conversationRecyclerview.scrollToPosition(count - 1)
                }
            } else {
                //
            }
        }*/

        binding.etMessage.doImeListener { v, insets, old ->
            val ime = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
            if (old.oldHeight == 0 && ime != 0) {
                binding.etMessage.postDelayed({
                    val count =
                        (binding.conversationRecyclerview.adapter as ConversationAdapter).itemCount
                    // 存在焦点
                    if (count > 1) {
                        // 反着来的布局 Log.d("fuck", "${count} - 1")
                        binding.conversationRecyclerview.smoothScrollToPosition(0)

                    }
                }, 100)

                ToastUtils.showShort("键盘弹出")

            }
            old.oldHeight = ime
        }

    }

    override val inflate: (LayoutInflater) -> ActivityWechatBinding
        get() = ActivityWechatBinding::inflate

    /** Core common api*/
    fun View.doImeListener(
        block: (View, WindowInsetsCompat, IntWrapper) -> Unit,
    ) {
        val oldHeight = IntWrapper(0)
        // Set an actual OnApplyWindowInsetsListener which proxies to the given
        // lambda, also passing in the original padding & margin states
        ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
            block(v, insets, oldHeight)
            // Always return the insets, so that children can also use them
            insets
        }
        // request some insets
        requestApplyInsetsWhenAttached()
    }

    fun View.requestApplyInsetsWhenAttached() {
        if (isAttachedToWindow) {
            // We're already attached, just request as normal
            requestApplyInsets()
        } else {
            // We're not attached to the hierarchy, add a listener to
            // request when we are
            addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
                override fun onViewAttachedToWindow(v: View) {
                    v.removeOnAttachStateChangeListener(this)
                    v.requestApplyInsets()
                }

                override fun onViewDetachedFromWindow(v: View) = Unit
            })
        }
    }

    data class IntWrapper(var oldHeight: Int = 0)

}