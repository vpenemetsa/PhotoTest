package com.nctest.photoviewer.network;

import com.nctest.photoviewer.model.Photo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PhotoService {

    @GET("photos")
    Call<List<Photo>> getPhotos();
}
