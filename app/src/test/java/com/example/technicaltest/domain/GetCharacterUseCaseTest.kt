package com.example.technicaltest.domain

import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import com.example.technicaltest.domain.model.CharacterModel
import com.example.technicaltest.domain.repository.CharacterRepository
import com.example.technicaltest.domain.usecase.GetCharacterUseCase
import com.example.technicaltest.util.fakeCharacter
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetCharacterUseCaseTest {

    private lateinit var repository: CharacterRepository
    private lateinit var useCase: GetCharacterUseCase

    private val testDispatcher = StandardTestDispatcher()


    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        repository = mockk()
        useCase = GetCharacterUseCase(repository)
    }


    @Test
    fun `invoke returns PagingData with expected character`() = runTest {
        val pagingData = PagingData.from(listOf(fakeCharacter))
        coEvery { repository.getCharacters() } returns flowOf(pagingData)

        val differ = AsyncPagingDataDiffer(
            diffCallback = object : DiffUtil.ItemCallback<CharacterModel>() {
                override fun areItemsTheSame(
                    oldItem: CharacterModel,
                    newItem: CharacterModel
                ): Boolean =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(
                    oldItem: CharacterModel,
                    newItem: CharacterModel
                ): Boolean =
                    oldItem == newItem
            },
            updateCallback = noopListUpdateCallback,
            mainDispatcher = testDispatcher,
            workerDispatcher = testDispatcher
        )

        useCase().collect { pagingData ->
            differ.submitData(pagingData)
        }

        testDispatcher.scheduler.advanceUntilIdle()

        val snapshot = differ.snapshot().items
        assertEquals(1, snapshot.size)
        assertEquals("Rick Sanchez", snapshot[0].name)
    }

    private val noopListUpdateCallback = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}

