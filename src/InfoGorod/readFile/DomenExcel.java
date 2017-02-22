package InfoGorod.readFile;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class DomenExcel implements DomenReadActionChoose {
    private File file;
    private ArrayList<String> domenList = new ArrayList<>();
    private static ArrayList<String> finalList = new ArrayList<>();
    private Workbook myExcelBook;

    public DomenExcel(File file) throws IOException {
       this.file = file;
       choose();
    }

    public void choose() throws IOException {
        if (file.getName().endsWith(".xls")) {
            myExcelBook = new HSSFWorkbook(new FileInputStream(this.file));
        } else if (file.getName().endsWith(".xlsx")) {
            myExcelBook = new XSSFWorkbook(new FileInputStream(this.file));
        }
    }

    public void action() {
        Sheet myExcelSheet = myExcelBook.getSheetAt(0);
        //список доменов
        domenList.clear();
        finalList.clear();
        for (int w = 1; w< myExcelSheet.getLastRowNum()+1; w++){
            if (myExcelSheet.getRow(w).getCell(4) == null)
                domenList.add(String.valueOf(myExcelSheet.getRow(w).getCell(2)));
            else
                domenList.add(String.valueOf(myExcelSheet.getRow(w).getCell(4)));
        }
        for (String s : domenList) {
            if (!finalList.contains(s)) {
                finalList.add(s);
            }
        }
        System.out.println(finalList);
    }

    public String getFileName(){
        return this.file.getName();
    }

    public ArrayList<String> getDomenName(){
        return finalList;
    }
}
