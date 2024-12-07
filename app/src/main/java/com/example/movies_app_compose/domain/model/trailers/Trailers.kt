package com.example.movies_app_compose.domain.model.trailers

import com.google.gson.annotations.SerializedName


data class Trailers (

  @SerializedName("id"      ) var id      : Int?               = null,
  @SerializedName("results" ) var results : ArrayList<Results> = arrayListOf()

)