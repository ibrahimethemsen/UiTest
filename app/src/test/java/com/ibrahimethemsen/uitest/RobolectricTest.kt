package com.ibrahimethemsen.uitest

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import java.io.FileOutputStream


@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [33])
class RobolectricTest {
    @Test
    fun `draw a circle `() {
        val bitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888)
        val paint = Paint().apply {
            color = Color.WHITE
        }

        Canvas(bitmap).apply {
            drawColor(Color.RED)
            drawCircle(100f, 100f, 75f, paint)
        }

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, FileOutputStream("robolectric.png"))
    }
}
