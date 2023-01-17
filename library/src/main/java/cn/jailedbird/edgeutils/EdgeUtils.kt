@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package cn.jailedbird.edgeutils

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.os.Build
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import cn.jailedbird.edgeutils.EdgeControl.edgeHideNavigationBar
import cn.jailedbird.edgeutils.EdgeControl.edgeHideStatusBar
import cn.jailedbird.edgeutils.EdgeControl.edgeHideSystemBar
import cn.jailedbird.edgeutils.EdgeControl.edgeNavigationBarHeight
import cn.jailedbird.edgeutils.EdgeControl.edgeNavigationBarHeightIgnoringVisibility
import cn.jailedbird.edgeutils.EdgeControl.edgeNavigationBarsIsVisible
import cn.jailedbird.edgeutils.EdgeControl.edgeShowNavigationBar
import cn.jailedbird.edgeutils.EdgeControl.edgeShowStatusBar
import cn.jailedbird.edgeutils.EdgeControl.edgeStatusBarHeight
import cn.jailedbird.edgeutils.EdgeControl.edgeStatusBarHeightIgnoringVisibility
import cn.jailedbird.edgeutils.EdgeControl.edgeStatusBarsIsVisible
import cn.jailedbird.edgeutils.EdgeControl.getSuspendCustomRootWindowInsets
import cn.jailedbird.edgeutils.EdgeControl.isAppearanceLightNavigationBars
import cn.jailedbird.edgeutils.EdgeControl.isAppearanceLightStatusBars
import cn.jailedbird.edgeutils.EdgeControl.setNavigationBarLight
import cn.jailedbird.edgeutils.EdgeControl.setStatusBarLight
import cn.jailedbird.edgeutils.EdgeControl.setSystemBarLight
import cn.jailedbird.edgeutils.EdgeControl.showSystemBar


/**
 * Note: method annotated with @JvmStatic need with [] call
 *
 * extension method name start with "edge" can direct call in activity, those has same function
 * */
object EdgeUtils {
    @JvmStatic
    fun setEdgeToEdge(activity: Activity, withScrim: Boolean = true) =
        activity.edgeToEdge(withScrim)

    @JvmStatic
    fun setEdgeToEdge(dialog: Dialog, withScrim: Boolean = true) = dialog.edgeToEdge(withScrim)

    /**
     * Make activity implement edge-to-edge layout--> first step
     *
     * [withScrim] if true, set background as TRANSPARENT(alpha=0) else TRANSPARENT(alpha=1)
     * */
    fun Activity.edgeToEdge(withScrim: Boolean = true) {
        /**
         * To fix [hide status bar cause black background] please reference this video
         * [youtube course](https://www.youtube.com/watch?v=yukwno2GBoI)
         * or [stackoverflow doc](https://stackoverflow.com/a/72773422/15859474)
         * */
        /** google doc about cutout: https://developer.android.com/develop/ui/views/layout/display-cutout*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.attributes.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }
        setWindowEdgeToEdge(this.window, withScrim)
    }

    fun Dialog.edgeToEdge(withScrim: Boolean = true) {
        val window = this.window
        if (window != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                window.attributes.layoutInDisplayCutoutMode =
                    WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            }
            setWindowEdgeToEdge(window, withScrim)
        } else {
            Toast.makeText(
                this.context, "Edge: ensure dialog window is not null", Toast.LENGTH_SHORT
            ).show()
        }

    }

    /**
     * Edge to edge as google document: [edge-to-edge](https://developer.android.com/develop/ui/views/layout/edge-to-edge#lay-out-in-full-screen)
     * */
    private fun setWindowEdgeToEdge(window: Window, withScrim: Boolean) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        /** using not transparent avoid scrim*/
        val color = if (withScrim) {
            Color.TRANSPARENT
        } else {
            Color.parseColor("#01000000")
        }
        window.statusBarColor = color
        window.navigationBarColor = color
    }

