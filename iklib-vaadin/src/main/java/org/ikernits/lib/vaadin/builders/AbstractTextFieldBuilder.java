package org.ikernits.lib.vaadin.builders;

import com.vaadin.ui.AbstractTextField;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.shared.ui.ValueChangeMode;

@SuppressWarnings({"deprecation", "unused", "unchecked"})
public class AbstractTextFieldBuilder<T extends AbstractTextField, B extends AbstractTextFieldBuilder<T, B>> extends AbstractFieldBuilder<T, B> {

    public AbstractTextFieldBuilder(T delegate) {
        super(delegate);
    }
    
    /**
     * @see com.vaadin.ui.AbstractTextField#setValue
     */
    public B setValue(String value) {
        delegate.setValue(value);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.AbstractTextField#setMaxLength
     */
    public B setMaxLength(int maxLength) {
        delegate.setMaxLength(maxLength);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.AbstractTextField#setPlaceholder
     */
    public B setPlaceholder(String placeholder) {
        delegate.setPlaceholder(placeholder);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.AbstractTextField#setSelection
     */
    public B setSelection(int param1, int param2) {
        delegate.setSelection(param1, param2);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.AbstractTextField#setCursorPosition
     */
    public B setCursorPosition(int cursorPosition) {
        delegate.setCursorPosition(cursorPosition);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.AbstractTextField#setValueChangeMode
     */
    public B setValueChangeMode(ValueChangeMode valueChangeMode) {
        delegate.setValueChangeMode(valueChangeMode);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.AbstractTextField#setValueChangeTimeout
     */
    public B setValueChangeTimeout(int valueChangeTimeout) {
        delegate.setValueChangeTimeout(valueChangeTimeout);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.AbstractTextField#addFocusListener
     */
    public B addFocusListener(FocusListener focusListener) {
        delegate.addFocusListener(focusListener);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.AbstractTextField#addBlurListener
     */
    public B addBlurListener(BlurListener blurListener) {
        delegate.addBlurListener(blurListener);
        return self;
    }
    
}
