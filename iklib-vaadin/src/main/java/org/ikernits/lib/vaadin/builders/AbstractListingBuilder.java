package org.ikernits.lib.vaadin.builders;

import com.vaadin.ui.AbstractListing;

@SuppressWarnings({"deprecation", "unused", "unchecked"})
public class AbstractListingBuilder<T extends AbstractListing, B extends AbstractListingBuilder<T, B>> extends AbstractComponentBuilder<T, B> {

    public AbstractListingBuilder(T delegate) {
        super(delegate);
    }
    
    /**
     * @see com.vaadin.ui.AbstractListing#setTabIndex
     */
    public B setTabIndex(int tabIndex) {
        delegate.setTabIndex(tabIndex);
        return self;
    }
    
}
