package dev.helena.gardenofeden_ccl3.util

import android.content.Context
import android.util.Log
import dev.helena.gardenofeden_ccl3.R
import dev.helena.gardenofeden_ccl3.data.Question
import com.google.gson.Gson

object JsonUtils {
    fun readQuestionsFromJson(context: Context): List<Question> {
        return try {
        val inputStream = context.resources.openRawResource(R.raw.self_reflection_questions)
        val json = inputStream.bufferedReader().use { it.readText() }


            // parse the JSON into a list of questions
            val questions = Gson().fromJson(json, QuestionList::class.java)

            questions.questions

    } catch (e:Exception) {
        Log.e("JsonUtils", "Error reading JSON", e)
        emptyList()
    }
}

    data class QuestionList(val questions: List<Question>)
}