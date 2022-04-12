package is.hbv2.ComradeFinderApp.Network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import is.hbv2.ComradeFinderApp.Entities.Account;
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
    public void login(String username, String password, final NetworkCallback<Account> callback) {
        Log.d(TAG, "Start Login - u="+username + ", p=" + password);
        StringRequest request = new StringRequest(
                Request.Method.GET, BASE_URL + "Login/" + username + "/" + password, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Response");
                        Gson gson = new Gson();
                        Type accType = new TypeToken<Account>(){}.getType();
                        Account account = gson.fromJson(response, accType);
                        callback.onSuccess(account);
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
    public void registerUser(String username, String password, String phone, String email, final NetworkCallback<User> callback) {

        StringRequest request = new StringRequest(
                Request.Method.POST, BASE_URL + "registerUser", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

            }
        },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error");
                callback.onFailure(error.toString());
            }
        });
    }
    /* == We might need more mappings on the backend to support what we need to do. == */
}
