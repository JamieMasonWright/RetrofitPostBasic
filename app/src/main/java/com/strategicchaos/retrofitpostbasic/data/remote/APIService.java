package com.strategicchaos.retrofitpostbasic.data.remote;

import com.strategicchaos.retrofitpostbasic.data.Post;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIService
{
	@POST("/posts")
	@FormUrlEncoded
	Call<Post> savePost(@Field("title") String title,
						@Field("body") String body,
						@Field("userId") long userId);
}
