package com.plugin.transform.values;

import com.plugin.transform.values.utils.Logger;

import org.gradle.api.GradleException;

import java.io.File;
import java.util.HashMap;

import javax.annotation.Nullable;

public class TransformValuesPluginConfig {
    private static final String VALUE_FILE = "values";
    private static final String STRINGS_FILE = "strings.xml";
    private static final String XLS = "xls";
    private static final String XLSX = "xlsx";

    public String excelPath;
    public String outputPath;
    public String sheetName;
    public int stringNameColumn = 0;
    public int defaultValueColumn = -1;
    private HashMap<Integer, String> mapLanguage = new HashMap<>();

    public TransformValuesPluginConfig(){

    }

    public void checkParams() {
        if (!isXlsFile() && !isXlsxFile()) {
            throw new GradleException(" The is not excel file " + excelPath);
        }

        if (outputPath.isEmpty()) {
            throw new GradleException(" output path is empty ");
        }
    }

    public File getExcelFile() {
        return new File(excelPath);
    }

    public void mappingValues(int column, String languageCode) {
        mapLanguage.putIfAbsent(column, languageCode);
    }

    @Nullable
    public File buildValuesFile(int index) {
        String languageCode = mapLanguage.get(index);
        if (languageCode == null) {
            return null;
        }
        StringBuilder valuesName = new StringBuilder(VALUE_FILE);
        if (index != defaultValueColumn) {
            valuesName.append("-");
            valuesName.append(languageCode);
        }
        valuesName.append(File.separator);
        valuesName.append(STRINGS_FILE);
        Logger.INSTANCE.i(" buildValuesFile " + valuesName.toString());
        return new File((getOutputPath()), valuesName.toString());
    }

    public boolean isXlsFile() {
        return !excelPath.isEmpty() && excelPath.endsWith(XLS);
    }

    public boolean isXlsxFile() {
        return !excelPath.isEmpty() && excelPath.endsWith(XLSX);
    }

    private String getOutputPath() {
        return outputPath.replace("\\", File.separator);
    }

    @Override
    public String toString() {
        return "TransformValuesPluginConfig{" +
                "excelPath='" + excelPath + '\'' +
                ", outputPath='" + outputPath + '\'' +
                ", sheetName='" + sheetName + '\'' +
                ", stringNameColumn=" + stringNameColumn +
                ", defaultValueColumn=" + defaultValueColumn +
                ", mapLanguage=" + mapLanguage +
                '}';
    }
}
