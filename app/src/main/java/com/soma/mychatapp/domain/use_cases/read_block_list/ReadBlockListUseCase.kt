package com.soma.mychatapp.domain.use_cases.read_block_list

import com.soma.mychatapp.data.local.BlockUser
import com.soma.mychatapp.data.repository.Repository
import kotlinx.coroutines.flow.Flow

class ReadBlockListUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke():Flow<List<BlockUser>>{
        return repository.getBlockList()
    }
}