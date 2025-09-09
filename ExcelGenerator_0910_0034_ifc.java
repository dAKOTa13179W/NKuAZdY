// 代码生成时间: 2025-09-10 00:34:11
import org.apache.poi.ss.usermodel.*;
    import org.apache.poi.xssf.usermodel.XSSFWorkbook;

    import java.io.FileOutputStream;
    import java.io.IOException;

    /**
     * Excel表格自动生成器
     * 该类用于根据模板创建Excel文件
     */
    public class ExcelGenerator {

        /**
         * 创建Excel文件
         * @param sheetName 工作表名称
         * @param rows 要创建的行数
         * @param columns 要创建的列数
         * @param outputFileName 输出文件的全路径
         */
        public void createExcelFile(String sheetName, int rows, int columns, String outputFileName) {
            try (Workbook workbook = new XSSFWorkbook()) {
                // 创建一个新的工作表
                Sheet sheet = workbook.createSheet(sheetName);
                
                // 创建行和列
                for (int rowNum = 0; rowNum < rows; rowNum++) {
                    Row row = sheet.createRow(rowNum);
                    for (int cellNum = 0; cellNum < columns; cellNum++) {
                        Cell cell = row.createCell(cellNum);
                        cell.setCellValue("" + (char)('A' + cellNum) + (rowNum + 1)"); // 设置单元格值
                    }
                }

                // 将工作簿写入文件系统
                try (FileOutputStream outputStream = new FileOutputStream(outputFileName)) {
                    workbook.write(outputStream);
                }
            } catch (IOException e) {
                // 错误处理：打印异常信息
                System.err.println("Error creating Excel file: " + e.getMessage());
            }
        }

        public static void main(String[] args) {
            ExcelGenerator generator = new ExcelGenerator();
            // 调用方法生成Excel文件
            generator.createExcelFile("SampleSheet", 10, 5, "example.xlsx");
        }
    }