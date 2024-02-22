package com.ibrahimethemsen.uitest

import android.app.Activity
import android.graphics.Bitmap
import android.widget.Button
import android.widget.TextView
import androidx.core.view.drawToBitmap
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import java.io.FileOutputStream

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [33])
class ViewTest {
    private lateinit var view: Button

    @Before
    fun setUp() {
        val activityController = Robolectric.buildActivity(Activity::class.java)
        val activity = activityController.create().get()

        view = Button(activity)

        activity.setContentView(view)
        activityController.start().resume().visible()
    }

    @Test
    fun `draw a button`() {
        view.apply {
            text = "Kotlin Mükemmeldir !"
            drawToBitmap().compress(Bitmap.CompressFormat.PNG, 100, FileOutputStream("button.png"))
        }
    }
}
