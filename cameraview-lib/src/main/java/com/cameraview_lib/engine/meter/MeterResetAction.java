package com.cameraview_lib.engine.meter;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.cameraview_lib.engine.action.ActionWrapper;
import com.cameraview_lib.engine.action.Actions;
import com.cameraview_lib.engine.action.BaseAction;

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
public class MeterResetAction extends ActionWrapper {

    private final BaseAction action;

    public MeterResetAction() {
        this.action = Actions.together(
                new ExposureReset(),
                new FocusReset(),
                new WhiteBalanceReset()
        );
    }

    @NonNull
    @Override
    public BaseAction getAction() {
        return action;
    }
}
