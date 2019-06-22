package com.justincamp.demo

/**
 * (Mostly) Original Code, heavily influenced by
 * https://www.codexpedia.com/android/unit-test-retrofit-2-rxjava-2-and-livedata-in-android/
 */

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.justincamp.demo.api.DiscogsAPI
import com.justincamp.demo.api.Response
import com.justincamp.demo.models.SearchResultItem
import com.justincamp.demo.models.SearchResults
import com.justincamp.demo.ui.searchScreen.SearchViewModel
import io.reactivex.Single
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class SearchViewModelTest {

    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var discogsAPI: DiscogsAPI

    @Mock
    lateinit var observer: Observer<ArrayList<SearchResultItem>>

    @InjectMocks
    lateinit var searchVM: SearchViewModel

    // See associated file for credit on this class
    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }

    @Test
    fun searchViewModel_success_should_populate_results_array() {
        // Populate a "successful" result
        val resultItem = SearchResultItem()
        val results = SearchResults()
        results.results = ArrayList<SearchResultItem>()
        results.results.add(resultItem)
        val successResp = Response(results)

        // Mock the api
        Mockito.`when`(discogsAPI.search(anyString(), anyInt())).thenReturn(Single.just(successResp))

        // Observe live data, execute the search and assert we've gotten a positive result
        searchVM.searchResults.observeForever(observer)
        searchVM.search("nirvana")
        assert(searchVM.searchResults.value!!.size > 0)
    }

    @Test
    fun searchViewModel_failure_should_assign_error() {
        val failResp = Response<SearchResults>(Throwable("error"))

        // Mock the api
        Mockito.`when`(discogsAPI.search(anyString(), anyInt())).thenReturn(Single.just(failResp))

        // Observe live data, execute the search and assert we've gotten a positive result
        searchVM.searchResults.observeForever(observer)
        searchVM.search("nirvana")
        assert(searchVM.error.value != null)
    }

}