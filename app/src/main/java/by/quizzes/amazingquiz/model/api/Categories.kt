package by.quizzes.amazingquiz.model.api

import com.google.gson.annotations.SerializedName

data class Categories(

    @SerializedName("Arts & Literature")
    val artAndLiterature: List<String>,
    @SerializedName("Film & TV")
    val filmAndTV: List<String>,
    @SerializedName("Food & Drink")
    val foodAndDrink: List<String>,
    @SerializedName("General Knowledge")
    val generalKnowledge: List<String>,
    @SerializedName("Geography")
    val geography: List<String>,
    @SerializedName("History")
    val history: List<String>,
    @SerializedName("Music")
    val music: List<String>,
    @SerializedName("Science")
    val science: List<String>,
    @SerializedName("Society & Culture")
    val societyAndCulture: List<String>,
    @SerializedName("Sport & Leisure")
    val sportAndLeisure: List<String>
)