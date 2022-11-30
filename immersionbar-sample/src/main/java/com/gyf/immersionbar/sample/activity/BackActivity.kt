package com.gyf.immersionbar.sample.activity

import android.os.Bundle
import cn.jailedbird.edgeutils.EdgeUtils
import cn.jailedbird.edgeutils.paddingTopSystemWindowInsets
import com.gyf.immersionbar.sample.R
import me.imid.swipebacklayout.lib.app.SwipeBackActivity

class BackActivity : SwipeBackActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EdgeUtils.setEdgeToEdge(this)
        setContentView(R.layout.activity_swipe_back)
        findViewById(R.id.toolbarWrapper).paddingTopSystemWindowInsets()
        EdgeUtils.setNavigationBarColor(this, R.color.colorPrimary)
    }
}