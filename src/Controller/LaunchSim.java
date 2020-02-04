package Controller;
import java.io.File;
import java.io.FileOutputStream;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import View.PetrolGUI;
public class LaunchSim {
    private static int seed;
    private static double gallonPrice;
    private static double pChance;
    private static double qChance;
    private static int noOfPumps;
    private static int noOfTills;
    private static int ticks;
    private static boolean withTrucks;
    public static void main(String[] args){
        if(args.length > 1){
            seed = Integer.parseInt(args[0]);
            gallonPrice = Double.parseDouble(args[1]);
            pChance = Double.parseDouble(args[2]);
            qChance = Double.parseDouble(args[3]);
            noOfPumps = Integer.parseInt(args[4]);
            noOfTills = Integer.parseInt(args[5]);
        }
        else{
            gallonPrice = 1.20;
            pChance = 0.02;
            qChance = 0.02;
            noOfPumps = 4;
            noOfTills = 4;
            ticks = 1000;
            seed = 42;
            withTrucks = true;
        }
        Simulation s = new Simulation(seed, gallonPrice, pChance, qChance, noOfPumps, noOfTills, ticks, withTrucks);
        new PetrolGUI(s);
    }
    /**
     *
     * @param s - The Simulation object we want to create the file from.
     *
     */
    public void createFile(Simulation s) {
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("List shit");
            HSSFCellStyle currencyStyle = workbook.createCellStyle();
            HSSFDataFormat dataFormat = workbook.createDataFormat();
            currencyStyle.setDataFormat(dataFormat.getFormat("£#,##00.00"));
//Create Rows
            Row row0 = sheet.createRow(0);//Petrol money
            Row row1 = sheet.createRow(1);//shop money
            Row row2 = sheet.createRow(2);//total money
            Row row4 = sheet.createRow(4);//lost money
            Row row5 = sheet.createRow(5);//lost shop money
            Row row6 = sheet.createRow(6);//overall lost money
            Row row7 = sheet.createRow(8);//filled vehicle count
            Row row8 = sheet.createRow(9);//lost vehicle count
//first row
            Cell cell0 = row0.createCell(0);
            Cell cell1 = row0.createCell(1);
            cell0.setCellValue("Total Petrol Money: ");
            cell1.setCellStyle(currencyStyle);
            cell1.setCellValue(s.getDatabase().getPetrolEarnedMoney());
//second row
            Cell cell2 = row1.createCell(0);
            Cell cell3 = row1.createCell(1);
            cell2.setCellValue("Total Shop Money: ");
            cell3.setCellStyle(currencyStyle);
            cell3.setCellValue(s.getDatabase().getShopEarned());
//third row
            Cell cell4 = row2.createCell(0);
            Cell cell5 = row2.createCell(1);
            cell4.setCellValue("Overall Total Money: ");
            cell5.setCellStyle(currencyStyle);
            cell5.setCellValue(s.getDatabase().getOverallMoney());
//fourth row
            Cell cell6 = row4.createCell(0);
            Cell cell7 = row4.createCell(1);
            cell6.setCellValue("Lost Petrol Money: ");
            cell7.setCellStyle(currencyStyle);
            cell7.setCellValue(s.getDatabase().getLostPetrolMoney());
//fifth row
            Cell cell8 = row5.createCell(0);
            Cell cell9 = row5.createCell(1);
            cell8.setCellValue("Lost Potential Shop Money: ");
            cell9.setCellStyle(currencyStyle);
            cell9.setCellValue(s.getDatabase().getLostShopMoney());
//sixth row
            Cell cell10 = row6.createCell(0);
            Cell cell11 = row6.createCell(1);
            cell10.setCellValue("Overall Lost Money: ");
            cell11.setCellStyle(currencyStyle);
            cell11.setCellValue(s.getDatabase().getOverallLost());
//seventh row
            Cell cell12 = row7.createCell(0);
            Cell cell13 = row7.createCell(1);
            cell12.setCellValue("Total filled cars: ");
            cell13.setCellValue(s.getDatabase().getFilledVehicleCount());
//eighth row
            Cell cell14 = row8.createCell(0);
            Cell cell15 = row8.createCell(1);
            cell14.setCellValue("Total lost cars: ");
            cell15.setCellValue(s.getDatabase().getLostVehicleCount());
//Auto size columns
            for(int i = 0; i < 2; i++){
                sheet.autoSizeColumn(i);
            }
            FileOutputStream out = new FileOutputStream(new File("d://testsheet.xls"));
            workbook.write(out);
            out.close();
            workbook.close();
            System.out.println("Excel file created!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}