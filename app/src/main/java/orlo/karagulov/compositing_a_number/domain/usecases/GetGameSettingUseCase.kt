package orlo.karagulov.compositing_a_number.domain.usecases

import orlo.karagulov.compositing_a_number.domain.entity.GameSettings
import orlo.karagulov.compositing_a_number.domain.entity.Level
import orlo.karagulov.compositing_a_number.domain.repository.GameRepository

class GetGameSettingUseCase(private val repository: GameRepository) {

    operator fun invoke(level: Level): GameSettings {
        return repository.getGameSettings(level)
    }

}