package cn.jailedbird.barutils

import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

object EdgeIme {
    /**
     * TODO 1.apply margin and padding
     *      2.save origin padding or margin
     */
    fun applyImeAnimation(view: View) {
        val deferringInsetsListener = RootViewDeferringInsetsCallback(
            persistentInsetTypes = WindowInsetsCompat.Type.systemBars(),
            deferredInsetTypes = WindowInsetsCompat.Type.ime()
        )
        // RootViewDeferringInsetsCallback is both an WindowInsetsAnimation.Callback and an
        // OnApplyWindowInsetsListener, so needs to be set as so.
        ViewCompat.setWindowInsetsAnimationCallback(view, deferringInsetsListener)
        ViewCompat.setOnApplyWindowInsetsListener(view, deferringInsetsListener)

    }
}