package com.kiraieee.nossist

import android.content.Intent
import android.os.RemoteException
import android.speech.RecognitionService
import android.speech.SpeechRecognizer

/**
 * Exists only because <voice-interaction-service> requires a recognitionService
 * pointing at a component in this package -- without it the system rejects the
 * whole voice interaction service and falls back to launching the ASSIST activity,
 * which is the thing that steals focus.
 *
 * It fails fast rather than hanging, so anything that does route speech here gets
 * an immediate error instead of a dead microphone.
 */
class NoopRecognitionService : RecognitionService() {

    override fun onStartListening(recognizerIntent: Intent?, listener: Callback?) {
        try {
            listener?.error(SpeechRecognizer.ERROR_CLIENT)
        } catch (_: RemoteException) {
        }
    }

    override fun onCancel(listener: Callback?) = Unit

    override fun onStopListening(listener: Callback?) = Unit
}
