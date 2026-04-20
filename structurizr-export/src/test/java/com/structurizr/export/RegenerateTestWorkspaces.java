package com.structurizr.export;

import com.structurizr.Workspace;
import com.structurizr.dsl.StructurizrDslParser;
import com.structurizr.util.WorkspaceUtils;

import java.io.File;
import java.util.List;

/**
 * Regenerates JSON workspace fixtures under structurizr-export/src/test/resources.
 */
public class RegenerateTestWorkspaces {

    private record Fixture(String dslPath, String jsonPath) {
    }

    public static void main(String[] args) throws Exception {
        List<Fixture> fixtures = List.of(
                new Fixture(
                        "structurizr-export/src/test/resources/sequence.dsl",
                        "structurizr-export/src/test/resources/sequence.json"
                ),
                new Fixture(
                        "structurizr-export/src/test/resources/groups.dsl",
                        "structurizr-export/src/test/resources/groups.json"
                ),
                new Fixture(
                        "structurizr-export/src/test/resources/amazon-web-services.dsl",
                        "structurizr-export/src/test/resources/amazon-web-services.json"
                )
        );

        int updated = 0;
        for (Fixture fixture : fixtures) {
            File dslFile = new File(fixture.dslPath).getCanonicalFile();
            File jsonFile = new File(fixture.jsonPath).getCanonicalFile();

            if (!dslFile.exists()) {
                throw new IllegalStateException("DSL file not found: " + dslFile);
            }

            File parent = jsonFile.getParentFile();
            if (parent != null && !parent.exists() && !parent.mkdirs()) {
                throw new IllegalStateException("Could not create directory: " + parent);
            }

            StructurizrDslParser parser = new StructurizrDslParser();
            parser.parse(dslFile);
            Workspace workspace = parser.getWorkspace();

            WorkspaceUtils.saveWorkspaceToJson(workspace, jsonFile);
            System.out.println("Wrote " + jsonFile);
            updated++;
        }

        System.out.println("Regenerated " + updated + " workspace fixture(s).");
    }

}
