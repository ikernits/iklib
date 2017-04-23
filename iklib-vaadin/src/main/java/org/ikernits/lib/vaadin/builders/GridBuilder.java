package org.ikernits.lib.vaadin.builders;

import com.vaadin.ui.Grid;
import com.vaadin.data.ValueProvider;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.GridSortOrderBuilder;
import com.vaadin.event.SortEvent.SortListener;
import com.vaadin.event.selection.SelectionListener;
import com.vaadin.server.SerializableSupplier;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.Grid.FetchItemsCallback;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.StyleGenerator;
import com.vaadin.ui.components.grid.ColumnReorderListener;
import com.vaadin.ui.components.grid.ColumnResizeListener;
import com.vaadin.ui.components.grid.ColumnVisibilityChangeListener;
import com.vaadin.ui.components.grid.DescriptionGenerator;
import com.vaadin.ui.components.grid.DetailsGenerator;
import com.vaadin.ui.components.grid.HeaderRow;
import com.vaadin.ui.components.grid.ItemClickListener;
import com.vaadin.ui.renderers.AbstractRenderer;
import java.util.List;

@SuppressWarnings({"deprecation", "unused", "unchecked"})
public class GridBuilder<T extends Grid, B extends GridBuilder<T, B>> extends AbstractListingBuilder<T, B> {

    public GridBuilder(T delegate) {
        super(delegate);
    }
    
    /**
     * @see com.vaadin.ui.Grid#addSelectionListener
     */
    public B addSelectionListener(SelectionListener selectionListener) {
        delegate.addSelectionListener(selectionListener);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.Grid#addColumn
     */
    public B addColumn(String param1, AbstractRenderer param2) {
        delegate.addColumn(param1, param2);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.Grid#addColumn
     */
    public B addColumn(String column) {
        delegate.addColumn(column);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.Grid#addColumn
     */
    public B addColumn(ValueProvider column) {
        delegate.addColumn(column);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.Grid#addColumn
     */
    public B addColumn(ValueProvider param1, AbstractRenderer param2) {
        delegate.addColumn(param1, param2);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.Grid#setDetailsGenerator
     */
    public B setDetailsGenerator(DetailsGenerator detailsGenerator) {
        delegate.setDetailsGenerator(detailsGenerator);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.Grid#setDetailsVisible
     */
    public B setDetailsVisible(Object param1, boolean param2) {
        delegate.setDetailsVisible(param1, param2);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.Grid#setFrozenColumnCount
     */
    public B setFrozenColumnCount(int frozenColumnCount) {
        delegate.setFrozenColumnCount(frozenColumnCount);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.Grid#setHeightByRows
     */
    public B setHeightByRows(double heightByRows) {
        delegate.setHeightByRows(heightByRows);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.Grid#setHeightMode
     */
    public B setHeightMode(HeightMode heightMode) {
        delegate.setHeightMode(heightMode);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.Grid#setDescriptionGenerator
     */
    public B setDescriptionGenerator(DescriptionGenerator descriptionGenerator) {
        delegate.setDescriptionGenerator(descriptionGenerator);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.Grid#addHeaderRowAt
     */
    public B addHeaderRowAt(int headerRowAt) {
        delegate.addHeaderRowAt(headerRowAt);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.Grid#setDefaultHeaderRow
     */
    public B setDefaultHeaderRow(HeaderRow defaultHeaderRow) {
        delegate.setDefaultHeaderRow(defaultHeaderRow);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.Grid#addFooterRowAt
     */
    public B addFooterRowAt(int footerRowAt) {
        delegate.addFooterRowAt(footerRowAt);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.Grid#addColumnReorderListener
     */
    public B addColumnReorderListener(ColumnReorderListener columnReorderListener) {
        delegate.addColumnReorderListener(columnReorderListener);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.Grid#addColumnResizeListener
     */
    public B addColumnResizeListener(ColumnResizeListener columnResizeListener) {
        delegate.addColumnResizeListener(columnResizeListener);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.Grid#addItemClickListener
     */
    public B addItemClickListener(ItemClickListener itemClickListener) {
        delegate.addItemClickListener(itemClickListener);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.Grid#addColumnVisibilityChangeListener
     */
    public B addColumnVisibilityChangeListener(ColumnVisibilityChangeListener columnVisibilityChangeListener) {
        delegate.addColumnVisibilityChangeListener(columnVisibilityChangeListener);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.Grid#setColumnReorderingAllowed
     */
    public B setColumnReorderingAllowed(boolean columnReorderingAllowed) {
        delegate.setColumnReorderingAllowed(columnReorderingAllowed);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.Grid#setSelectionMode
     */
    public B setSelectionMode(SelectionMode selectionMode) {
        delegate.setSelectionMode(selectionMode);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.Grid#setSortOrder
     */
    public B setSortOrder(GridSortOrderBuilder sortOrder) {
        delegate.setSortOrder(sortOrder);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.Grid#setSortOrder
     */
    public B setSortOrder(List sortOrder) {
        delegate.setSortOrder(sortOrder);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.Grid#addSortListener
     */
    public B addSortListener(SortListener sortListener) {
        delegate.addSortListener(sortListener);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.Grid#setColumnResizeMode
     */
    public B setColumnResizeMode(ColumnResizeMode columnResizeMode) {
        delegate.setColumnResizeMode(columnResizeMode);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.Grid#setHeight
     */
    public B setHeight(float height, Unit unit) {
        delegate.setHeight(height, unit);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.Grid#setDataProvider
     */
    public B setDataProvider(FetchItemsCallback param1, SerializableSupplier param2) {
        delegate.setDataProvider(param1, param2);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.Grid#setDataProvider
     */
    public B setDataProvider(DataProvider dataProvider) {
        delegate.setDataProvider(dataProvider);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.Grid#setStyleGenerator
     */
    public B setStyleGenerator(StyleGenerator styleGenerator) {
        delegate.setStyleGenerator(styleGenerator);
        return self;
    }
    
}
