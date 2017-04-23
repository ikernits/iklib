package org.ikernits.lib.vaadin.builders;

import com.vaadin.ui.DateField;

@SuppressWarnings({"deprecation", "unused", "unchecked"})
public class DateFieldBuilder<T extends DateField, B extends DateFieldBuilder<T, B>> extends AbstractLocalDateFieldBuilder<T, B> {

    public DateFieldBuilder(T delegate) {
        super(delegate);
    }
    
    /**
     * @see com.vaadin.ui.DateField#setPlaceholder
     */
    public B setPlaceholder(String placeholder) {
        delegate.setPlaceholder(placeholder);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.DateField#setTextFieldEnabled
     */
    public B setTextFieldEnabled(boolean textFieldEnabled) {
        delegate.setTextFieldEnabled(textFieldEnabled);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.DateField#setAssistiveText
     */
    public B setAssistiveText(String assistiveText) {
        delegate.setAssistiveText(assistiveText);
        return self;
    }
    
}
