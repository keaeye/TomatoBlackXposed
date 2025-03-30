package com.black.tomatobook;

import android.view.View;
import android.widget.TextView;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class MainHook {
    public static void init(XC_LoadPackage.LoadPackageParam lpparam) {
        try {
            Class<?> activityClass = XposedHelpers.findClass("com.dragon.read.ui.activity.ReadActivity", lpparam.classLoader);
            
            XposedHelpers.findAndHookMethod(activityClass, "onCreate", android.os.Bundle.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    View rootView = (View) XposedHelpers.getObjectField(param.thisObject, "mRootView");
                    if (rootView != null) {
                        rootView.setBackgroundColor(0xFF000000);
                    }
                }
            });

            XposedHelpers.findAndHookMethod(TextView.class, "setTextColor", int.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) {
                    param.args[0] = 0xFFFFFFFF;
                }
            });

        } catch (Throwable t) {
            XposedHelpers.log("TomatoBlackXposed Hook Failed: " + t.getMessage());
        }
    }
}
