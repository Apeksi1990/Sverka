package InfoGorod.readFile;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

public class ReadExcel implements ReadFileChoose {
    private ArrayList<String> firstCol;
    private String thisDom;
    private File file;

    private Workbook myExcelBook;
    private InputStream ExcelFileToRead;

    public ReadExcel(ArrayList<String> firstCol, String thisDom, File file) throws IOException {
        this.firstCol = firstCol;
        this.thisDom = thisDom;
        this.file = file;
        ExcelFileToRead = new FileInputStream(file);
        choose();
    }

    public void choose() throws IOException {
        if (file.getName().endsWith(".xls")) {
            myExcelBook = new HSSFWorkbook(ExcelFileToRead);
        } else if (file.getName().endsWith(".xlsx")) {
            myExcelBook = new XSSFWorkbook(ExcelFileToRead);
        }
    }

    public void read() throws IOException {
        Sheet myExcelSheet = myExcelBook.getSheetAt(0);
        String fC;
        for (int i = 1; i< myExcelSheet.getLastRowNum()+1; i++){
            if (myExcelSheet.getRow(i).getCell(4) == null) {
                fC = String.valueOf(myExcelSheet.getRow(i).getCell(2));
            }
            else {
                fC = String.valueOf(myExcelSheet.getRow(i).getCell(4));
            }

            if (Objects.equals(fC, thisDom)) {
                firstCol.add(String.valueOf(myExcelSheet.getRow(i).getCell(0)) + "\n");
            }
        }
    }
}
