/**
 * Created by Kasun Weerasinghe
 * Date: 2/24/25
 * Time: 10:11 AM
 * Project Name: CarRentalSystem
 */

package com.carrental.carrentalsystem.controller;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/DownloadBillServlet")
public class DownloadBillServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=bill.pdf");

        try {
            PdfWriter writer = new PdfWriter(response.getOutputStream());
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Define a font for the text
            com.itextpdf.kernel.font.PdfFont font = com.itextpdf.kernel.font.PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

            // Add title
            document.add(new Paragraph("Car Rental System")
                    .setFont(font)
                    .setFontSize(18)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(10));

            document.add(new Paragraph("Booking Invoice")
                    .setFont(font)
                    .setFontSize(16)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(20));

            document.add(new Paragraph("Booking ID: " + request.getParameter("bookingId"))
                    .setFont(font)
                    .setFontSize(12)
                    .setMarginBottom(5));

            document.add(new Paragraph("Customer Name: " + request.getParameter("customerName"))
                    .setFont(font)
                    .setFontSize(12)
                    .setMarginBottom(5));

            document.add(new Paragraph("Date: " + request.getParameter("currentDate"))
                    .setFont(font)
                    .setFontSize(12)
                    .setMarginBottom(5));

            document.add(new Paragraph("Car: " + request.getParameter("carBrand") + " - " + request.getParameter("carModel"))
                    .setFont(font)
                    .setFontSize(12)
                    .setMarginBottom(5));

            document.add(new Paragraph("Total Price: RS " + request.getParameter("totalPrice"))
                    .setFont(font)
                    .setFontSize(12)
                    .setMarginBottom(5));

            document.add(new Paragraph("Rental Period: " + request.getParameter("startDate") + " to " + request.getParameter("endDate"))
                    .setFont(font)
                    .setFontSize(12)
                    .setMarginBottom(5));

            document.add(new Paragraph("Driver: " + request.getParameter("driverName"))
                    .setFont(font)
                    .setFontSize(12)
                    .setMarginBottom(20));

            document.add(new Paragraph("Thank you for choosing our Car Rental Service!")
                    .setFont(font)
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(10));

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