    @JvmStatic
    suspend fun windowInsetCompat(activity: Activity) = activity.window.edgeStatusBarsIsVisible()
    suspend fun Activity.edgeWindowInsetCompat(): WindowInsetsCompat? =
        this.window.getSuspendCustomRootWindowInsets()

    /** judge has status bar*/
    @JvmStatic
    suspend fun hasStatusBar(activity: Activity) = activity.window.edgeStatusBarsIsVisible()
    suspend fun Activity.edgeHasStatusBar() = window.edgeStatusBarsIsVisible()

    /** get status bar height, please call it with View.post{}, otherwise it perhaps get 0 when
     * it not attach to view tree*/
    @JvmStatic
    suspend fun statusBarHeight(activity: Activity) = activity.window.edgeStatusBarHeight()
    suspend fun Activity.edgeStatusBarHeight(): Int = window.edgeStatusBarHeight()

    /** show status bar*/
    @JvmStatic
    fun showStatusBar(activity: Activity) = activity.window.edgeShowStatusBar()
    fun Activity.edgeShowStatusBar() = this.window.edgeShowStatusBar()

    /** hide status bar
     * [behavior] Determines how the bars behave when being hidden by the application. */
    @JvmStatic
    fun hideStatusBar(
        activity: Activity,
        behavior: Int = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    ) = activity.window.edgeHideStatusBar(behavior)

    fun Activity.edgeHideStatusBar() = this.window.edgeHideStatusBar()

    /** get status bar height(even if status has hide), please call it with View.post{}, otherwise
     * it perhaps get 0 when it not attach to view tree*/
    @JvmStatic
    suspend fun statusBarHeightIgnoringVisibility(activity: Activity) =
        activity.window.edgeStatusBarHeightIgnoringVisibility()

    suspend fun Activity.edgeStatusBarHeightIgnoringVisibility() =
        this.window.edgeStatusBarHeightIgnoringVisibility()

    /** ----------------------------------Navigation bar-------------------------------------------*/

    /** judge has navigation bar*/
    @JvmStatic
    suspend fun hasNavigationBar(activity: Activity) = activity.window.edgeNavigationBarsIsVisible()
    suspend fun Activity.edgeHasNavigationBar() = this.window.edgeNavigationBarsIsVisible()

    /** get navigation height, please call it with View.post{}, otherwise it perhaps get 0 when
     * it not attach to view tree*/
    @JvmStatic
    suspend fun navigationBarHeight(activity: Activity) = activity.window.edgeNavigationBarHeight()
    suspend fun Activity.edgeNavigationBarHeight(): Int = window.edgeNavigationBarHeight()

    /** get navigation height(even if navigation has hide), please call it with View.post{}, otherwise
     * it perhaps get 0 when it not attach to view tree*/
    @JvmStatic
    suspend fun navigationBarHeightIgnoringVisibility(activity: Activity) =
        activity.window.edgeNavigationBarHeightIgnoringVisibility()

    suspend fun Activity.edgeNavigationBarHeightIgnoringVisibility() =
        this.window.edgeNavigationBarHeightIgnoringVisibility()

    /** show navigation bar*/
    @JvmStatic
    fun showNavigationBar(activity: Activity) = activity.window.edgeShowNavigationBar()
    fun Activity.edgeShowNavigationBar() = this.window.edgeShowNavigationBar()

    /**
     * hide navigation bar
     * [behavior] Determines how the bars behave when being hidden by the application.
     * */
    @JvmStatic
    fun hideNavigationBar(
        activity: Activity,
        behavior: Int = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    ) = activity.window.edgeHideNavigationBar(behavior)

    fun Activity.edgeHideNavigationBar(behavior: Int = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE) =
        this.window.edgeHideNavigationBar(behavior)

