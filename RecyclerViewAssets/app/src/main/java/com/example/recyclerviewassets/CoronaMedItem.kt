package com.example.recyclerviewassets


import com.google.gson.annotations.SerializedName

data class CoronaMedItem(
    @SerializedName("AGRGT_DE")
    val aGRGTDE: String,
    @SerializedName("DISTRCT_DIV_DTLS")
    val dISTRCTDIVDTLS: String,
    @SerializedName("EMGC_CENTER_TELNO")
    val eMGCCENTERTELNO: String,
    @SerializedName("MEDINST_NM")
    val mEDINSTNM: String,
    @SerializedName("REFINE_LOTNO_ADDR")
    val rEFINELOTNOADDR: String,
    @SerializedName("REFINE_ROADNM_ADDR")
    val rEFINEROADNMADDR: String,
    @SerializedName("REFINE_WGS84_LAT")
    val rEFINEWGS84LAT: String,
    @SerializedName("REFINE_WGS84_LOGT")
    val rEFINEWGS84LOGT: String,
    @SerializedName("REPRSNT_TELNO")
    val rEPRSNTTELNO: String,
    @SerializedName("SIGUN_NM")
    val sIGUNNM: String
)