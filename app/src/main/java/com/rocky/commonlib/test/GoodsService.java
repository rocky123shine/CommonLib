package com.rocky.commonlib.test;

import com.rocky.commonlib.net.ResultModel;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface GoodsService {

    @FormUrlEncoded
    @POST("index.php?m=home&c=wish&a=add_wish")
    Observable<ResultModel<String>> addLike(@FieldMap Map<String, Object> map);

}



