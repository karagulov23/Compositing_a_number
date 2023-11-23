package orlo.karagulov.compositing_a_number.domain.usecases

import orlo.karagulov.compositing_a_number.domain.entity.Question
import orlo.karagulov.compositing_a_number.domain.repository.GameRepository

class GenerateQuestionUseCase(private val repository: GameRepository) {

    operator fun invoke(maxSumValue: Int): Question {
        return repository.generateQuestion(maxSumValue, COUNT_OF_OPTIONS)
    }

    private val COUNT_OF_OPTIONS = 6

}