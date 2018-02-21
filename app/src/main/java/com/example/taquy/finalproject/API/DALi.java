package com.example.taquy.finalproject.API;

import org.json.JSONException;

/**
 * Created by taquy on 2/18/2018.
 */

public interface DALi {
    void makeRequest(Object object);
    void makeResponse(Object object) throws JSONException;
}
