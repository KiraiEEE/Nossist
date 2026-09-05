package com.kiraieee.nossist

import android.content.Context
import android.os.Bundle
import android.service.voice.VoiceInteractionSession
import android.service.voice.VoiceInteractionSessionService
import android.view.View

/**
 * The session the system spins up when the assist gesture fires.
 *
 * The whole point is that no window is ever added: [setUiEnabled] false means the
 * framework skips showing our window entirely, so the foreground app never loses
 * focus and immersive mode survives.
 *
 * Nothing here may throw. A crash in the session leaves the system waiting on a
 * session that never arrives, which surfaces to the user as an ANR dialog.
 */
class NoopSessionService : VoiceInteractionSessionService() {
    override fun onNewSession(args: Bundle?): VoiceInteractionSession = NoopSession(this)
}

private class NoopSession(context: Context) : VoiceInteractionSession(context) {

    override fun onCreate() {
        super.onCreate()
        quiet()
        // Don't let the system collect assist data or screenshot the foreground app
        // on our behalf -- we never read it, and the screenshot costs a frame.
        runCatching { setDisabledShowContext(SHOW_WITH_ASSIST or SHOW_WITH_SCREENSHOT) }
        runCatching { setKeepAwake(false) }
    }

    override fun onPrepareShow(args: Bundle?, showFlags: Int) {
        // Documented place to opt out of the UI, before the window would be built.
        quiet()
    }

    override fun onShow(args: Bundle?, showFlags: Int) {
        // Deliberately not calling super. Hide immediately; keep the session object
        // alive so the next trigger is instant instead of restarting the service.
        runCatching { hide() }
    }

    override fun onCreateContentView(): View? = null

    private fun quiet() {
        runCatching { setUiEnabled(false) }
    }
}
