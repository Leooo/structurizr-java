package com.structurizr.export;

import com.structurizr.Workspace;
import com.structurizr.export.plantuml.StructurizrPlantUMLExporter;
import com.structurizr.util.WorkspaceUtils;
import com.structurizr.view.ThemeUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

/**
 * Dumps all PlantUML diagrams from the test JSON fixtures into tmp/ for manual inspection.
 * Run with: ./gradlew :structurizr-export:dumpPlantUMLDiagrams
 */
public class DumpPlantUMLDiagrams {

    public static void main(String[] args) throws Exception {
        List<String> fixtures = List.of(
                "structurizr-export/src/test/resources/sequence.json",
                "structurizr-export/src/test/resources/groups.json",
                "structurizr-export/src/test/resources/amazon-web-services.json",
                "structurizr-export/src/test/resources/big-bank-plc.json"
        );

        File outputDir = new File("tmp");
        outputDir.mkdirs();

        StructurizrPlantUMLExporter exporter = new StructurizrPlantUMLExporter();
        int total = 0;

        for (String fixturePath : fixtures) {
            File fixtureFile = new File(fixturePath).getCanonicalFile();
            if (!fixtureFile.exists()) {
                System.err.println("Fixture not found: " + fixtureFile);
                continue;
            }

            String fixturePrefix = fixtureFile.getName().replace(".json", "");
            Workspace workspace = WorkspaceUtils.loadWorkspaceFromJson(fixtureFile);
            ThemeUtils.loadThemes(workspace);

            for (Diagram diagram : exporter.export(workspace)) {
                String fileName = fixturePrefix + "-" + diagram.getKey() + ".puml";
                File out = new File(outputDir, fileName);
                Files.writeString(out.toPath(), diagram.getDefinition(), StandardCharsets.UTF_8);
                System.out.println("Wrote " + out);
                total++;
            }
        }

        System.out.println("Dumped " + total + " diagram(s) to " + outputDir.getCanonicalPath());
    }

}
