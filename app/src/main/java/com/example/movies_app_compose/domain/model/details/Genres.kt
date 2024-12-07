package com.example.movies_app_compose.domain.model.details

import com.google.gson.annotations.SerializedName


data class Genres (

  @SerializedName("id"   ) var id   : Int?    = null,
  @SerializedName("name" ) var name : String? = null

)