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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;

import is.hbv2.ComradeFinderApp.Entities.Account;
import is.hbv2.ComradeFinderApp.Entities.Ad;
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

    //=============================================================================================
    //                              LOGIN AND REGISTRATION
    //=============================================================================================

    // TODO: Test for company
    public void login(String username, String password, final NetworkCallback<Object> callback) {
        Log.d(TAG, "Start Login - u="+username + ", p=" + password);
        StringRequest request = new StringRequest(
                Request.Method.GET, BASE_URL + "Login/" + username + "/" + password, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Response: " + response);
                        if (response == null || response.equals("")) {
                            Log.d(TAG, "Response was null or empty");
                            callback.onSuccess(null);
                            return;
                        }
                        Gson gson = new Gson();
                        try {
                            Type accType = new TypeToken<Company>() {}.getType();
                            Log.d(TAG, "accType: " + accType.toString());
                            Log.d(TAG, "gson: " + gson.fromJson(response, accType).toString());
                            Company account = gson.fromJson(response, accType);
                            if (account.getCompanyPhone() != null) {
                                callback.onSuccess(account);
                                return;
                            }
                        } catch(Exception e) {
                            Log.d(TAG, "Login is not of type Company either.");
                        }/**/
                        try {
                            Type accType = new TypeToken<User>() {}.getType();
                            Log.d(TAG, "accType: " + accType.toString());
                            Log.d(TAG, "gson: " + gson.fromJson(response, accType).toString());
                            User account = gson.fromJson(response, accType);
                            callback.onSuccess(account);
                            return;
                        } catch(Exception e) {
                            Log.d(TAG, "Login is not of type User. Trying Company. Got error: " + e.toString());
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
                Log.d(TAG, "Error:" + error.toString());
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

    public void registerCompany(String username, String password, String phone, String email, String companyName, String SSN, final NetworkCallback<Boolean> callback) {

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(username);
        jsonArray.put(password);
        jsonArray.put(phone);
        jsonArray.put(email);
        jsonArray.put(companyName);
        jsonArray.put(SSN);
        final String mRequestBody = jsonArray.toString();

        StringRequest request = new StringRequest(
                Request.Method.POST, BASE_URL + "registerCompany", new Response.Listener<String>() {

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


    //=============================================================================================
    //                                         ADS
    //=============================================================================================

    //TODO: createAd needs to be tested properly
    public void createAd(String title, String description, String salaryRange, List<String> extraQuestions, String companyUsername, List<String> tags, final NetworkCallback<Boolean> callback) {
        // 1. PUT INPUT INTO JSONARRAY
        // Note: My use of ListPacking might change the input data in the context that called this function.
        // listPacking: Surrounds a string with a ??? symbol so the backend can unpack it.
        UnaryOperator<String> listPacking = x -> "???" + x + "???";
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(title);
        jsonArray.put(description);
        jsonArray.put(salaryRange);
        extraQuestions.replaceAll(listPacking);
        jsonArray.put(extraQuestions);
        jsonArray.put(companyUsername);
        tags.replaceAll(listPacking);
        jsonArray.put(tags);
        final String mRequestBody = jsonArray.toString();

        // 2. DELEGATE REQUEST AND SEND TO BACKEND
        StringRequest request = new StringRequest(
                Request.Method.POST, BASE_URL + "createAd", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Ad created with response: " + response);
                callback.onSuccess(true);
            }
        },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error creating ad");
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

    public void getAllAds(final NetworkCallback<List<Ad>> callback) {
        Log.d(TAG, "Fetching all ads");
        StringRequest request = new StringRequest(
                Request.Method.GET, BASE_URL + "getAds", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response);
                Gson gson = new Gson();
                Type accType = new TypeToken<List<Ad>>() {}.getType();
                List<Ad> ads = gson.fromJson(response, accType);
                for (int i = 0; i<ads.size(); i++) {
                    ads.get(i).setID(i);
                }
                Log.d(TAG, "ads object result: " + ads.toString());
                //if (ads.isEmpty()) ads = dummyAds();
                callback.onSuccess(ads);
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
    // fake ads
    private List<Ad> dummyAds() {
        List<Ad> ads = new ArrayList<>();

        Ad testAd1 = new Ad("Title1", "Description1", "1-1", new ArrayList<>(), "Comapny1", "LinkToImage1", new ArrayList<>());
        Ad testAd2 = new Ad("Title2", "Description2", "2-2", new ArrayList<>(), "Comapny2", "LinkToImage2", new ArrayList<>());
        Ad testAd3 = new Ad("Title3", "Description3", "3-3", new ArrayList<>(), "Comapny3", "LinkToImage3", new ArrayList<>());

        testAd1.setID(0);
        testAd2.setID(1);
        testAd3.setID(2);

        ads.add(testAd1);
        ads.add(testAd2);
        ads.add(testAd3);
        return ads;
    }

    public void getAllAdsByCompany(String username, final NetworkCallback<List<Ad>> callback) {
        Log.d(TAG, "Fetching all ads by company");
        StringRequest request = new StringRequest(
                Request.Method.GET, BASE_URL + "getAdsByCompany/" + username, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response);
                Gson gson = new Gson();
                Type accType = new TypeToken<List<Ad>>() {}.getType();
                List<Ad> ads = gson.fromJson(response, accType);
                for (int i = 0; i<ads.size(); i++) {
                    ads.get(i).setID(i);
                }
                Log.d(TAG, "ads object result: " + ads.toString());
                //if (ads.isEmpty()) ads = dummyAds();
                callback.onSuccess(ads);
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

    public void deleteAdByID(long id, final NetworkCallback<Boolean> callback) {
        Log.d(TAG, "Deleting ad with ID: " + id);
        StringRequest request = new StringRequest(
                Request.Method.DELETE, BASE_URL + "deleteAd/" + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response);
                callback.onSuccess(true);
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
    /* == We might need more mappings on the backend to support what we need to do. == */
}
