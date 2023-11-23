package orlo.karagulov.compositing_a_number.domain.entity

data class  Question (
    val sum: Int,
    val visibleNumber: Int,
    val options: List<Int>
)