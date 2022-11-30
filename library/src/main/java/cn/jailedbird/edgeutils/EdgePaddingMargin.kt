package cn.jailedbird.edgeutils

import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams

/** Margin*/
fun View.marginTopSystemWindowInsets() =
    applySystemWindowInsetsMargin(applyTop = true)


fun View.marginBottomSystemWindowInsets() =
    applySystemWindowInsetsMargin(applyBottom = true)


fun View.marginVerticalSystemWindowInsets() =
    applySystemWindowInsetsMargin(applyTop = true, applyBottom = true)


fun View.applySystemWindowInsetsMargin(
    applyLeft: Boolean = false,
    applyTop: Boolean = false,
    applyRight: Boolean = false,
    applyBottom: Boolean = false,
) {
    doOnApplyWindowInsets { view, insets, _, margin ->
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        val left = if (applyLeft) systemBars.left else 0
        val top = if (applyTop) systemBars.top else 0
        val right = if (applyRight) systemBars.right else 0
        val bottom = if (applyBottom) systemBars.bottom else 0

        view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            leftMargin = margin.left + left
            topMargin = margin.top + top
            rightMargin = margin.right + right
            bottomMargin = margin.bottom + bottom
        }
    }
}

/** Padding*/
fun View.paddingTopSystemWindowInsets() =
    applySystemWindowInsetsPadding(applyTop = true)


fun View.paddingBottomSystemWindowInsets() =
    applySystemWindowInsetsPadding(applyBottom = true)


fun View.paddingVerticalSystemWindowInsets() =
    applySystemWindowInsetsPadding(applyTop = true, applyBottom = true)


fun View.applySystemWindowInsetsPadding(
    applyLeft: Boolean = false,
    applyTop: Boolean = false,
    applyRight: Boolean = false,
    applyBottom: Boolean = false,
) {
    doOnApplyWindowInsets { view, insets, padding, _ ->
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        val left = if (applyLeft) systemBars.left else 0
        val top = if (applyTop) systemBars.top else 0
        val right = if (applyRight) systemBars.right else 0
        val bottom = if (applyBottom) systemBars.bottom else 0

        view.setPadding(
            padding.left + left,
            padding.top + top,
            padding.right + right,
            padding.bottom + bottom
        )
    }
}

/** Margin & Padding*/
fun View.applySystemWindowInsetsPaddingMargin(
    applyLeftPadding: Boolean = false,
    applyTopPadding: Boolean = false,
    applyRightPadding: Boolean = false,
    applyBottomPadding: Boolean = false,
    applyLeftMargin: Boolean = false,
    applyTopMargin: Boolean = false,
    applyRightMargin: Boolean = false,
    applyBottomMargin: Boolean = false,
) {
    doOnApplyWindowInsets { view, insets, padding, margin ->
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

        /** Padding*/
        val leftPadding = if (applyLeftPadding) systemBars.left else 0
        val topPadding = if (applyTopPadding) systemBars.top else 0
        val rightPadding = if (applyRightPadding) systemBars.right else 0
        val bottomPadding = if (applyBottomPadding) systemBars.bottom else 0

        view.setPadding(
            padding.left + leftPadding,
            padding.top + topPadding,
            padding.right + rightPadding,
            padding.bottom + bottomPadding
        )
        /** Margin*/
        val left = if (applyLeftMargin) systemBars.left else 0
        val top = if (applyTopMargin) systemBars.top else 0
        val right = if (applyRightMargin) systemBars.right else 0
        val bottom = if (applyBottomMargin) systemBars.bottom else 0

        view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            leftMargin = margin.left + left
            topMargin = margin.top + top
            rightMargin = margin.right + right
            bottomMargin = margin.bottom + bottom
        }
    }
}

/** Margin ime
 *
 *  [withNavigationHeight] margin distance with navigation bar height
 *
 *  [withNavigationHeight] default is true(with navigation height)
 *  */
fun View.marginBottomImeSystemWindowInsets(withNavigationHeight: Boolean = true) {
    doOnApplyWindowInsets { view, insets, _, margin ->
        val systemBars = if (withNavigationHeight) {
            insets.getInsets(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.ime())
        } else {
            insets.getInsets(WindowInsetsCompat.Type.ime())
        }

        val bottom = systemBars.bottom
        view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            bottomMargin = margin.bottom + bottom
        }
    }
}

/** Padding ime
 *
 *  [withNavigationHeight] margin distance with navigation bar height
 *
 *  [withNavigationHeight] default is true(with navigation height)
 *  */
fun View.paddingBottomImeSystemWindowInsets(withNavigationHeight: Boolean = true) {
    doOnApplyWindowInsets { view, insets, padding, _ ->
        val systemBars = if (withNavigationHeight) {
            insets.getInsets(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.ime())
        } else {
            insets.getInsets(WindowInsetsCompat.Type.ime())
        }

        view.setPadding(
            padding.left,
            padding.top,
            padding.right,
            padding.bottom + systemBars.bottom
        )
    }
}

/**
 * Height->
 * */
fun View.heightToTopSystemWindowInsets() {
    doOnApplyWindowInsets { view, insets, _, _ ->
        val systemBars =
            insets.getInsets(WindowInsetsCompat.Type.systemBars())
        view.layoutParams = view.layoutParams.apply {
            height = systemBars.top
        }
    }
}

/**
 * Height->
 * */
fun View.heightToBottomSystemWindowInsets() {
    doOnApplyWindowInsets { view, insets, _, _ ->
        val systemBars =
            insets.getInsets(WindowInsetsCompat.Type.systemBars())
        view.layoutParams = view.layoutParams.apply {
            height = systemBars.bottom
        }
    }
}

/** Core common api*/
fun View.doOnApplyWindowInsets(
    block: (View, WindowInsetsCompat, InitialPadding, InitialMargin) -> Unit,
) {
    // Create a snapshot of the view's padding & margin states
    val initialPadding = recordInitialPaddingForView(this)
    val initialMargin = recordInitialMarginForView(this)
    // Set an actual OnApplyWindowInsetsListener which proxies to the given
    // lambda, also passing in the original padding & margin states
    ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
        block(v, insets, initialPadding, initialMargin)
        // Always return the insets, so that children can also use them
        insets
    }
    // request some insets
    requestApplyInsetsWhenAttached()
}

class InitialPadding(val left: Int, val top: Int, val right: Int, val bottom: Int)

class InitialMargin(val left: Int, val top: Int, val right: Int, val bottom: Int)

private fun recordInitialPaddingForView(view: View) = InitialPadding(
    view.paddingLeft, view.paddingTop, view.paddingRight, view.paddingBottom
)

private fun recordInitialMarginForView(view: View): InitialMargin {
    val lp = view.layoutParams as? ViewGroup.MarginLayoutParams
        ?: throw IllegalArgumentException("Invalid view layout params")
    return InitialMargin(lp.leftMargin, lp.topMargin, lp.rightMargin, lp.bottomMargin)
}

private fun View.requestApplyInsetsWhenAttached() {
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
