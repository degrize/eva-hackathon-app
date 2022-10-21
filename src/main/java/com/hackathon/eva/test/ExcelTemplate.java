package com.hackathon.eva.test;

import java.io.IOException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public class ExcelTemplate {

    public static void main(String[] args) throws IOException, InvalidFormatException {
        String text = ", bonjour, les, amis";
        System.out.println(text.startsWith(", "));
        if (text.startsWith(", ")) text = text.substring(2);
        System.out.println(text);
    }
}
