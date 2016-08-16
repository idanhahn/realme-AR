package com.realme_demo.realmeapp.vu;

/**
 * Created by idanhahn on 8/11/2016.
 */

import com.vuforia.State;

public interface VuControl {

    // init trackers
    boolean doInitTrackers();

    // load trackers
    boolean doLoadTrackersData();

    // start trackers (after init and load
    boolean doStartTrackers();

    // stop trackers
    boolean doStopTrackers();

    // destroy trackers
    boolean doUnloadTrackersData();

    // deinit trackers
    boolean doDeinitTrackers();

    // callback after init and load
    void onInitARDone(VuException e);

    // callback for every cycle
    void onVuforiaUpdate(State state);

}
