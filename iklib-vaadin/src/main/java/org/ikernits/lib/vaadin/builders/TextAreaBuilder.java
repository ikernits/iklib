package org.ikernits.lib.vaadin.builders;

import com.vaadin.ui.TextArea;

@SuppressWarnings({"deprecation", "unused", "unchecked"})
public class TextAreaBuilder<T extends TextArea, B extends TextAreaBuilder<T, B>> extends AbstractTextFieldBuilder<T, B> {

    public TextAreaBuilder(T delegate) {
        super(delegate);
    }
    
    /**
     * @see com.vaadin.ui.TextArea#setRows
     */
    public B setRows(int rows) {
        delegate.setRows(rows);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.TextArea#setWordWrap
     */
    public B setWordWrap(boolean wordWrap) {
        delegate.setWordWrap(wordWrap);
        return self;
    }
    
}
