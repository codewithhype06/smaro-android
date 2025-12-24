package com.example.smaro.ui.resume

import android.content.Context
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

object ResumePdfExporter {

    fun export(
        context: Context,
        name: String,
        role: String,
        lines: List<String>
    ): File {

        val document = PdfDocument()
        val paint = Paint()

        val pageInfo = PdfDocument.PageInfo.Builder(
            595, // A4 width
            842, // A4 height
            1
        ).create()

        val page = document.startPage(pageInfo)
        val canvas = page.canvas

        var y = 50

        // ---- NAME ----
        paint.textSize = 20f
        paint.isFakeBoldText = true
        canvas.drawText(name, 40f, y.toFloat(), paint)

        // ---- ROLE ----
        y += 30
        paint.textSize = 14f
        paint.isFakeBoldText = false
        canvas.drawText(role, 40f, y.toFloat(), paint)

        // ---- CONTENT ----
        y += 30
        paint.textSize = 12f

        lines.forEach { line ->
            canvas.drawText(line, 40f, y.toFloat(), paint)
            y += 20
        }

        document.finishPage(page)

        // ---- FILE SAVE ----
        val file = File(
            context.cacheDir,
            "resume_${System.currentTimeMillis()}.pdf"
        )

        FileOutputStream(file).use { out ->
            document.writeTo(out)
        }

        document.close()

        return file
    }

    /**
     * Helper for sharing
     */
    fun getShareUri(context: Context, file: File) =
        FileProvider.getUriForFile(
            context,
            context.packageName + ".provider",
            file
        )
}
