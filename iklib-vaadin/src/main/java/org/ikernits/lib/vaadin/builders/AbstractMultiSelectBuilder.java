package org.ikernits.lib.vaadin.builders;

import com.vaadin.ui.AbstractMultiSelect;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.event.selection.MultiSelectionListener;
import com.vaadin.ui.ItemCaptionGenerator;
import java.util.Set;

@SuppressWarnings({"deprecation", "unused", "unchecked"})
public class AbstractMultiSelectBuilder<T extends AbstractMultiSelect, B extends AbstractMultiSelectBuilder<T, B>> extends AbstractListingBuilder<T, B> {

    public AbstractMultiSelectBuilder(T delegate) {
        super(delegate);
    }
    
    /**
     * @see com.vaadin.ui.AbstractMultiSelect#setReadOnly
     */
    public B setReadOnly(boolean readOnly) {
        delegate.setReadOnly(readOnly);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.AbstractMultiSelect#setValue
     */
    public B setValue(Set value) {
        delegate.setValue(value);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.AbstractMultiSelect#addValueChangeListener
     */
    public B addValueChangeListener(ValueChangeListener valueChangeListener) {
        delegate.addValueChangeListener(valueChangeListener);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.AbstractMultiSelect#addSelectionListener
     */
    public B addSelectionListener(MultiSelectionListener selectionListener) {
        delegate.addSelectionListener(selectionListener);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.AbstractMultiSelect#setRequiredIndicatorVisible
     */
    public B setRequiredIndicatorVisible(boolean requiredIndicatorVisible) {
        delegate.setRequiredIndicatorVisible(requiredIndicatorVisible);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.AbstractMultiSelect#setItemCaptionGenerator
     */
    public B setItemCaptionGenerator(ItemCaptionGenerator itemCaptionGenerator) {
        delegate.setItemCaptionGenerator(itemCaptionGenerator);
        return self;
    }
    
}
