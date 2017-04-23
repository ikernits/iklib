package org.ikernits.lib.vaadin.builders;

import com.vaadin.ui.AbstractField;
import com.vaadin.data.HasValue.ValueChangeListener;

@SuppressWarnings({"deprecation", "unused", "unchecked"})
public class AbstractFieldBuilder<T extends AbstractField, B extends AbstractFieldBuilder<T, B>> extends AbstractComponentBuilder<T, B> {

    public AbstractFieldBuilder(T delegate) {
        super(delegate);
    }
    
    /**
     * @see com.vaadin.ui.AbstractField#setReadOnly
     */
    public B setReadOnly(boolean readOnly) {
        delegate.setReadOnly(readOnly);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.AbstractField#setValue
     */
    public B setValue(Object value) {
        delegate.setValue(value);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.AbstractField#addValueChangeListener
     */
    public B addValueChangeListener(ValueChangeListener valueChangeListener) {
        delegate.addValueChangeListener(valueChangeListener);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.AbstractField#setRequiredIndicatorVisible
     */
    public B setRequiredIndicatorVisible(boolean requiredIndicatorVisible) {
        delegate.setRequiredIndicatorVisible(requiredIndicatorVisible);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.AbstractField#setTabIndex
     */
    public B setTabIndex(int tabIndex) {
        delegate.setTabIndex(tabIndex);
        return self;
    }
    
}
