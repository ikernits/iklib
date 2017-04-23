package org.ikernits.lib.vaadin.builders;

import com.vaadin.ui.PasswordField;

@SuppressWarnings({"deprecation", "unused", "unchecked"})
public class PasswordFieldBuilder<T extends PasswordField, B extends PasswordFieldBuilder<T, B>> extends TextFieldBuilder<T, B> {

    public PasswordFieldBuilder(T delegate) {
        super(delegate);
    }
    
}