    /** ----------------------------------System bar-------------------------------------------*/
    /**
     * hide system bar
     *
     * [behavior] Determines how the bars behave when being hidden by the application.
     * */
    @JvmStatic
    fun hideSystemBar(
        activity: Activity,
        behavior: Int = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    ) = activity.window.edgeHideSystemBar(behavior)

    fun Activity.edgeHideSystemBar(behavior: Int = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE) =
        this.window.edgeHideSystemBar(behavior)

    /** show system bar*/
    @JvmStatic
    fun showSystemBar(activity: Activity) = activity.window.showSystemBar()

    fun Activity.edgeShowSystemBar() = this.window.showSystemBar()


    /** Color Status bar*/
    @JvmStatic
    fun setStatusBarColorInt(activity: Activity, @ColorInt color: Int) =
        activity.window.edgeSetStatusBarColor(color)

    @JvmStatic
    fun setStatusBarColorInt(dialog: Dialog, @ColorInt color: Int) =
        dialog.window?.edgeSetStatusBarColor(color)

    @JvmStatic
    fun setStatusBarColor(activity: Activity, @ColorRes color: Int) =
        activity.window.edgeSetStatusBarColor(ContextCompat.getColor(activity, color))

    @JvmStatic
    fun setStatusBarColor(dialog: Dialog, @ColorRes color: Int) =
        dialog.window?.edgeSetStatusBarColor(ContextCompat.getColor(dialog.context, color))

    fun Activity.edgeSetStatusBarColor(@ColorRes color: Int) =
        this.window.edgeSetStatusBarColor(ContextCompat.getColor(this, color))

    fun Dialog.edgeSetStatusBarColor(@ColorRes color: Int) =
        this.window?.edgeSetStatusBarColor(ContextCompat.getColor(this.context, color))

    fun Activity.edgeSetStatusBarColorInt(@ColorInt colorInt: Int) =
        this.window.edgeSetStatusBarColor(colorInt)

    fun Dialog.edgeSetStatusBarColorInt(@ColorInt colorInt: Int) =
        this.window?.edgeSetStatusBarColor(colorInt)

    private fun Window.edgeSetStatusBarColor(@ColorInt colorInt: Int) {
        this.statusBarColor = colorInt
    }

    /** Color Navigation bar*/
    @JvmStatic
    fun setNavigationBarColorInt(activity: Activity, @ColorInt color: Int) =
        activity.window.edgeSetNavigationBarColor(color)

    @JvmStatic
    fun setNavigationBarColorInt(dialog: Dialog, @ColorInt color: Int) =
        dialog.window?.edgeSetNavigationBarColor(color)

    @JvmStatic
    fun setNavigationBarColor(activity: Activity, @ColorRes color: Int) =
        activity.window.edgeSetNavigationBarColor(ContextCompat.getColor(activity, color))

    @JvmStatic
    fun setNavigationBarColor(dialog: Dialog, @ColorRes color: Int) =
        dialog.window?.edgeSetNavigationBarColor(ContextCompat.getColor(dialog.context, color))

    fun Activity.edgeSetNavigationBarColor(@ColorRes color: Int) =
        this.window.edgeSetNavigationBarColor(ContextCompat.getColor(this, color))

    fun Dialog.edgeSetNavigationBarColor(@ColorRes color: Int) =
        this.window?.edgeSetNavigationBarColor(ContextCompat.getColor(this.context, color))

    fun Activity.edgeSetNavigationBarColorInt(@ColorInt colorInt: Int) =
        this.window.edgeSetNavigationBarColor(colorInt)

    fun Dialog.edgeSetNavigationBarColorInt(@ColorInt colorInt: Int) =
        this.window?.edgeSetNavigationBarColor(colorInt)

    private fun Window.edgeSetNavigationBarColor(@ColorInt colorInt: Int) {
        this.navigationBarColor = colorInt
    }

    /** Color System bar*/
    @JvmStatic
    fun setSystemBarColorInt(activity: Activity, @ColorInt color: Int) =
        activity.window.edgeSetSystemBarColor(color)

