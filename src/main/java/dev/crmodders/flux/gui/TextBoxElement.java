package dev.crmodders.flux.gui;

import dev.crmodders.flux.gui.base.BaseTextInput;

public class TextBoxElement extends BaseTextInput {

    public void setContent(String content) {
        super.editor.setText(content);
    }

    public String getContent() {
        return super.editor.toString();
    }


}
