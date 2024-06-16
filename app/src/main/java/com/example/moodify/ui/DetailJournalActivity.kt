package com.example.moodify.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.moodify.R
import com.example.moodify.databinding.ActivityDetailJournalBinding
import com.example.moodify.model.response.JournalItem
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

class DetailJournalActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailJournalBinding
    private val journal by lazy { intent.getParcelableExtra<JournalItem>(EXTRA_JOURNAL) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailJournalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setView()
        setListener()
    }

    private fun setView() {
        binding.apply {
            journal?.let { journal ->
                tvTitle.text = journal.journalTitle
                tvJournal.text = journal.journalText

                val dateTime = OffsetDateTime.parse(journal.updatedAt, DateTimeFormatter.ISO_DATE_TIME)
                val localDateTime = dateTime.atZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()

                val dateFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd yyyy", Locale.ENGLISH)
                val dateString = localDateTime.format(dateFormatter)
                tvJam.text = "Written on $dateString"
            }
        }
    }

    private fun setListener() {
        binding.apply {
            fabAdd.setOnClickListener { finish() }
        }
    }

    companion object {
        const val EXTRA_JOURNAL = "extra_journal"
    }
}