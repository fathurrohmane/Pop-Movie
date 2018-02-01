package com.elkusnandi.popularmovie.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Taruna 98 on 06/01/2018.
 */

public class Respond {

//     "status_code": 1,
//     "status_message": "Success."
//     "status_code": 3,
//     "status_message": "Authentication failed: You do not have permissions to access the service."
//     "status_code": 34
//     "status_message": "The resource you requested could not be found.",

    // Reference here
    // https://www.themoviedb.org/documentation/api/status-codes

    @SerializedName("status_code")
    @Expose
    private int statusCode;
    @SerializedName("status_message")
    @Expose
    private String statusMessage;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

}
