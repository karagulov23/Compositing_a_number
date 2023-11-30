package orlo.karagulov.compositing_a_number.presentation

import GameViewModel
import android.content.res.ColorStateList
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import orlo.karagulov.compositing_a_number.R
import orlo.karagulov.compositing_a_number.databinding.FragmentGameBinding
import orlo.karagulov.compositing_a_number.domain.entity.GameResult
import orlo.karagulov.compositing_a_number.domain.entity.Level

class GameFragment : Fragment() {

    private val args by navArgs<GameFragmentArgs>()
    private lateinit var rightSound: MediaPlayer
    private lateinit var wrongSound: MediaPlayer
    private var answer = false

    private val viewModelFactory by lazy {
        GameViewModelFactory(args.level, requireActivity().application)
    }

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[GameViewModel::class.java]
    }

    private val tvOptions by lazy {
        mutableListOf<TextView>().apply {
            add(binding.tvOption1)
            add(binding.tvOption2)
            add(binding.tvOption3)
            add(binding.tvOption4)
            add(binding.tvOption5)
            add(binding.tvOption6)
        }
    }

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setClickListenersToOptions()
        setProgress()
        rightSound = MediaPlayer.create(context, R.raw.right_answer)
        wrongSound = MediaPlayer.create(context,R.raw.wrong_answer)
    }

    private fun setProgress() {
        binding.pbCountOfRightAnswer.max = when(args.level){
            Level.TEST -> 3
            Level.EASY -> 10
            Level.NORMAL -> 20
            Level.HARD -> 30
        }
        binding.pbPercentOfRightAnswers.max = when(args.level){
            Level.TEST -> 50
            Level.EASY -> 70
            Level.NORMAL -> 80
            Level.HARD -> 90
        }
    }

    private fun setClickListenersToOptions() {
        for (tvOption in tvOptions) {
            tvOption.setOnClickListener {
                answer = viewModel.checkAnswerForSound(tvOption.text.toString().toInt())
                playSound(answer)
                if (answer)  {
                    binding.pbCountOfRightAnswer.progress++
                }
                viewModel.chooseAnswer(tvOption.text.toString().toInt())
            }
        }
    }

    private fun observeViewModel() {
        viewModel.question.observe(viewLifecycleOwner) {
            binding.tvSum.text = it.sum.toString()
            binding.tvLeftNumber.text = it.visibleNumber.toString()
            for (i in 0 until tvOptions.size) {
                tvOptions[i].text = it.options[i].toString()
            }
        }
        viewModel.percentOfRightAnswers.observe(viewLifecycleOwner) {
            binding.pbPercentOfRightAnswers.setProgress(it,true)
        }
        viewModel.enoughCount.observe(viewLifecycleOwner) {
            binding.tvAnswersProgress.setTextColor(getColorByState(it))
        }
        viewModel.enoughPercent.observe(viewLifecycleOwner) {
            val color = getColorByState(it)
            binding.pbPercentOfRightAnswers.progressTintList = ColorStateList.valueOf(color)
        }
        viewModel.formattedTime.observe(viewLifecycleOwner) {
            binding.tvTimer.text = it
        }
        viewModel.minPercent.observe(viewLifecycleOwner) {
            binding.pbPercentOfRightAnswers.progress = it
        }
        viewModel.gameResult.observe(viewLifecycleOwner) {
            launchGameFinishedFragment(it)
        }
        viewModel.progressAnswers.observe(viewLifecycleOwner) {
            binding.tvAnswersProgress.text = it
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getColorByState(goodState: Boolean): Int {
        val colorResId = if (goodState) {
            android.R.color.holo_green_light
        } else {
            android.R.color.holo_red_light
        }
        return ContextCompat.getColor(requireContext(), colorResId)
    }

    private fun playSound(answer: Boolean) {
        if (answer) {
            rightSound.start()
        } else {
            wrongSound.start()
        }
    }
    private fun launchGameFinishedFragment(gameResult: GameResult) {
        findNavController().navigate(
            GameFragmentDirections.actionGameFragmentToFinishGameFragment(gameResult)
        )
    }
}