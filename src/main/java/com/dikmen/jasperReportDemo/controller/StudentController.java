/**
 * 
 */
package com.dikmen.jasperReportDemo.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dikmen.jasperReportDemo.service.JasperPrintService;

import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

/**
 * @author mutlu.dikmen
 *
 */
@RestController
@RequestMapping("/api/student")
public class StudentController {
	
	@Autowired
	JasperPrintService printService;

	@GetMapping("/pdfReport")
	public void getPdfReport(HttpServletResponse response) {

		try {

			JRPdfExporter exporter = new JRPdfExporter();
			SimplePdfReportConfiguration reportConfigPDF = new SimplePdfReportConfiguration();
			reportConfigPDF.setSizePageToContent(true);
			exporter.setConfiguration(reportConfigPDF);
			exporter.setExporterInput(new SimpleExporterInput(printService.createJasperPrint()));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
			response.setHeader("Content-Disposition", "attachment;filename=student.pdf");
			response.setContentType("application/pdf");
			exporter.exportReport();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@GetMapping("/xlsxReport")
	public void getXlsxReport(HttpServletResponse response) {

		try {

			JRXlsxExporter exporter = new JRXlsxExporter();
			SimpleXlsxReportConfiguration reportConfigXLS = new SimpleXlsxReportConfiguration();
			reportConfigXLS.setSheetNames(new String[] { "sheet1" });
			exporter.setConfiguration(reportConfigXLS);
			exporter.setExporterInput(new SimpleExporterInput(printService.createJasperPrint()));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
			response.setHeader("Content-Disposition", "attachment;filename=student.xlsx");
			response.setContentType("application/octet-stream");
			exporter.exportReport();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@GetMapping("/docxReport")
	public void getDocxReport(HttpServletResponse response) {

		try {

			JRDocxExporter exporter = new JRDocxExporter();
			exporter.setExporterInput(new SimpleExporterInput(printService.createJasperPrint()));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
			response.setHeader("Content-Disposition", "attachment;filename=student.docx");
			response.setContentType("application/octet-stream");
			exporter.exportReport();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
