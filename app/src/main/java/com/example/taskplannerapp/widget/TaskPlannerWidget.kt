package com.example.taskplannerapp.widget

import android.content.ComponentName
import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.glance.*
import androidx.glance.action.actionStartActivity
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.*
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.appwidget.cornerRadius
import com.example.taskplannerapp.MainActivity

// Фейковые данные (замени на данные из БД)
val tasks = listOf("Купить молоко", "Позвонить боссу", "Сделать дз по Kotlin")

class TaskPlannerWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            Box(
                modifier = GlanceModifier
                    .fillMaxSize()
                    .background(ColorProvider(Color(0xCC202020))) // Темный фон
                    .padding(16.dp)
                    .cornerRadius(12.dp)
            ) {
                Column(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Ваши задачи:",
                        style = TextStyle(
                            color = ColorProvider(Color.White),
                            fontSize = 18.sp
                        )
                    )

                    Spacer(modifier = GlanceModifier.height(8.dp))

                    tasks.forEach { task ->
                        Text(
                            text = "• $task",
                            style = TextStyle(
                                color = ColorProvider(Color.LightGray),
                                fontSize = 14.sp
                            )
                        )
                    }

                    Spacer(modifier = GlanceModifier.height(12.dp))

                    Button(
                        text = "Открыть",
                        onClick = actionStartActivity(ComponentName(context, MainActivity::class.java)),
                        modifier = GlanceModifier
                            .background(ColorProvider(Color(0xFF4CAF50)))
                            .cornerRadius(8.dp)
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }
        }
    }
}

class TaskPlannerWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget = TaskPlannerWidget()
}
