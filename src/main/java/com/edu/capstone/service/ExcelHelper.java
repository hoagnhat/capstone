package com.edu.capstone.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.edu.capstone.repository.ProfileRepository;
import com.edu.capstone.request.AccountRequest;
import com.edu.capstone.request.AddStudentIntoClassRequest;

@Service
public class ExcelHelper {
	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	static String[] HEADERs = { "Khoa", "Role Id", "Specialization Id", "Person Email", "Name", "Age", "Avatar",
			"Phone", "Gender", "Address" };
	static String SHEET = "Accounts";

	public static boolean hasExcelFormat(MultipartFile file) {
		if (!TYPE.equals(file.getContentType())) {
			return false;
		}
		return true;
	}

	public static List<AccountRequest> excelToTutorials(InputStream is) {
		try {
			Workbook workbook = new XSSFWorkbook(is);
			Sheet sheet = workbook.getSheet(SHEET);
			Iterator<Row> rows = sheet.iterator();
			List<AccountRequest> requests = new ArrayList<AccountRequest>();
			int rowNumber = 0;
			while (rows.hasNext()) {
				Row currentRow = rows.next();
				// skip header
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}
				Iterator<Cell> cellsInRow = currentRow.iterator();
				AccountRequest request = new AccountRequest();
				int cellIdx = 0;
				while (cellsInRow.hasNext()) {
					Cell currentCell = cellsInRow.next();
					switch (cellIdx) {
					case 0:
						request.setKhoa(Integer.parseInt(currentCell.getStringCellValue()));
						break;
					case 1:
						request.setRoleId(Integer.parseInt(currentCell.getStringCellValue()));
						break;
					case 2:
						request.setSpecializationId(Integer.parseInt(currentCell.getStringCellValue()));
						break;
					case 3:
						request.setPersonalEmail(currentCell.getStringCellValue());
						break;
					case 4:
						request.setName(currentCell.getStringCellValue());
						break;
					case 5:
						request.setAge(Integer.parseInt(currentCell.getStringCellValue()));
						break;
					case 6:
						request.setAvatar(currentCell.getStringCellValue());
						break;
					case 7:
						request.setPhone(currentCell.getStringCellValue());
						break;
					case 8:
						request.setGender(Integer.parseInt(currentCell.getStringCellValue()));
						break;
					case 9:
						request.setAddress(currentCell.getStringCellValue());
						break;
					default:
						break;
					}
					cellIdx++;
				}
				requests.add(request);
			}
			workbook.close();
			return requests;
		} catch (IOException e) {
			throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
		}
	}

	public static AddStudentIntoClassRequest excelToClassStudent(InputStream is) {
		try {
			Workbook workbook = new XSSFWorkbook(is);
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rows = sheet.iterator();
			AddStudentIntoClassRequest request = new AddStudentIntoClassRequest();
			request.setStudentIds(new ArrayList<String>());
			Row classRow = sheet.getRow(1);
			Cell classCell = classRow.getCell(0);
			request.setClassId(classCell.getStringCellValue());
			int rowNumber = 0;
			while (rows.hasNext()) {
				Row currentRow = rows.next();
				// skip header
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}
				Cell studentCell = currentRow.getCell(1);
				request.getStudentIds().add(studentCell.getStringCellValue());
			}
			workbook.close();
			return request;
		} catch (IOException e) {
			throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
		}
	}

	public static ByteArrayInputStream registerSample() {
		try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("Accounts");

			Row row = sheet.createRow(0);
			// Creating header
			Cell cell = row.createCell(0);
			cell.setCellValue("Khoa");

			cell = row.createCell(1);
			cell.setCellValue("Role Id");

			cell = row.createCell(2);
			cell.setCellValue("Specialization Id");

			cell = row.createCell(3);
			cell.setCellValue("Person Email");

			cell = row.createCell(4);
			cell.setCellValue("Name");

			cell = row.createCell(5);
			cell.setCellValue("Age");

			cell = row.createCell(6);
			cell.setCellValue("Avatar");

			cell = row.createCell(7);
			cell.setCellValue("Phone");

			cell = row.createCell(8);
			cell.setCellValue("Gender");

			cell = row.createCell(9);
			cell.setCellValue("Address");

			Row dataRow = sheet.createRow(1);

			dataRow.createCell(0).setCellValue("14");
			dataRow.createCell(1).setCellValue("2");
			dataRow.createCell(2).setCellValue("1");
			dataRow.createCell(3).setCellValue("nguyenndb511@gmail.com");
			dataRow.createCell(4).setCellValue("Nguyễn Duy Bảo Nguyên");
			dataRow.createCell(5).setCellValue("20");
			dataRow.createCell(6).setCellValue("1MWs_eK1xy8jHKe6M3-MXm2OUJMVftl7s");
			dataRow.createCell(7).setCellValue("0905003872");
			dataRow.createCell(8).setCellValue("0");
			dataRow.createCell(9).setCellValue("Da Nang");

			sheet.autoSizeColumn(0);
			sheet.autoSizeColumn(1);
			sheet.autoSizeColumn(2);
			sheet.autoSizeColumn(3);
			sheet.autoSizeColumn(4);
			sheet.autoSizeColumn(5);
			sheet.autoSizeColumn(6);
			sheet.autoSizeColumn(7);
			sheet.autoSizeColumn(8);
			sheet.autoSizeColumn(9);

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			workbook.write(outputStream);
			return new ByteArrayInputStream(outputStream.toByteArray());
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static ByteArrayInputStream classStudentSample() {
		try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("Add Students");

			Row row = sheet.createRow(0);

			// Creating header
			Cell cell = row.createCell(0);
			cell.setCellValue("Class Id");

			cell = row.createCell(1);
			cell.setCellValue("Student Id");

			sheet.autoSizeColumn(0);
			sheet.autoSizeColumn(1);
			Row dataRow1 = sheet.createRow(1);
			dataRow1.createCell(0).setCellValue("SE1401");
			dataRow1.createCell(1).setCellValue("SI1400001");
			Row dataRow2 = sheet.createRow(2);
			dataRow2.createCell(1).setCellValue("SI1400002");
			Row dataRow3 = sheet.createRow(3);
			dataRow3.createCell(1).setCellValue("SI1400003");
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			workbook.write(outputStream);
			return new ByteArrayInputStream(outputStream.toByteArray());
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}
}
