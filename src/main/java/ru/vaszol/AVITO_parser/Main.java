package ru.vaszol.AVITO_parser;

import ru.vaszol.AVITO_parser.model.Parser;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        List<Parser> parserList = new ArrayList<>();
        String arg = "?p=";

        for(int i = 0; i < 1;i++) {
            Document document = Jsoup.connect("https://m.avito.ru/search/items" + arg + i)
                    .data("category_id","24")
                    .data("params[201]","1059")
                    .data("params[496][from]","5120")
                    .data("params[496][to]","5120")
                    .data("region_id","625670")
                    .data("location_id","625810")
                    .post();

            Elements h3Elements = document.getElementsByAttributeValue("class", "b-item js-catalog-item-enum ");

            h3Elements.forEach(h3Element -> {
                Element element = h3Element;
                Element elementBase = element.getElementsByClass("item-link").get(0).getElementsByClass("item-link").get(0);
                Element elementInfo = elementBase.getElementsByClass("item-info").get(0);
                String url = element.getElementsByClass("item-link").attr("href");
                String title = elementBase.getElementsByClass("item-header").text();
                String price = elementBase.getElementsByClass("item-price-value").text();
                String district = elementInfo.getElementsByClass("info-metro-district").text();
                String address = elementInfo.getElementsByClass("info-address").text();
                String date = elementInfo.getElementsByClass("info-date").text();

                parserList.add(new Parser(url, title,price,district,address,date));
            });
        }

        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        HSSFSheet hssfSheet = hssfWorkbook.createSheet("Проверка");


        int rowNum = 0;

        Row row = hssfSheet.createRow(rowNum);
        row.createCell(1).setCellValue("ссылка");
        row.createCell(2).setCellValue("Краткое описание");
        row.createCell(3).setCellValue("цена");
        row.createCell(4).setCellValue("Район/метро");
        row.createCell(5).setCellValue("Адрес");
        row.createCell(6).setCellValue("Дата обновления");


        for (Parser parserSayt : parserList) {
            try {
                createSheetHeader(hssfSheet, ++rowNum, parserSayt);
            } catch (IOException e) {
                System.out.println("ошибка в строке");
                e.printStackTrace();
            }
            try{
                FileOutputStream out = new FileOutputStream(new File("Voronezh.xls"));
                hssfWorkbook.write(out);

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Создан/Обновил файл Voronezh.xls");
            }

            System.out.println("Excel добавлена строка!");

        }
        System.out.println("Excel файл успешно создан!");

    }

    private static void createSheetHeader(HSSFSheet sheet, int rowNum, Parser parserSayt) throws IOException {
        Row row = sheet.createRow(rowNum);

        row.createCell(1).setCellValue("https://m.avito.ru" + parserSayt.getUrl());
        row.createCell(2).setCellValue(parserSayt.getName());
        row.createCell(3).setCellValue(parserSayt.getPrice());
        row.createCell(4).setCellValue(parserSayt.getDistrict());
        row.createCell(5).setCellValue(parserSayt.getAddress());
        row.createCell(6).setCellValue(parserSayt.getDate());

    }
}

