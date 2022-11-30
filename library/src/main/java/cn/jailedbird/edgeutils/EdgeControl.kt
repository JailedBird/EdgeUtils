@file:Suppress("MemberVisibilityCanBePrivate")

package cn.jailedbird.edgeutils

import android.view.Window
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

internal object EdgeControl {
    /** Status bar*/
    /**
     * About status bar WindowInsetsCompat [ViewCompat.getRootWindowInsets] from the top of the view
     * hierarchy or null if View is detached
     *
     * When use it, using View.post{ getCustomRootInset() } to
     * get NonNull WindowInsetsCompat
     */
    fun Window.edgeStatusBarsIsVisible() =
        getCustomRootWindowInsets()
            ?.isVisible(WindowInsetsCompat.Type.statusBars())
            ?: true

    fun Window.edgeStatusBarHeight() = getCustomRootWindowInsets()
        ?.getInsets(WindowInsetsCompat.Type.systemBars())?.top ?: 0

    fun Window.edgeStatusBarHeightIgnoringVisibility(): Int =
        getCustomRootWindowInsets()
            ?.getInsetsIgnoringVisibility(WindowInsetsCompat.Type.systemBars())?.top ?: 0

    fun Window.edgeShowStatusBar() =
        WindowCompat.getInsetsController(this, this.decorView)
            ?.show(WindowInsetsCompat.Type.statusBars())

    /**
     * [behavior] Determines how the bars behave when being hidden by the application.
     * */
    fun Window.edgeHideStatusBar(behavior: Int = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE) {
        WindowCompat.getInsetsController(this, this.decorView)?.let {
            it.systemBarsBehavior = behavior
            it.hide(WindowInsetsCompat.Type.statusBars())
        }
    }


    /** Navigation bar*/
    fun Window.edgeNavigationBarsIsVisible() =
        getCustomRootWindowInsets()
            ?.isVisible(WindowInsetsCompat.Type.navigationBars())
            ?: true

    fun Window.edgeNavigationBarHeight() = getCustomRootWindowInsets()
        ?.getInsets(WindowInsetsCompat.Type.systemBars())?.bottom ?: 0

    fun Window.edgeNavigationBarHeightIgnoringVisibility(): Int =
        getCustomRootWindowInsets()
            ?.getInsetsIgnoringVisibility(WindowInsetsCompat.Type.systemBars())?.bottom ?: 0

    fun Window.edgeShowNavigationBar() =
        WindowCompat.getInsetsController(this, this.decorView)
            ?.show(WindowInsetsCompat.Type.navigationBars())

    /**
     * [behavior] Determines how the bars behave when being hidden by the application.
     * */
    fun Window.edgeHideNavigationBar(behavior: Int = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE) {
        WindowCompat.getInsetsController(this, this.decorView)?.let {
            it.systemBarsBehavior = behavior
            it.hide(WindowInsetsCompat.Type.navigationBars())
        }
    }

    /** System bar*/
    fun Window.showSystemBar() {
        WindowCompat.getInsetsController(this, this.decorView)
            ?.show(WindowInsetsCompat.Type.systemBars())
    }

    fun Window.edgeHideSystemBar(behavior: Int = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE) {
        WindowCompat.getInsetsController(this, this.decorView)?.let {
            it.systemBarsBehavior = behavior
            it.hide(WindowInsetsCompat.Type.systemBars())
        }
    }

    /** About ime bar*/
    fun Window.edgeImeBarIsVisible() =
        getCustomRootWindowInsets()?.isVisible(WindowInsetsCompat.Type.ime())
            ?: false

    fun Window.edgeImeHeight() =
        getCustomRootWindowInsets()
            ?.getInsets(WindowInsetsCompat.Type.ime())?.bottom ?: 0

    fun Window.edgeShowIme() =
        WindowCompat.getInsetsController(this, this.decorView)
            ?.show(WindowInsetsCompat.Type.ime())

    fun Window.edgeHideIme() =
        WindowCompat.getInsetsController(this, this.decorView)
            ?.hide(WindowInsetsCompat.Type.ime())

    /**
     * Bar light and dark mode
     *
     * [isLight] true: system bar content draw as black color; else draw as white color
     * */
    fun setSystemBarLight(window: Window, isLight: Boolean) {
        WindowCompat.getInsetsController(window, window.decorView).let {
            it?.isAppearanceLightStatusBars = isLight
            it?.isAppearanceLightNavigationBars = isLight
        }
    }

    fun setNavigationBarLight(window: Window, isLight: Boolean) {
        WindowCompat.getInsetsController(window, window.decorView)
            ?.isAppearanceLightNavigationBars = isLight
    }

    fun setStatusBarLight(window: Window, isLight: Boolean) {
        WindowCompat.getInsetsController(window, window.decorView)
            ?.isAppearanceLightStatusBars = isLight
    }

    fun isAppearanceLightNavigationBars(window: Window) =
        WindowCompat.getInsetsController(window, window.decorView)?.isAppearanceLightNavigationBars
            ?: true

    fun isAppearanceLightStatusBars(window: Window) =
        WindowCompat.getInsetsController(window, window.decorView)?.isAppearanceLightStatusBars
            ?: true

    /**
     * hook [ViewCompat.getRootWindowInsets], some advantage as follows:
     * 1 avoid api change cause extensive modifications
     * 2 hook api do some judge, such as throw exception or toast some developer's tips
     * */
    private fun Window.getCustomRootWindowInsets(): WindowInsetsCompat? {
        val res = ViewCompat.getRootWindowInsets(this.decorView)
        if (res == null) {
            Toast.makeText(
                context,
                "ViewCompat.getRootWindowInsets(this.decorView) is null, please use View.post{ called_api } to ensure View has Attached",
                Toast.LENGTH_SHORT
            ).show()
        }
        return res
    }

}