package com.structurizr.export.mermaid;

import com.structurizr.export.Diagram;
import com.structurizr.view.ModelView;

public class MermaidDiagram extends Diagram {

    private String cssContent;

    public MermaidDiagram(ModelView view, String definition) {
        super(view, definition);
    }

    /**
     * Optional CSS content to be written as a companion .css file alongside the .mmd file.
     * Used to inject per-participant fill/stroke/colour rules for sequence diagrams,
     * since Mermaid sequence diagrams hardcode fill="#eaeaea" on participant boxes.
     */
    public String getCssContent() {
        return cssContent;
    }

    public void setCssContent(String cssContent) {
        this.cssContent = cssContent;
    }

    public boolean hasCssContent() {
        return cssContent != null && !cssContent.isEmpty();
    }

    @Override
    public String getFileExtension() {
        return "mmd";
    }

}
