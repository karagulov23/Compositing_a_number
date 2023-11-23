package orlo.karagulov.compositing_a_number.domain.repository

import orlo.karagulov.compositing_a_number.domain.entity.GameSettings
import orlo.karagulov.compositing_a_number.domain.entity.Level
import orlo.karagulov.compositing_a_number.domain.entity.Question

interface GameRepository {

    fun generateQuestion(
        maxSumValue: Int,
        countOfOptions: Int
    ): Question

    fun getGameSettings(level: Level): GameSettings

}