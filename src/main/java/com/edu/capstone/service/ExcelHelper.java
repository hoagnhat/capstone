package com.edu.capstone.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.edu.capstone.request.AccountRequest;

@Service
public class ExcelHelper {
	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	static String[] HEADERs = { "Khoa", "Role Id", "Specialization Id", "Person Email", "Name", "Age", "Avatar", "Phone",
			"Gender", "Address" };
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
}
