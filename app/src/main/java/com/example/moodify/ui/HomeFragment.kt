package com.example.moodify.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import com.example.moodify.R
import com.example.moodify.databinding.FragmentHomeBinding
import com.example.moodify.model.response.JournalItem
import com.example.moodify.model.response.JournalResult
import com.example.moodify.model.response.Mood
import com.example.moodify.model.response.Result
import com.example.moodify.retrofit.UserModel
import com.example.moodify.ui.DetailJournalActivity.Companion.EXTRA_JOURNAL
import com.example.moodify.viewModel.MainViewModel
import com.example.moodify.viewModel.ViewModelFactory
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

class HomeFragment(
    private val user: LiveData<UserModel>,
    private val listJournal: LiveData<Result<List<JournalItem>>>,
    private val mood: LiveData<String>
) : Fragment() {
    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_EMAIL = "extra_email"
        const val EXTRA_MOOD_DESC = "extra_mood_desc"
    }



    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        user.observe(viewLifecycleOwner) {
            binding.tvHelloUser.text = "Hello ${it.name}"
        }
        mood.observe(viewLifecycleOwner){
            binding.tvMoodDesc.text = "You feel ${it} today"
            binding.apply {
                when(mood.value){
                    "anger", "fear", "sadness" -> {
                        ivMood.setImageResource(R.drawable.ic_mood_sad)
                        tvMoodTitle.text = "Bad"
                    }

                    "love", "joy", "happy" -> {
                        ivMood.setImageResource(R.drawable.ic_mood_happy)
                        tvMoodTitle.text = "Good"
                    }

                    else -> {
                        ivMood.setImageResource(R.drawable.ic_mood_neutral)
                        tvMoodTitle.text = "Neutral"
                    }
                }
            }

        }

        listJournal.observe(viewLifecycleOwner) { result ->
            if (result is Result.Success) {
                if (result.data.isEmpty()){
                    val intent = Intent(requireActivity(), MyJournalActivity::class.java)
                    intent.putExtra(MyJournalActivity.EXTRA_IS_NEW, true)
                    startActivity(intent)
                    requireActivity().finish()
                } else {
                    val journal = result.data.first()
                    binding.apply {
                        tvTitle.text = journal.journalTitle
                        val dateTime = OffsetDateTime.parse(journal.updatedAt, DateTimeFormatter.ISO_DATE_TIME)
                        val localDateTime = dateTime.atZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()


                        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
                        val timeString = localDateTime.format(timeFormatter)

                        tvHour.text = "Written on $timeString"

                        llContainer.setOnClickListener {
                            val intent = Intent(requireActivity(), DetailJournalActivity::class.java)
                            intent.putExtra(EXTRA_JOURNAL, journal)
                            startActivity(intent)
                        }
                    }
                }
            } else if (result is Result.Error) {
                Toast.makeText(requireActivity(), result.error, Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnToProfile.setOnClickListener {
            val intent = Intent(requireActivity(), ProfileActivity::class.java)
            startActivity(intent)
        }

        binding.btnToRecommendation.setOnClickListener {
            val intent = Intent(requireActivity(),CopingActivity::class.java)
            startActivity(intent)
        }
    }


}