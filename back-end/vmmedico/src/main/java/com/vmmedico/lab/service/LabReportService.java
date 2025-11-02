package com.vmmedico.lab.service;

import com.vmmedico.lab.entity.LabReport;
import com.vmmedico.lab.repo.LabReportRepository;
import com.vmmedico.Appointment.entity.LabBooking;
import com.vmmedico.Appointment.repo.LabBookingRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.time.Instant;

@Service
public class LabReportService {

    private final LabReportRepository reportRepo;
    private final LabBookingRepository bookingRepo;
    private final Path basePath;

    public LabReportService(LabReportRepository reportRepo,
                            LabBookingRepository bookingRepo,
                            @Value("${app.reports.dir:./reports}") String reportsDir) {
        this.reportRepo = reportRepo;
        this.bookingRepo = bookingRepo;
        this.basePath = Paths.get(reportsDir).toAbsolutePath().normalize();
        try { Files.createDirectories(basePath); } catch (IOException ignored) {}
    }

    public LabReport uploadReport(Long bookingId, MultipartFile file, String uploadedBy) throws IOException {
        LabBooking booking = bookingRepo.findById(bookingId).orElseThrow(() -> new RuntimeException("Booking not found"));
        if (!file.getOriginalFilename().toLowerCase().endsWith(".pdf")) {
            throw new RuntimeException("Only PDF files are allowed");
        }

        Long labId = booking.getLab().getId();
        Path labDir = basePath.resolve(String.valueOf(labId));
        Files.createDirectories(labDir);

        String fname = "report_booking_" + bookingId + "_" + System.currentTimeMillis() + ".pdf";
        Path target = labDir.resolve(fname);

        try (InputStream in = file.getInputStream()) {
            Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
        }

        booking.setReportUploaded(true);
        booking.setReportFileName(fname);
        booking.setBookingStatus(com.vmmedico.Appointment.entity.LabBooking.BookingStatus.COMPLETED);
        bookingRepo.save(booking);

        LabReport rep = LabReport.builder()
                .booking(booking)
                .fileName(fname)
                .filePath(target.toString())
                .uploadedAt(Instant.now())
                .uploadedBy(uploadedBy)
                .build();
        return reportRepo.save(rep);
    }

    public Resource loadReport(Long bookingId) throws MalformedURLException {
        LabBooking booking = bookingRepo.findById(bookingId).orElseThrow(() -> new RuntimeException("Booking not found"));
        LabReport rep = reportRepo.findByBooking(booking).orElseThrow(() -> new RuntimeException("Report not found"));
        Path file = Paths.get(rep.getFilePath());
        Resource resource = new UrlResource(file.toUri());
        if (!resource.exists()) throw new RuntimeException("File not found");
        return resource;
    }
}
