package com.cameraview_lib.engine.lock;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.cameraview_lib.engine.action.ActionWrapper;
import com.cameraview_lib.engine.action.Actions;
import com.cameraview_lib.engine.action.BaseAction;

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
public class LockAction extends ActionWrapper {

    private final BaseAction action = Actions.together(
            new ExposureLock(),
            new FocusLock(),
            new WhiteBalanceLock()
    );

    @NonNull
    @Override
    public BaseAction getAction() {
        return action;
    }
}
