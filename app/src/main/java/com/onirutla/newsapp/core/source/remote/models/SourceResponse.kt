package com.onirutla.newsapp.core.source.remote.models

import com.onirutla.newsapp.domain.models.Source
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SourceResponse(
    @SerialName("id")
    val id: String? = "",
    @SerialName("name")
    val name: String? = "",
)

fun SourceResponse.toSource() = Source(
    id = id.orEmpty(),
    name = name.orEmpty()
)
