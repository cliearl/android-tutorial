package com.example.retrofit2basic


import com.squareup.moshi.Json

data class EmgMedResponse(
    @field:Json(name = "EmgMedInfo")
    val emgMedInfo: List<EmgMedInfo>?
)

data class EmgMedInfo(
    val head: List<Head>?,
    val row: List<Row>?
)

data class Head(
    @field:Json(name = "api_version")
    val apiVersion: String?, // 1.0
    @field:Json(name = "list_total_count")
    val listTotalCount: Int?, // 172
    @field:Json(name = "RESULT")
    val rESULT: RESULT?
)

data class RESULT(
    @field:Json(name = "CODE")
    val cODE: String?, // INFO-000
    @field:Json(name = "MESSAGE")
    val mESSAGE: String? // 정상 처리되었습니다.
)

data class Row(
    @field:Json(name = "DISTRCT_DIV_NM")
    val dISTRCTDIVNM: String?, // 선별진료소+국민안심병원+호흡기전담클리닉
    @field:Json(name = "EMGNCY_CENTER_TELNO")
    val eMGNCYCENTERTELNO: String?, // 031-842-1211
    @field:Json(name = "MEDCARE_INST_NM")
    val mEDCAREINSTNM: String?, // 의정부백병원
    @field:Json(name = "REFINE_LOTNO_ADDR")
    val rEFINELOTNOADDR: String?, // 경기도 의정부시 신곡동 519-11번지
    @field:Json(name = "REFINE_ROADNM_ADDR")
    val rEFINEROADNMADDR: String?, // 경기도 의정부시 금신로 322
    @field:Json(name = "REFINE_WGS84_LAT")
    val rEFINEWGS84LAT: String?, // 37.7453057969
    @field:Json(name = "REFINE_WGS84_LOGT")
    val rEFINEWGS84LOGT: String?, // 127.0621355806
    @field:Json(name = "REPRSNT_TELNO")
    val rEPRSNTTELNO: String?, // 031-856-8111
    @field:Json(name = "SIGUN_CD")
    val sIGUNCD: String?, // 41150
    @field:Json(name = "SIGUN_NM")
    val sIGUNNM: String?, // 의정부시
    @field:Json(name = "SUM_DE")
    val sUMDE: String? // 2021-10-22
)