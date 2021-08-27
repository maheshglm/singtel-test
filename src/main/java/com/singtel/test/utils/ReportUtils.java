package com.singtel.test.utils;

import com.singtel.test.pages.todo.TodoPage;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class ReportUtils {

    private String targetDir;

    public void setTargetDir(String targetDir) {
        this.targetDir = targetDir;
    }

    private String getTargetDir() {
        return targetDir;
    }

    public void generateReports() {
        File reportOutputDirectory = new File(getTargetDir());
        List<String> jsonFiles = this.getAllJsonFilesUnderTarget(getTargetDir());

        Configuration configuration = new Configuration(reportOutputDirectory, "Demo");
        configuration.setParallelTesting(true);
        configuration.setRunWithJenkins(false);
        configuration.setBuildNumber("1.0");
        configuration.addClassifications("Platform", System.getProperty("os.name"));
        configuration.addClassifications("Browser", "Chrome");
        configuration.addClassifications("Url", TodoPage.TODOMVC_URL);
        ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
        reportBuilder.generateReports();
    }

    public void generateReports(String targetDir) {
        setTargetDir(targetDir);
        generateReports();
    }

    private List<String> getAllJsonFilesUnderTarget(String folderLocation) {
        List<String> jsonFiles = new ArrayList<>();
        File directory = new File(folderLocation);
        File[] files = directory.listFiles((file, name) -> name.endsWith(".json"));
        if (files != null && files.length > 0) {
            for (File f : files) {
                jsonFiles.add(folderLocation + "/" + f.getName());
            }
        }
        return jsonFiles;
    }

}