    @JvmStatic
    fun setSystemBarColorInt(dialog: Dialog, @ColorInt color: Int) =
        dialog.window?.edgeSetSystemBarColor(color)

    @JvmStatic
    fun setSystemBarColor(activity: Activity, @ColorRes color: Int) =
        activity.window.edgeSetSystemBarColor(ContextCompat.getColor(activity, color))

    @JvmStatic
    fun setSystemBarColor(dialog: Dialog, @ColorRes color: Int) =
        dialog.window?.edgeSetSystemBarColor(ContextCompat.getColor(dialog.context, color))

    fun Activity.edgeSetSystemBarColor(@ColorRes color: Int) =
        this.window.edgeSetSystemBarColor(ContextCompat.getColor(this, color))

    fun Dialog.edgeSetSystemBarColor(@ColorRes color: Int) =
        this.window?.edgeSetSystemBarColor(ContextCompat.getColor(this.context, color))

    fun Activity.edgeSetSystemBarColorInt(@ColorInt colorInt: Int) =
        this.window.edgeSetSystemBarColor(colorInt)

    fun Dialog.edgeSetSystemBarColorInt(@ColorInt colorInt: Int) =
        this.window?.edgeSetSystemBarColor(colorInt)

    private fun Window.edgeSetSystemBarColor(@ColorInt colorInt: Int) {
        this.statusBarColor = colorInt
        this.navigationBarColor = colorInt
    }


    /** System bar front color*/
    @JvmStatic
    fun setSystemBarLight(activity: Activity, isLight: Boolean) =
        setSystemBarLight(activity.window, isLight)

    @JvmStatic
    fun setSystemBarLight(dialog: Dialog, isLight: Boolean) = dialog.window?.let {
        setSystemBarLight(it, isLight)
    }

    fun Activity.edgeSetSystemBarLight(isLight: Boolean) = setSystemBarLight(this.window, isLight)

    fun Dialog.edgeSetSystemBarLight(isLight: Boolean) = window?.let {
        setSystemBarLight(it, isLight)
    }

    /** Navigation bar front color*/
    @JvmStatic
    fun setNavigationBarLight(activity: Activity, isLight: Boolean) =
        setNavigationBarLight(activity.window, isLight)

    @JvmStatic
    fun setNavigationBarLight(dialog: Dialog, isLight: Boolean) = dialog.window?.let {
        setNavigationBarLight(it, isLight)
    }

    fun Activity.edgeSetNavigationBarLight(isLight: Boolean) =
        setNavigationBarLight(window, isLight)

    fun Dialog.edgeSetNavigationBarLight(isLight: Boolean) = window?.let {
        setNavigationBarLight(it, isLight)
    }

    /** Status bar front color*/
    @JvmStatic
    fun setStatusBarLight(activity: Activity, isLight: Boolean) =
        setStatusBarLight(activity.window, isLight)

    @JvmStatic
    fun setStatusBarLight(dialog: Dialog, isLight: Boolean) = dialog.window?.let {
        setStatusBarLight(it, isLight)
    }


    fun Activity.edgeSetStatusBarLight(isLight: Boolean) = setStatusBarLight(this.window, isLight)
    fun Dialog.edgeSetStatusBarLight(isLight: Boolean) = window?.let {
        setStatusBarLight(it, isLight)
    }

    /** judge is Status bar is light*/
    @JvmStatic
    fun isStatusBarLight(activity: Activity) = isAppearanceLightStatusBars(activity.window)

    fun Activity.edgeIsStatusBarLight() = isAppearanceLightStatusBars(this.window)

    /** judge is Navigation bar is light*/
    @JvmStatic
    fun isNavigationBarLight(activity: Activity) = isAppearanceLightNavigationBars(activity.window)

    fun Activity.edgeIsNavigationBarLight() = isAppearanceLightNavigationBars(this.window)

}

