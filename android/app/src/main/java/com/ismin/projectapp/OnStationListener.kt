package com.ismin.projectapp

import android.widget.Button

public interface OnStationListener{
    public fun onStationClick(item: Station , position: Int)
    public fun likeClick(item: Station, favBtn: Button)
}