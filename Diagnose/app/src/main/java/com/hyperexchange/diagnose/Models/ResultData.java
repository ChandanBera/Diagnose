package com.hyperexchange.diagnose.Models;

import android.widget.TextView;

/**
 * Created by amit on 5/5/17.
 */

public class ResultData {
     static ResultData resultData;
    private String neworkStatus;
     boolean volUpkey,volDownkey;




    public static synchronized ResultData getInstance() {
        if (resultData == null) {
            resultData = new ResultData();
        }
        return resultData;
    }


    public String getNeworkStatus() {
        return neworkStatus;
    }

    public void setNeworkStatus(String neworkStatus) {
        this.neworkStatus = neworkStatus;
    }

    public boolean isVolUpkey() {
        return volUpkey;
    }

    public boolean isVolDownkey() {
        return volDownkey;
    }

    public void setVolDownkey(boolean volDownkey) {
        this.volDownkey = volDownkey;
    }

    public void setVolUpkey(boolean volUpkey) {
        this.volUpkey = volUpkey;


    }
}
