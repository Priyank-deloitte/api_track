import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class javaUtility {
    String tokenValue = "1e3fbfc04d510423f16db6b1976fb8d49a339e4e486ef5006ebe118b552a2a32";

    public static FileInputStream fileinput; //Pre-defined class present in java for reading the file
    public static XSSFWorkbook workbook;   //XSSFWorkbook is a class For workbook, we have created an object of the workbook
    public static XSSFSheet worksheet;  //XSSFSheet is a class For worksheet, we have created an object of the worksheet
    public static XSSFRow row;       //XSSFRow is a class For row, we have created an object of the row
    public static XSSFCell cell;    //XSSFCell is a class For cell, we have created an object of the Cell

    public static int getRowCount(String FilePath, String xlsheetname) throws IOException {
        int rowcount;
        fileinput = new FileInputStream(FilePath); //USED HERE TO READ THE FILE FROM THE LOCATION

        //With the workbook object we can navigate to that workbook which is Present inside that File
        workbook = new XSSFWorkbook(fileinput); //It will accept the File as a Parameter where workbook is kept

        //Inside the workbook There are multiple sheets and the sheet which we want to read its name
        worksheet = workbook.getSheet(xlsheetname);  //have  passed as a parameter to get that sheet and  worksheet object will keep its reference

        //Sheet object  will provide us the count of rows we have
        rowcount = worksheet.getLastRowNum(); //Returns number of rows
        workbook.close();  //Closed the workbook
        fileinput.close(); //Closes the file
        return rowcount; //Row count returned
    }

    public static String getCellValue(String FilePath, String SheetName, int rownum, int colnum) throws IOException {

        fileinput = new FileInputStream(FilePath);
        workbook = new XSSFWorkbook(fileinput);
        worksheet = workbook.getSheet(SheetName);
        row = worksheet.getRow(rownum);
        cell = row.getCell(colnum);  //This wil get us the value of that cell

        DataFormatter formatter = new DataFormatter();
        String values = formatter.formatCellValue(cell);

        return values;
    }
}
