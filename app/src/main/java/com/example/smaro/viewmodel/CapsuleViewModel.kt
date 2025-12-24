package com.example.smaro.viewmodel

import androidx.lifecycle.ViewModel
import com.example.smaro.model.Capsule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CapsuleViewModel : ViewModel() {

    private val _capsules = MutableStateFlow<List<Capsule>>(emptyList())
    val capsules: StateFlow<List<Capsule>> = _capsules

    fun addCapsule(topic: String, content: String) {
        if (topic.isBlank() || content.isBlank()) return

        _capsules.value = _capsules.value + Capsule(
            topic = topic,
            content = content
        )
    }
}
