/**
 * 
 */
package com.dikmen.jasperReportDemo.service;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import com.dikmen.jasperReportDemo.model.Student;
import com.dikmen.jasperReportDemo.model.Subject;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.base.JRBaseTextField;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * @author mutlu.dikmen
 *
 */

@Component
public class JasperPrintService {
	
	public JasperPrint createJasperPrint() {
		
		try {
			
			String filePath = ResourceUtils.getFile("classpath:Student.jrxml").getAbsolutePath();

			Subject subject1 = new Subject("Nesne Yönelimli programlama", 80);
			Subject subject2 = new Subject("Veri Yapıları ve Algoritmalar", 70);
			Subject subject3 = new Subject("Veri Tabanı Sistemleri", 50);
			Subject subject4 = new Subject("İşletim Sistemleri", 40);
			Subject subject5 = new Subject("Yazılım Mühendisliği Temelleri", 60);

			List<Subject> subjects = new ArrayList<Subject>();

			subjects.add(subject1);
			subjects.add(subject2);
			subjects.add(subject3);
			subjects.add(subject4);
			subjects.add(subject5);

			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(subjects);

			JRBeanCollectionDataSource chartDataSource = new JRBeanCollectionDataSource(subjects);

//	    Create parameter data
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("studentName", "Mutlu DİKMEN");
			parameters.put("tableData", dataSource);
			parameters.put("subReport", getSubReport());
			parameters.put("subDataSource", getSubReportDataSource());
			parameters.put("subParameters", getSubParameters());

//	    Compile jasper report file            
			JasperReport report = JasperCompileManager.compileReport(filePath);

//	    Manipulating parameters with java code 
			JRBaseTextField textField = (JRBaseTextField) report.getTitle().getElementByKey("name");
			textField.setForecolor(Color.yellow);

//	    With the help of jasperreports.properties file we can set Encodings globally. Otherwise we must to set every property we want to change 
//	    textField.setPdfEncoding("Cp1254");

//	    JasperPrint print = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());

//	    We must fill the report with chart data source if we want to show charts in the report
			return JasperFillManager.fillReport(report, parameters, chartDataSource);
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		
	}

	private JasperReport getSubReport() {

		try {

			String filePath = ResourceUtils.getFile("classpath:SubReport.jrxml").getAbsolutePath();
			return JasperCompileManager.compileReport(filePath);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	private JRBeanCollectionDataSource getSubReportDataSource() {

		Student student1 = new Student(1L, "Aziz", "SANCAR", "Kızılay", "Ankara");
		Student student2 = new Student(2L, "Canan", "KARATAY", "Taksim", "İstanbul");

		List<Student> students = new ArrayList<Student>();
		students.add(student1);
		students.add(student2);

		return new JRBeanCollectionDataSource(students);
	}

	private Map<String, Object> getSubParameters() {

		Map<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("studentName", "Dikmen Mutlu");

		return parameters;
	}

}
