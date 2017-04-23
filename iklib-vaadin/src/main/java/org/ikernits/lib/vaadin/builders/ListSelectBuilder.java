package org.ikernits.lib.vaadin.builders;

import com.vaadin.ui.ListSelect;
import com.vaadin.data.provider.DataProvider;

@SuppressWarnings({"deprecation", "unused", "unchecked"})
public class ListSelectBuilder<T extends ListSelect, B extends ListSelectBuilder<T, B>> extends AbstractMultiSelectBuilder<T, B> {

    public ListSelectBuilder(T delegate) {
        super(delegate);
    }
    
    /**
     * @see com.vaadin.ui.ListSelect#setRows
     */
    public B setRows(int rows) {
        delegate.setRows(rows);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.ListSelect#setDataProvider
     */
    public B setDataProvider(DataProvider dataProvider) {
        delegate.setDataProvider(dataProvider);
        return self;
    }
    
}
