package com.example.moodify.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moodify.databinding.CardJournalBinding
import com.example.moodify.model.response.JournalItem
import com.example.moodify.ui.DetailJournalActivity
import com.example.moodify.ui.DetailJournalActivity.Companion.EXTRA_JOURNAL
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

class JournalAdapter: ListAdapter<JournalItem, JournalAdapter.ViewHolder>(DIFF_CALLBACK){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardJournalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val journal = getItem(position)
        holder.bind(journal)
    }
    class ViewHolder(private val binding: CardJournalBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(journal: JournalItem){
            binding.apply {

                val dateTime = OffsetDateTime.parse(journal.updatedAt, DateTimeFormatter.ISO_DATE_TIME)
                val localDateTime = dateTime.atZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()
                val dateFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd yyyy", Locale.ENGLISH)
                val dateString = localDateTime.format(dateFormatter)

                val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
                val timeString = localDateTime.format(timeFormatter)
                Log.d(TAG, "bind: $journal")
                tvTitle.text = journal.journalTitle
                tvDate.text = dateString
                tvHour.text = "Written on $timeString"

                llContainer.setOnClickListener {
                    val intent = Intent(itemView.context, DetailJournalActivity::class.java)
                    intent.putExtra(EXTRA_JOURNAL, journal)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<JournalItem>() {
            override fun areItemsTheSame(oldItem: JournalItem, newItem: JournalItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: JournalItem, newItem: JournalItem): Boolean {
                return oldItem == newItem
            }
        }
        private const val TAG = "JournalAdapter"
    }
}