package org.ikernits.lib.vaadin.builders;

import com.vaadin.ui.AbstractLocalDateField;

@SuppressWarnings({"deprecation", "unused", "unchecked"})
public class AbstractLocalDateFieldBuilder<T extends AbstractLocalDateField, B extends AbstractLocalDateFieldBuilder<T, B>> extends AbstractDateFieldBuilder<T, B> {

    public AbstractLocalDateFieldBuilder(T delegate) {
        super(delegate);
    }
    
}
