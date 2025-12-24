package com.example.smaro.viewmodel

import androidx.lifecycle.ViewModel
import com.example.smaro.model.LearningEntry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.concurrent.TimeUnit

/**
 * ViewModel managing Daily Learning entries.
 * Uses in-memory storage only.
 */
class LearningViewModel : ViewModel() {

    private val _learningEntries =
        MutableStateFlow<List<LearningEntry>>(emptyList())

    val learningEntries: StateFlow<List<LearningEntry>> =
        _learningEntries.asStateFlow()

    // ✅ Proper derived recall state (NO Flow operators = NO errors)
    private val _todaysRecall =
        MutableStateFlow<List<LearningEntry>>(emptyList())

    val todaysRecall: StateFlow<List<LearningEntry>> =
        _todaysRecall.asStateFlow()

    /**
     * Add a new learning entry safely.
     */
    fun addLearningEntry(
        title: String,
        description: String,
        dateMillis: Long,
        tags: List<String> = emptyList()
    ) {
        if (title.isBlank()) return
        if (description.isBlank()) return
        if (dateMillis <= 0L) return

        val entry = LearningEntry(
            title = title.trim(),
            description = description.trim(),
            dateMillis = dateMillis,
            tags = tags.filter { it.isNotBlank() }
        )

        _learningEntries.value = _learningEntries.value + entry

        // 🔁 Recompute recall whenever data changes
        recomputeTodaysRecall()
    }

    fun removeLearningEntry(entryId: String) {
        if (entryId.isBlank()) return
        _learningEntries.value =
            _learningEntries.value.filterNot { it.id == entryId }

        recomputeTodaysRecall()
    }

    fun clearAllEntries() {
        _learningEntries.value = emptyList()
        _todaysRecall.value = emptyList()
    }

    /**
     * ---------------- INTERNAL LOGIC ----------------
     */

    private fun recomputeTodaysRecall() {
        val now = System.currentTimeMillis()

        _todaysRecall.value =
            _learningEntries.value
                .filter { entry ->
                    val daysOld = TimeUnit.MILLISECONDS.toDays(
                        now - entry.createdAtMillis
                    )
                    daysOld >= 1
                }
                .sortedWith(
                    compareBy<LearningEntry> {
                        extractConfidence(it)
                    }.thenBy {
                        it.createdAtMillis
                    }
                )
                .take(2)
    }

    /**
     * Helper: extract confidence from tags safely.
     */
    private fun extractConfidence(entry: LearningEntry): Int {
        val tag = entry.tags.firstOrNull { it.startsWith("confidence:") }
        return tag?.substringAfter(":")?.toIntOrNull() ?: 50
    }
}
