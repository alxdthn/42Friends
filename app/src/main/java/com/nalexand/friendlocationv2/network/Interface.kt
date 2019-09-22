package com.nalexand.friendlocation.network

import retrofit2.Response
import retrofit2.http.*

interface RetrofitService {

    @POST("oauth/token")
    suspend fun getToken(
        @Body requestBody: TokenRequest?
    ): Response<TokenResponse>

    @GET("/v2/users/?")
    suspend fun getUser(
        @Header("Authorization") auth: String?,
        @Query("filter[login]", encoded = true) login: String): Response<Array<User>>

    @GET("/v2/locations")
    suspend fun getUserLocation(
        @Header("Authorization") auth: String?,
        @Query("filter[user_id]", encoded = true) user_id: String,
        @Query("filter[end]", encoded = true) end: String): Response<Array<UserLocation>>
}
//https://api.intra.42.fr/v2/users/locations?filter[user_id]=52424,43705,52889,46919,52404,61124,52898,50348,52848,50381&filter[end]=false
/*
curl -X POST --data "grant_type=client_credentials&client_id=6d94ec75f2839e414dcba355566192b67ee05bc2d9a3da66aee8645343a4bffd&client_secret=de6ac060116057e0d3129a461d45ace116c3136da2b2e8778db39ab4fd6b53bd" https://api.intra.42.fr/oauth/token

1fc769ad277dae24f7f26b4876483085b6dddba188540c635bca413fcfac3dc8

curl -g -H "Authorization: Bearer aef3256526588166bd3c056f458c8a4c67de09a7e1ebc92a45c0b29df7702555" "https://api.intra.42.fr/v2/locations/?&filter[user_id]=52424

curl -g -H "Authorization: Bearer 1fc769ad277dae24f7f26b4876483085b6dddba188540c635bca413fcfac3dc8" "https://api.intra.42.fr/v2/users/?&filter[login]=ashari

curl -g -H "Authorization: Bearer 1fc769ad277dae24f7f26b4876483085b6dddba188540c635bca413fcfac3dc8" "https://api.intra.42.fr/v2/users/ashari

curl -g -H "Authorization: Bearer 1fc769ad277dae24f7f26b4876483085b6dddba188540c635bca413fcfac3dc8" "https://api.intra.42.fr/v2/locations/graph(/on/end_at(/by/day))?&filter[user_id]=52424

curl -g -H "Authorization: Bearer aef3256526588166bd3c056f458c8a4c67de09a7e1ebc92a45c0b29df7702555" "https://api.intra.42.fr/v2/users/52424-52404/locations

curl -g -H "Authorization: Bearer aef3256526588166bd3c056f458c8a4c67de09a7e1ebc92a45c0b29df7702555" "https://api.intra.42.fr/oauth/token/info

nalexand=52424
ashari=52404

 */