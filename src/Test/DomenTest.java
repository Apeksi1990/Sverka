package Test;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class DomenTest {

    File x;
    ArrayList<String> domenList = new ArrayList<>();
    static ArrayList<String> finalList = new ArrayList<>();

    DomenTest(File x) throws IOException {
        this.x = x;

        HSSFWorkbook myExcelBook = new HSSFWorkbook(new FileInputStream(x));
        HSSFSheet myExcelSheet = myExcelBook.getSheetAt(0);

        //список доменов
        domenList.clear();
        finalList.clear();
        for (int w=1; w<myExcelSheet.getLastRowNum()+1; w++){
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
        return x.getName();
    }

    public ArrayList<String> getDomenName(){
        return finalList;
    }

}
