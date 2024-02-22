package com.ibrahimethemsen.uitest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ibrahimethemsen.uitest.ui.theme.UiTestTheme
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.sp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UiTestTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box(modifier = Modifier.height(120.dp)){
                        DiscountVoucherCard()
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    UiTestTheme {
        DiscountVoucherCard()
    }
}


val orange = Color(0, 229, 255, 255)

@Composable
fun DiscountVoucherCard() {
    val circleRadius = 12.dp
    val cornerSize = 20f
    val cutPoint = 1.5f
    Box(
        modifier = Modifier
            .height(120.dp)
            .width(360.dp)
            .padding(12.dp)
            .border(
                width = 1.dp, color = orange, shape = DiscountVoucher(
                    circleRadius,
                    cornerSize,
                    cutPoint
                )
            )
            .drawBehind {
                val pathEffect = PathEffect.dashPathEffect(floatArrayOf(40f, 20f), 0f)
                drawLine(
                    color = orange,
                    start = Offset(circleRadius.toPx(), size.height.div(cutPoint)),
                    end = Offset(size.width - circleRadius.toPx(), size.height.div(cutPoint)),
                    pathEffect = pathEffect
                )
            }
    ) {
        Column(
            modifier = Modifier.padding(
                top = 12.dp,
                start = circleRadius,
                bottom = 4.dp,
                end = circleRadius
            ),
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Column {
                    Text("50 TL", fontSize = 20.sp)
                    Text("Alt Limit : 100 TL", color = Color.Gray)
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {},
                    border = BorderStroke(2.dp, orange),
                    shape = RoundedCornerShape(50),

                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Text("Kullan", color = orange)
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Text("Seçili ürün grubunda 100TL indirim", color = Color.Gray)
        }
    }
}


class DiscountVoucher(
    private val circleRadius: Dp,
    private val cornerSize: Float,
    private val cutPoint: Float,
) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(path = getCombinePath(size, density))
    }

    private fun getCombinePath(size: Size, density: Density): Path {
        val roundedRect = RoundRect(size.toRect(), CornerRadius(cornerSize))
        val roundedRectPath = Path().apply { addRoundRect(roundedRect) }
        return Path.combine(
            operation = PathOperation.Intersect,
            path1 = roundedRectPath,
            path2 = getDiscountVoucherPath(size, density)
        )
    }

    private fun getDiscountVoucherPath(size: Size, density: Density): Path {
        val cuttingY = size.height.div(cutPoint)
        val sizeX = size.width
        val sizeY = size.height

        return Path().apply {
            lineTo(x = sizeX, y = 0F)

            arcTo(
                rect = density.arcRect(sizeX, cuttingY),
                startAngleDegrees = 270f,
                sweepAngleDegrees = -180f,
                forceMoveTo = false
            )

            lineTo(x = sizeX, y = sizeY)

            lineTo(x = 0F, y = sizeY)

            arcTo(
                rect = density.arcRect(0f, cuttingY),
                startAngleDegrees = 90F,
                sweepAngleDegrees = -180f,
                forceMoveTo = false
            )
        }
    }


    private fun Density.arcRect(
        arcX: Float,
        arcY: Float,
    ): Rect {
        return Rect(
            left = arcX.minus(circleRadius.toPx()),
            top = arcY.minus(circleRadius.toPx()),
            right = arcX.plus(circleRadius.toPx()),
            bottom = arcY.plus(circleRadius.toPx())
        )
    }
}