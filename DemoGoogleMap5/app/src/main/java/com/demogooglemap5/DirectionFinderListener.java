package com.demogooglemap5;

import java.util.List;

/**
 * Created by Administrator on 6/8/2016.
 */
public interface DirectionFinderListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route);
}

