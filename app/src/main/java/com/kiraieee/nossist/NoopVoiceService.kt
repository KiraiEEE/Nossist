package com.kiraieee.nossist

import android.service.voice.VoiceInteractionService

class NoopVoiceService : VoiceInteractionService() {

    /** Assist pressed on the lockscreen: swallow it, don't wake anything up. */
    override fun onLaunchVoiceAssistFromKeyguard() = Unit
}
