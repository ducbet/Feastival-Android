package com.framgia.feastival.data.service;

import com.framgia.feastival.data.source.model.CategoriesResponse;
import com.framgia.feastival.data.source.model.LoginResponse;
import com.framgia.feastival.data.source.model.RestaurantsResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by tmd on 19/07/2017.
 */
public interface FeastivalService {
    @GET("groups")
    Observable<RestaurantsResponse> getRestaurants();
    @GET("groups")
    Observable<RestaurantsResponse> getRestaurants(@Query("lat") float lat,
                                                   @Query("lng") float lng,
                                                   @Query("distance") float distance);
    @POST("sign_in")
    Observable<LoginResponse> logIn(@Query("sign_in[email]") String email, @Query
        ("sign_in[password]") String password);
    @GET("categories")
    Observable<CategoriesResponse> getCategories();
}
