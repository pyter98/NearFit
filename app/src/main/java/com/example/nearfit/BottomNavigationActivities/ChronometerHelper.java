package com.example.nearfit.BottomNavigationActivities;

import androidx.annotation.Nullable;

public class ChronometerHelper {

    @Nullable
    private static Long mStartTime;

    @Nullable
    public Long getStartTime() {
        return mStartTime;
    }

    public void setStartTime(final long startTime) {
        this.mStartTime = startTime;
    }
}