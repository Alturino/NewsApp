package com.onirutla.newsapp.core.source.remote.api_services

import arrow.core.Either
import arrow.retrofit.adapter.either.networkhandling.CallError
import com.onirutla.newsapp.core.source.remote.models.BaseArticleResponse
import com.onirutla.newsapp.core.source.remote.models.BaseSourceResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET(value = "everything")
    suspend fun getEverything(
        /**
         * A date and optional time for the oldest article allowed. This should be in ISO 8601 format (e.g. 2023-11-30 or 2023-11-30T00:13:18
         *
         * Default: the oldest according to your plan.
         *
         * Developer Plan 1 Month Old
         * */
        @Query(value = "from") fromDateTime: String? = null,
        /**
         * Use this to page through the results if the total results found is greater than the page size
         * */
        @Query(value = "page") pageNumber: Int? = 1,
        /**
         * The number of results to return per page.
         *
         * Default: 100. Maximum: 100.
         * */
        @Query(value = "pageSize") pageSize: Int? = 50,
        /**
         * Keywords or phrases to search for in the article title and body.
         *
         * Advanced search is supported here:
         *
         * - Surround phrases with quotes (") for exact match.
         * - Prepend words or phrases that must appear with a + symbol. Eg: +bitcoin
         * - Prepend words that must not appear with a - symbol. Eg: -bitcoin
         * - Alternatively you can use the AND / OR / NOT keywords, and optionally group these with parenthesis. Eg: crypto AND (ethereum OR litecoin) NOT bitcoin.
         *
         * The complete value for q must be URL-encoded. Max length: 500 chars.
         * */
        @Query(value = "q") query: String,
        /**
         * The fields to restrict your q search to.
         *
         * The possible options are:
         *
         * - title
         * - description
         * - content
         * - Multiple options can be specified by separating them with a comma, for example: title,content.
         *
         * This parameter is useful if you have an edge case where searching all the fields is not giving the desired outcome, but generally you should not need to set this.
         * */
        @Query(value = "searchIn") searchIn: String? = null,
        /**
         * The order to sort the articles in. Possible options: relevancy, popularity, publishedAt.
         * relevancy = articles more closely related to q come first.
         * popularity = articles from popular sources and publishers come first.
         * publishedAt = newest articles come first.
         *
         * Default: publishedAt
         * */
        @Query(value = "sortBy") sortBy: String? = null,
        /**
         * A comma-seperated string of identifiers (maximum 20) for the news sources or blogs you want headlines from. Use the /sources endpoint to locate these programmatically or look at the sources index.
         * */
        @Query(value = "sources") sources: String? = null,
        /**
         * A date and optional time for the newest article allowed. This should be in ISO 8601 format (e.g. 2023-11-30 or 2023-11-30T00:13:18)
         *
         * Default: the newest according to your plan.
         *
         * Developer Plan 1 Month Old
         * */
        @Query(value = "to") toDateTime: String? = null,
    ): Either<CallError, BaseArticleResponse>

    @GET(value = "top-headlines")
    suspend fun getTopHeadlines(
        /**
         * The category you want to get headlines for. Possible options:
         *   - business
         *   - entertainment
         *   - general
         *   - health
         *   - science
         *   - sports
         *   - technology.
         *
         *   Note: you can't mix this param with the sources param.
         */
        @Query(value = "category") category: String? = null,
        /**
         * The 2-letter ISO 3166-1 code of the country you want to get headlines for.
         * Note: you can't mix this param with the sources param
         */
        @Query(value = "country") countryCode: String? = null,
        /**
         * Use this to page through the results if the total results found is greater than the page size
         * */
        @Query(value = "page") pageNumber: Int? = 1,
        /**
         * The number of results to return per page (request). 20 is the default, 100 is the maximum
         * */
        @Query(value = "pageSize") pageSize: Int? = 50,
        /**
         * Keywords or a phrase to search for.
         */
        @Query(value = "q") query: String? = null,
        /**
         * sources is comma separated string you can't mix this param with the country or category params
         */
        @Query(value = "sources") sources: String? = null,
    ): Either<CallError, BaseArticleResponse>

    @GET(value = "top-headlines/sources")
    suspend fun getTopHeadlineSources(
        /**
         * Find sources that display news of this category. Possible options:
         * - business
         * - entertainment
         * - general
         * - health
         * - science
         * - sports
         * - technology
         *
         * Default: all categories
         */
        @Query(value = "category") category: String? = null,
        /**
         * The 2-letter ISO 3166-1 code of the country you want to get headlines for.
         * Note: you can't mix this param with the sources param
         */
        @Query(value = "country") countryCode: String? = null,
        /**
         * Find sources that display news in a specific language. Possible options:
         * - ar
         * - de
         * - en
         * - es
         * - fr
         * - he
         * - it
         * - nl
         * - no
         * - pt
         * - ru
         * - sv
         * - ud
         * - zh.
         *
         * Default: all languages.
         */
        @Query(value = "language") language: String? = null,
    ): Either<CallError, BaseSourceResponse>
}

