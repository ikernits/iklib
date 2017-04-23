package org.ikernits.lib.vaadin.builders;

import com.vaadin.ui.Label;
import com.vaadin.shared.ui.ContentMode;

@SuppressWarnings({"deprecation", "unused", "unchecked"})
public class LabelBuilder<T extends Label, B extends LabelBuilder<T, B>> extends AbstractComponentBuilder<T, B> {

    public LabelBuilder(T delegate) {
        super(delegate);
    }
    
    /**
     * @see com.vaadin.ui.Label#setValue
     */
    public B setValue(String value) {
        delegate.setValue(value);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.Label#setContentMode
     */
    public B setContentMode(ContentMode contentMode) {
        delegate.setContentMode(contentMode);
        return self;
    }
    
}
