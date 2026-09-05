package com.kiraieee.nossist

import android.app.Activity
import android.os.Bundle
import android.widget.Toast

/**
 * Fallback for devices that route the assist gesture at the ASSIST intent instead of
 * the voice interaction service. Launching an activity always costs the foreground
 * app its focus, so this exists only as a safety net -- the session path is preferred.
 *
 * With Theme.NoDisplay the activity MUST finish before onResume() completes. If it
 * doesn't, it holds input focus with no drawn window and the system raises an
 * "isn't responding" dialog. Hence: finish synchronously, no handler, no delay.
 */
class NoopAssistant : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences("nossist", MODE_PRIVATE)
        if (!prefs.getBoolean("seen", false)) {
            Toast.makeText(applicationContext, "not today, earbuds", Toast.LENGTH_SHORT).show()
            prefs.edit().putBoolean("seen", true).apply()
        }

        finish()
        @Suppress("DEPRECATION")
        overridePendingTransition(0, 0)
    }
}
