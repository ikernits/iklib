package org.ikernits.lib.vaadin.builders;

import com.vaadin.ui.CheckBox;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.event.FieldEvents.FocusListener;

@SuppressWarnings({"deprecation", "unused", "unchecked"})
public class CheckBoxBuilder<T extends CheckBox, B extends CheckBoxBuilder<T, B>> extends AbstractFieldBuilder<T, B> {

    public CheckBoxBuilder(T delegate) {
        super(delegate);
    }
    
    /**
     * @see com.vaadin.ui.CheckBox#setValue
     */
    public B setValue(Boolean value) {
        delegate.setValue(value);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.CheckBox#addFocusListener
     */
    public B addFocusListener(FocusListener focusListener) {
        delegate.addFocusListener(focusListener);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.CheckBox#addBlurListener
     */
    public B addBlurListener(BlurListener blurListener) {
        delegate.addBlurListener(blurListener);
        return self;
    }
    
}
