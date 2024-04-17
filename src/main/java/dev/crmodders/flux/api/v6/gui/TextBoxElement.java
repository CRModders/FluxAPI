package dev.crmodders.flux.api.gui;

import dev.crmodders.flux.api.gui.base.BaseTextInput;

public class TextBoxElement extends BaseTextInput {

    public void setContent(String content) {
        super.editor.setText(content);
    }

    public String getContent() {
        return super.editor.toString();
    }


}
