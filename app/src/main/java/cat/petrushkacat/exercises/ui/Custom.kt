package cat.petrushkacat.exercises.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.progressSemantics
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.random.Random

@Preview
@Composable
fun Custom() {
    val color = Color(0xFF038585)
    val backgroundColor = Color(0xFFA9F0F0)
    val progress = rememberSaveable { mutableStateOf(0f) }

    val animatedProgress: Float by animateFloatAsState(
        targetValue = progress.value,
        animationSpec = tween(
            durationMillis = 1000,
            easing = LinearEasing,
        )
    )

    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            MyCircularProgressIndicator(
                progress = animatedProgress,
                modifier = Modifier.size(200.dp),
                color =  color,
                backgroundColor = backgroundColor,
                strokeWidth = 36.dp,
                backgroundStrokeWidth = 20.dp
            )
            Button(onClick = { progress.value = Random.nextFloat()},
                colors = ButtonDefaults.buttonColors(containerColor = color)
            ) {
                Text("Animate with random value")
            }

        }
    }
}

@Composable
fun MyCircularProgressIndicator(
    /*@FloatRange(from = 0.0, to = 1.0)*/
    progress: Float,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.primary,
    strokeWidth: Dp = ProgressIndicatorDefaults.StrokeWidth,
    backgroundColor: Color = Color.Transparent,
    backgroundStrokeWidth: Dp = ProgressIndicatorDefaults.StrokeWidth,
    strokeCap: StrokeCap = StrokeCap.Butt,
) {
    val coercedProgress = progress.coerceIn(0f, 1f)
    val stroke = with(LocalDensity.current) {
        Stroke(width = strokeWidth.toPx(), cap = strokeCap)
    }
    val backgroundStroke = with(LocalDensity.current) {
        Stroke(width = backgroundStrokeWidth.toPx(), cap = strokeCap)
    }
    Canvas(
        modifier
            .progressSemantics(coercedProgress)
            .size(40.dp)
    ) {
        // Start at 12 O'clock
        val startAngle = 270f
        val sweep = coercedProgress * 360f

        val diameterOffset = stroke.width / 2
        val arcDimen = size.width - 2 * diameterOffset
        drawArc(
            color = backgroundColor,
            startAngle = startAngle,
            sweepAngle = 360f,
            useCenter = false,
            topLeft = Offset(diameterOffset, diameterOffset),
            size = Size(arcDimen, arcDimen),
            style = backgroundStroke
        )
        drawArc(
            color = color,
            startAngle = startAngle,
            sweepAngle = sweep,
            useCenter = false,
            topLeft = Offset(diameterOffset, diameterOffset),
            size = Size(arcDimen, arcDimen),
            style = stroke
        )
    }
}