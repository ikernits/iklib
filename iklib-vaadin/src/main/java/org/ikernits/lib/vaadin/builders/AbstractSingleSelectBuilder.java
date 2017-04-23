package org.ikernits.lib.vaadin.builders;

import com.vaadin.ui.AbstractSingleSelect;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.event.selection.SingleSelectionListener;

@SuppressWarnings({"deprecation", "unused", "unchecked"})
public class AbstractSingleSelectBuilder<T extends AbstractSingleSelect, B extends AbstractSingleSelectBuilder<T, B>> extends AbstractListingBuilder<T, B> {

    public AbstractSingleSelectBuilder(T delegate) {
        super(delegate);
    }
    
    /**
     * @see com.vaadin.ui.AbstractSingleSelect#setReadOnly
     */
    public B setReadOnly(boolean readOnly) {
        delegate.setReadOnly(readOnly);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.AbstractSingleSelect#setValue
     */
    public B setValue(Object value) {
        delegate.setValue(value);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.AbstractSingleSelect#addValueChangeListener
     */
    public B addValueChangeListener(ValueChangeListener valueChangeListener) {
        delegate.addValueChangeListener(valueChangeListener);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.AbstractSingleSelect#addSelectionListener
     */
    public B addSelectionListener(SingleSelectionListener selectionListener) {
        delegate.addSelectionListener(selectionListener);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.AbstractSingleSelect#setRequiredIndicatorVisible
     */
    public B setRequiredIndicatorVisible(boolean requiredIndicatorVisible) {
        delegate.setRequiredIndicatorVisible(requiredIndicatorVisible);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.AbstractSingleSelect#setSelectedItem
     */
    public B setSelectedItem(Object selectedItem) {
        delegate.setSelectedItem(selectedItem);
        return self;
    }
    
}
