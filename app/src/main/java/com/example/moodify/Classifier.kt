package com.example.moodify

import android.content.Context
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.FileUtil
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class Classifier(
    private val context: Context
) {

    private lateinit var vocabData : HashMap<String,Int>
    private lateinit var tfLiteInterpreter : Interpreter

    fun load(
        modelAssetsName: String ,
        vocabAssetsName: String ,
        onComplete: () -> Unit
    ) {
        CoroutineScope( Dispatchers.Default ).launch {
            val interpreter = loadModel( modelAssetsName )
            val vocab = loadVocab( vocabAssetsName )
            if( vocab != null && interpreter != null ) {
                this@Classifier.vocabData = vocab
                this@Classifier.tfLiteInterpreter = interpreter
                withContext( Dispatchers.Main ) {
                    onComplete()
                }
            }
            else {
                throw Exception( "Could not load model" )
            }
        }
    }


    suspend fun classify(text: String): String = withContext(Dispatchers.Default) {
        val inputs: Array<FloatArray> = arrayOf(
            padSequence(tokenize(text))
                .map { it.toFloat() }
                .toFloatArray()
        )
        val outputs: Array<FloatArray> = arrayOf(FloatArray(7))
        tfLiteInterpreter.run(inputs, outputs)

        // Find the index of the maximum value
        var maxIndex = -1
        var maxValue = Float.MIN_VALUE
        for (i in outputs[0].indices) {
            if (outputs[0][i] > maxValue) {
                maxValue = outputs[0][i]
                maxIndex = i
            }
        }

        val moods = arrayOf("anger", "fear", "happy", "joy", "love", "sadness", "neutral")
        return@withContext moods[maxIndex]
    }

    // Tokenize the given sentence
    private fun tokenize(
        message : String
    ): IntArray {
        return message
            .split(" " )
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .map { part -> vocabData[part] ?: 0 }
            .toIntArray()
    }

    // Pad the given sequence to `maxlen` with zeros.
    private fun padSequence(
        sequence: IntArray,
        maxlen: Int = 100
    ): IntArray {
        val paddedSequence = IntArray(maxlen) { 0 }
        for (i in sequence.indices) {
            if (i < maxlen) {
                paddedSequence[i] = sequence[i]
            }
        }
        return paddedSequence
    }

    private suspend fun loadVocab(
        vocabAssetsName: String
    ): HashMap<String,Int>? = withContext( Dispatchers.IO ) {
        Log.d( "Model" , "Loading vocab from $vocabAssetsName" )
        val inputStream = context.assets?.open( vocabAssetsName )
        if( inputStream != null ) {
            val reader = BufferedReader( InputStreamReader( inputStream ) )
            val jsonContents = reader.readText()
            val jsonObject = JSONObject( jsonContents )
            val iterator: Iterator<String> = jsonObject.keys()
            val data = HashMap<String, Int>()
            while (iterator.hasNext()) {
                val key = iterator.next()
                val index = jsonObject.get( key )
                if( index is Int ) {
                    data[ key ] = index.toInt()
                }
            }
            return@withContext data
        }
        else { null }
    }

    private suspend fun loadModel(
        modelAssetsName: String
    ): Interpreter? = withContext( Dispatchers.IO ) {
        Log.d( "Model" , "Loading model from $modelAssetsName" )
        return@withContext try {
            Interpreter( FileUtil.loadMappedFile(context, modelAssetsName) )
        }
        catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

}