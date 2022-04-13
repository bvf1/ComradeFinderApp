package is.hbv2.ComradeFinderApp.Network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import is.hbv2.ComradeFinderApp.Entities.Account;
import is.hbv2.ComradeFinderApp.Entities.Company;
import is.hbv2.ComradeFinderApp.Entities.User;

public class NetworkManager {

    private static final String TAG = "NetworkManager";
    private static final String BASE_URL = "http://10.0.2.2:8080/";

    private static NetworkManager mInstance;
    private static RequestQueue mQueue;
    private Context mContext;

    public static synchronized NetworkManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new NetworkManager(context);
        }
        return mInstance;
    }

    private NetworkManager(Context context) {
        mContext = context;
        mQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue() {
        if (mQueue == null) {
            mQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mQueue;
    }

    //====================================================
    // NETWORK REQUESTS FOR BACKEND HERE BELOW
    //====================================================

    // TODO: Tests for connecting to backend failed.
    public void login(String username, String password, final NetworkCallback<Object> callback) {
        Log.d(TAG, "Start Login - u="+username + ", p=" + password);
        StringRequest request = new StringRequest(
                Request.Method.GET, BASE_URL + "Login/" + username + "/" + password, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Response");
                        Gson gson = new Gson();
                        //try {
                            Type accType = new TypeToken<User>() {}.getType();
                            Log.d(TAG, "accType: " + accType.toString());
                            Log.d(TAG, "gson: " + gson.fromJson(response, accType).toString());
                            User account = gson.fromJson(response, accType);
                            callback.onSuccess(account);
                            return;
                        /*} catch(Exception e) {
                            Log.d(TAG, "Login is not of type User. Trying Company");
                        }/**//*
                        try {
                            Type accType = new TypeToken<Company>() {}.getType();
                            Log.d(TAG, "accType: " + accType.toString());
                            Log.d(TAG, "gson: " + gson.fromJson(response, accType).toString());
                            Company account = gson.fromJson(response, accType);
                            callback.onSuccess(account);
                        } catch(Exception e) {
                            Log.d(TAG, "Login is not of type Company either.");
                        }/**/
                    }
        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error");
                        callback.onFailure(error.toString());
                    }
        }
        );
        mQueue.add(request);
    }

    // TODO: Finish implementing register
    public void registerUser(String username, String password, String phone, String email, final NetworkCallback<Boolean> callback) {

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(username);
        jsonArray.put(password);
        jsonArray.put(phone);
        jsonArray.put(email);
        final String mRequestBody = jsonArray.toString();

        StringRequest request = new StringRequest(
                Request.Method.POST, BASE_URL + "registerUser", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "User created with response: " + response);
                callback.onSuccess(true);
            }
        },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error");
                callback.onFailure(error.toString());
            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                    return null;
                }
            }
            /**/
        };
        mQueue.add(request);
    }
    /* == We might need more mappings on the backend to support what we need to do. == */
}
