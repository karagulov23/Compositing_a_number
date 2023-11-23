package orlo.karagulov.compositing_a_number.domain.entity

data class GameResult (
    val win: Boolean,
    val countOfRightAnswers: Int,
    val countOfQuestions: Int,
    val gameSettings: GameSettings
)