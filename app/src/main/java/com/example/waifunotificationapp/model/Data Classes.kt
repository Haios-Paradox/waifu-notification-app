package com.example.waifunotificationapp.model

data class Waifu(
    val waifuAvatar : String ="",
    val waifuPicture : String = "",
    val name: String = "",
    val personality: String = "",
    val exampleChat: String = "",
    val background: String = "",
    val relationship: String = "",
    val activities: List<String> = emptyList(),
    val moodVariants: List<String> = emptyList(),
    val maxLength: Int = 80,
    val temperature: Double = 0.8,
    val nsfw: Boolean = false
)

data class User(
    val name: String = "",
    val description: String = "",
    val waifus : List<String> = emptyList(),
    val task : List<String> = emptyList()
)

data class Task(
    val title: String = "",
    val description: String = "",
    val dueDateMillis: Long = 0,
    val isCompleted: Boolean = false
)

data class Request(
    val waifuId: String = "",
    val userId: String = "",
    val taskId : String? = null,
    val instruction: String = "",
    val prompt: String = "",
    val ujb : String = "",
    val time : Long = 0,
)

data class Message(
    val waifuId : String = "",
    val userId: String = "",
    val waifuName:String = "",
    val message: String = "",
    val title : String = "",
    val taskId: Int? = null,
    val dueDate: Long = 0,
    val seen : Boolean = false
)

data class WaifuMessage(
    val message: Message, val waifu: Waifu, var notified:Boolean = false
    )