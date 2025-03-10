package com.pdf.pdfwriter

import android.content.Context
import com.itextpdf.kernel.colors.DeviceRgb
import com.itextpdf.kernel.events.Event
import com.itextpdf.kernel.events.IEventHandler
import com.itextpdf.kernel.events.PdfDocumentEvent
import com.itextpdf.kernel.font.PdfFontFactory
import com.itextpdf.kernel.pdf.*
import com.itextpdf.kernel.pdf.canvas.PdfCanvas
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.properties.TextAlignment
import com.itextpdf.layout.properties.VerticalAlignment
import java.io.File


fun createPdfWithHeaderFooter(context: Context, fileName: String) {
    val file = File(context.getExternalFilesDir(null), fileName)
    val writer = PdfWriter(file)
    val pdfDoc = PdfDocument(writer)
    val document = Document(pdfDoc)

    // Add the custom header/footer
    pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, CustomHeaderFooter())

    // Add some sample content
    for(i in 1..5){
        document.add(Paragraph("\n".repeat(1)))
        document.add(
            Paragraph("This is a sample page with a header and footer.\n sample page \nwith a header\n and footer")
            )

        document.add(Paragraph("\n".repeat(1)))
        Paragraph("This is a sample page\n with a header and footer.")
    }


    document.close()
}

class CustomHeaderFooter : IEventHandler {
    override fun handleEvent(event: Event?) {
        val pdfEvent = event as PdfDocumentEvent
        val pdfDoc = pdfEvent.document
        val page = pdfEvent.page
        val canvas = PdfCanvas(page)
        val width = page.pageSize.width
        val height = page.pageSize.height

        // Set font and colors
        val font = PdfFontFactory.createFont()
        val headerColor = DeviceRgb(0, 0, 139) // Dark Blue
        val footerColor = DeviceRgb(255, 0, 0) // Red

        val pageNumber = pdfDoc.getPageNumber(page)

        val pdfCanvas = PdfCanvas(page.newContentStreamBefore(), page.resources, pdfDoc)

        // HEADER: Title in Dark Blue
        val header = Paragraph("My PDF Report")
            .setFont(font)
            .setFontSize(14f)
            .setFontColor(headerColor)
            .setTextAlignment(TextAlignment.CENTER)

        val doc = Document(pdfDoc)
        doc.showTextAligned(header, width / 2, height - 30, pdfDoc.getPageNumber(page), TextAlignment.CENTER, VerticalAlignment.TOP, 0f)

        // FOOTER: Page Number in Red
        val footer = Paragraph("Page $pageNumber")
            .setFont(font)
            .setFontSize(12f)
            .setFontColor(footerColor)
            .setTextAlignment(TextAlignment.CENTER)

        doc.showTextAligned(footer, width / 2, 30f, pdfDoc.getPageNumber(page), TextAlignment.CENTER, VerticalAlignment.BOTTOM, 0f)

        pdfCanvas . release()
    }




}
