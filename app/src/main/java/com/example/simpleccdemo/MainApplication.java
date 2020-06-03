package com.example.simpleccdemo;

import com.billy.cc.core.component.CC;
import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * Author: ryan.lei
 * Date: 2020/5/27 18:12
 * Description:
 */
public class MainApplication extends TinkerApplication {
    public MainApplication() {
        super(
                //tinkerFlags, which types is supported
                //dex only, library only, all support
                ShareConstants.TINKER_ENABLE_ALL,
                // This is passed as a string so the shell application does not
                // have a binary dependency on your ApplicationLifeCycle class.
                "com.example.simpleccdemo.MyApplicationLike");
    }
}
