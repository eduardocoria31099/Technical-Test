package com.example.technicaltest.util

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun <T> LifecycleOwner.collect(
    flow: Flow<T>,
    action: suspend (T) -> Unit,
) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect {
                action(it)
            }
        }
    }
}

fun <T : Any, VH : androidx.recyclerview.widget.RecyclerView.ViewHolder> LifecycleOwner.collectPagingData(
    flow: Flow<PagingData<T>>,
    adapter: PagingDataAdapter<T, VH>
) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }
}
