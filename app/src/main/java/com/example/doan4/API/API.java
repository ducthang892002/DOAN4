package com.example.doan4.API;

import com.example.doan4.Models.AppUser;
import com.example.doan4.Models.Carts;
import com.example.doan4.Models.Category;
import com.example.doan4.Models.Notify;
import com.example.doan4.Models.Product;
import com.example.doan4.Models.ProductDetails;
import com.example.doan4.Models.SOrder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface API {
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    API API = new Retrofit.Builder().baseUrl("https://newgreyroof66.conveyor.cloud/")
            .addConverterFactory(GsonConverterFactory.create(gson)).build().create(API.class);

    @GET("api/appuser/kiemtra/{acc}/{mk}")
    Call<List<AppUser>> CheckAcc (@Path("acc")String acc, @Path("mk") String mk);
    @GET("api/san_pham/timkiem/{id}")
    Call<List<Product>> GetSearchProduct(@Path("id") String id);
    @POST("api/AppUsers")
    Call<String> CreateAcc (@Body AppUser appUser);
    @GET("api/AppUsers/{id}")
    Call<AppUser> GetAccount(@Path("id") int id);
    @GET("api/Products")
    Call<List<Product>> Get_Product();
    @GET("api/Products/{id}")
    Call<Product> getchitiet(@Path("id") int id);
    @GET("api/Cart/GetCartAccpunt/{id_account}")
    Call<List<Carts>> GetCartAccpunt(@Path("id_account") int id_account);
    @GET("api/Cart/TotalMoney/{id_account}")
    Call<String> GetTotalMoney(@Path("id_account") int id_account);
    @GET("api/Categories")
    Call<List<Category>> Get_Category();
    @GET("api/ProductDetails/{id}")
    Call<ProductDetails> GetProductDetail(@Path("id") int id);
    @GET("api/Category/GetId_categorys/{Id_danhmuc}")
    Call<List<Product>> GetId_Product(@Path("Id_danhmuc") int Id_danhmuc);
    @GET("api/ProductDetails/GetProductDetails/{Id_product}")
    Call<List<ProductDetails>> GetProductDetails(@Path("Id_product") int Id_product);
    @POST("api/Carts")
    Call<List<Carts>> PostCart(@Body Carts cart);
    @GET("api/AppUsers/{id}")
    Call<AppUser> gettt(@Path("id") int id);
    @PUT("api/Carts/{id}")
    Call<Carts> PutCart(@Path ("id")int id,@Body Carts cart);
    @DELETE("api/Carts/{id}")
    Call<Carts> DeletCarts(@Path("id") int id);
    @DELETE("api/Carts/{id}")
    Call<Carts> DeleteCart(@Path("id") int id);
    @GET("api/Getorder/{id_Account}/{Status}")
    Call<List<SOrder>> Getorder(@Path("id_Account") int id_Account, @Path("Status") int Status);
    @POST("api/SOrders")
    Call<SOrder> Postorder_(@Body SOrder order);
    @POST("api/Notifies")
    Call<Notify>PostNotify(@Body Notify notify);
    @DELETE("api/SOrders/{id}")
    Call<SOrder> Putorder_(@Path("id") int id);
//    @Body SOrder order
}
