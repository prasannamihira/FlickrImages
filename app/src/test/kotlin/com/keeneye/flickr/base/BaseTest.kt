package com.keeneye.flickr.base

import com.keeneye.flickr.model.ImagesResponse
import com.keeneye.flickr.network.ApiService
import com.keeneye.flickr.repository.ImagesApiRepository
import io.reactivex.schedulers.TestScheduler
import io.reactivex.subscribers.TestSubscriber
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks
import org.robolectric.RobolectricTestRunner
import retrofit2.Response

@RunWith(RobolectricTestRunner::class)
abstract class BaseTest {

    @Mock
    lateinit var apiService: ApiService

    @Mock
    lateinit var testScheduler: TestScheduler

    @Mock
    lateinit var testSubscriber: TestSubscriber<Response<ImagesResponse>>

    @Mock
    lateinit var imagesApiRepository: ImagesApiRepository

    @Before
    fun init() {

        // To make sure a new mock is created for each new test and therefore all tests are independent
        initMocks(this)

        testScheduler = TestScheduler()

        testSubscriber = TestSubscriber()

        imagesApiRepository = ImagesApiRepository(apiService)
    }
}