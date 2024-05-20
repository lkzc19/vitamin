package com.example.usecase

import com.example.db.table.Score
import com.example.repository.ScoreRepository
import org.koin.core.component.KoinComponent

interface ScoreUseCase {

    suspend fun add(score: Score)
}

class ScoreUseCaseImpl: ScoreUseCase, KoinComponent {

    private val scoreRepository: ScoreRepository = ScoreRepository()

    override suspend fun add(score: Score) {
        scoreRepository.create(score)
    }
}
