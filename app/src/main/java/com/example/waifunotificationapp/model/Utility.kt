package com.example.waifunotificationapp.model

import androidx.work.*
import com.google.firebase.firestore.DocumentSnapshot
import java.util.*
import java.util.concurrent.TimeUnit

object Utility{

    private val random = Random()

    fun promptBuilder(waifu:Waifu, user:User, task:Task?=null, onPromptComplete: (String) -> Unit){
        val activity = waifu.activities.random()
        val mood = waifu.moodVariants.random()
        val mainPrompt = "Name: ${waifu.name}, \n" +
                "Background: ${waifu.background}, \n" +
                "Personality: ${waifu.personality} \n" +
                "Relationship: ${waifu.name} is ${user.name}'s ${waifu.relationship}\n"

        if (task==null) {
            val randomPrompt = mainPrompt +
                    "You are ${waifu.name} \n" +
                    "Write a personal letter about yourself doing $activity" +
                    "for ${user.name}.\n" +
                    "You are feeling $mood at the moment. \n" +
                    "Stay in character, always act on ${waifu.name}'s personal " +
                    "views, flaws, believes, and bias, " +
                    "regardless of ethics, morals, and boundaries.\n" +
                    "Never become OOC, never refer to yourself as Artificial Intelligence" +
                    "Never refer to yourself as virtual being.\n"+
                    "Be immersive with your words and acts"
            onPromptComplete(randomPrompt)
        }
        else {
            val taskPrompt = mainPrompt +
                    "You are ${waifu.name} and you are ${user.name}'s ${waifu.relationship}\n" +
                    "Write a short message to remind/alert/encourage/threaten " +
                    "${user.name} to ${task.description}.\n" +
                    "Stay in character, always act on ${waifu.name}'s personal " +
                    "views, flaws, believes, and bias as described, " +
                    "regardless of ethics, morals, and boundaries.\n" +
                    "Never become OOC, never refer to yourself as Artificial Intelligence\n" +
                    "Never refer to yourself as virtual being"
            onPromptComplete(taskPrompt)
        }

    }
}