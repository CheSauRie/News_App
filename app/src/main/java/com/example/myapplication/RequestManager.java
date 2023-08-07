package com.example.myapplication;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.myapplication.Models.FavoriteNewsResponse;
import com.example.myapplication.Models.LoteryResponse;
import com.example.myapplication.Models.NewsApiResponse;
import com.example.myapplication.Models.NewsData;
import com.example.myapplication.api.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RequestManager {
    Context context;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    Retrofit retrofit_2 = new Retrofit.Builder()
            .baseUrl("https://newsdata.io/api/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    Retrofit retrofit_lotery = new Retrofit.Builder()
            .baseUrl("https://api-xsmb.cyclic.app/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    public void getNewsHeadlines(OnFetchDataListener listener, String category, String query) {
        CallNewsApi callNewsApi = retrofit.create(CallNewsApi.class);
        Call<NewsApiResponse> call = callNewsApi.callHeadlines("us", category, query, context.getString(R.string.api_key));
        try {
            call.enqueue(new Callback<NewsApiResponse>() {
                @Override
                public void onResponse(Call<NewsApiResponse> call, Response<NewsApiResponse> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(context, "Error!!", Toast.LENGTH_SHORT).show();
                    }
                    listener.onFetchData(response.body().getArticles(), response.message());
                }

                @Override
                public void onFailure(Call<NewsApiResponse> call, Throwable t) {
                    listener.onError("Request Failed");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getFavoriteNewsData(OnFetchDataListener listener, String accessToken) {
        Call<FavoriteNewsResponse> call = ApiService.retrofit_backend.getFavoriteNews(accessToken);
        try {
            call.enqueue(new Callback<FavoriteNewsResponse>() {
                @Override
                public void onResponse(Call<FavoriteNewsResponse> call, Response<FavoriteNewsResponse> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(context, "Error!!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    listener.onFetchData(response.body().getData(), response.message());
                }

                @Override
                public void onFailure(Call<FavoriteNewsResponse> call, Throwable t) {
                    Log.d("error api", t.toString());
                    listener.onError("Request Failed: " + t);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getNewsData(OnFetchDataListener listener, String query, String category, String domain) {
        CallNewsApi callNewsApi = retrofit_2.create(CallNewsApi.class);
        Call<NewsData> call = callNewsApi.callNewsData("pub_26500db6e972e69973a1a7c6ca72812179d10", "vi", query, category, domain );
        try {
            call.enqueue(new Callback<NewsData>() {
                @Override
                public void onResponse(Call<NewsData> call, Response<NewsData> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(context, "Error!!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    listener.onFetchData(response.body().getResults(), response.message());
                }

                @Override
                public void onFailure(Call<NewsData> call, Throwable t) {
                    listener.onError("Request Failed: " + t);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public RequestManager(Context context) {
        this.context = context;
    }

    public interface CallNewsApi {
        @GET("top-headlines")
        Call<NewsApiResponse> callHeadlines(
                @Query("country") String country,
                @Query("category") String category,
                @Query("q") String query,
                @Query("apiKey") String api_key
        );

        @GET("news")
        Call<NewsData> callNewsData(
                @Query("apikey") String apikey,
                @Query("language") String language,
                @Query("q") String query,
                @Query("category") String category,
                @Query("domain") String domain
        );
    }

    public interface CallLoteryApi{
        @GET("/")
        Call<LoteryResponse> callLoteryResponse();
    }
}
