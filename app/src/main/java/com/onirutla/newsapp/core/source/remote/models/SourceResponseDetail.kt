package com.onirutla.newsapp.core.source.remote.models

import com.onirutla.newsapp.domain.models.SourceDetail
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SourceResponseDetail(
    @SerialName("category")
    val category: String? = "",
    @SerialName("country")
    val country: String? = "",
    @SerialName("description")
    val description: String? = "",
    @SerialName("id")
    val id: String? = "",
    @SerialName("language")
    val language: String? = "",
    @SerialName("name")
    val name: String? = "",
    @SerialName("url")
    val url: String? = "",
)

fun SourceResponseDetail.toSourceDetail() = SourceDetail(
    category = category.orEmpty(),
    country = country.orEmpty(),
    description = description.orEmpty(),
    id = id.orEmpty(),
    language = language.orEmpty(),
    name = name.orEmpty(),
    url = url.orEmpty(),
)
fun List<SourceResponseDetail?>?.toSourceDetail() = if (isNullOrEmpty()) listOf() else mapNotNull { it?.toSourceDetail() }

fun SourceResponseDetail.toSourceDetailEntity() = SourceDetail(
    category = category.orEmpty(),
    country = country.orEmpty(),
    description = description.orEmpty(),
    id = id.orEmpty(),
    language = language.orEmpty(),
    name = name.orEmpty(),
    url = url.orEmpty(),
)