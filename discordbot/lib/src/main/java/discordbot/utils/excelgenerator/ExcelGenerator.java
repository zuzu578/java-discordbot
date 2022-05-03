package discordbot.utils.excelgenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import discordbot.TaikoVo.TaikoDiscordBotVO;

public class ExcelGenerator {

    public void excelGenerate(HashMap<String, ArrayList<String>> result) throws IOException {
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("첫번째 시트");
        Row row = null;
        Cell cell = null;
        int rowNum = 0;
        row = sheet.createRow(rowNum++);
        cell = row.createCell(0);
        cell.setCellValue("노래제목");
        cell = row.createCell(1);
        cell.setCellValue("장르");
        cell = row.createCell(2);
        cell.setCellValue("난이도");

        for (int i = 0; i < result.get("songNameList").size(); i++) {

            row = sheet.createRow(rowNum++);
            cell = row.createCell(0);
            cell.setCellValue(result.get("songNameList").get(i));
            cell = row.createCell(1);
            cell.setCellValue(result.get("songGenreList").get(i));
            cell = row.createCell(2);
            cell.setCellValue(result.get("difficultList").get(i));
            cell = row.createCell(3);

        }

        File currDir = new File("."); // 현재 프로젝트 경로를 가져옴
        String path = currDir.getAbsolutePath();
        String fileLocation = path.substring(0, path.length() - 1) + "taiko.xlsx"; // 파일명 설정

        FileOutputStream fileOutputStream = new FileOutputStream(fileLocation); // 파일 생성
        wb.write(fileOutputStream); // 엑셀파일로 작성
        wb.close();
    }

}
