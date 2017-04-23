package org.ikernits.lib.vaadin.builders;

import com.vaadin.ui.ComboBox;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.server.SerializableFunction;
import com.vaadin.server.SerializableToIntFunction;
import com.vaadin.ui.ComboBox.CaptionFilter;
import com.vaadin.ui.ComboBox.FetchItemsCallback;
import com.vaadin.ui.ComboBox.NewItemHandler;
import com.vaadin.ui.IconGenerator;
import com.vaadin.ui.ItemCaptionGenerator;
import com.vaadin.ui.StyleGenerator;
import java.util.Collection;
import java.util.stream.Stream;

@SuppressWarnings({"deprecation", "unused", "unchecked"})
public class ComboBoxBuilder<T extends ComboBox, B extends ComboBoxBuilder<T, B>> extends AbstractSingleSelectBuilder<T, B> {

    public ComboBoxBuilder(T delegate) {
        super(delegate);
    }
    
    /**
     * @see com.vaadin.ui.ComboBox#addValueChangeListener
     */
    public B addValueChangeListener(ValueChangeListener valueChangeListener) {
        delegate.addValueChangeListener(valueChangeListener);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.ComboBox#setPlaceholder
     */
    public B setPlaceholder(String placeholder) {
        delegate.setPlaceholder(placeholder);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.ComboBox#addFocusListener
     */
    public B addFocusListener(FocusListener focusListener) {
        delegate.addFocusListener(focusListener);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.ComboBox#addBlurListener
     */
    public B addBlurListener(BlurListener blurListener) {
        delegate.addBlurListener(blurListener);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.ComboBox#setItems
     */
    public B setItems(Stream items) {
        delegate.setItems(items);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.ComboBox#setItems
     */
    public B setItems(Collection items) {
        delegate.setItems(items);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.ComboBox#setItems
     */
    public B setItems(CaptionFilter param1, Collection param2) {
        delegate.setItems(param1, param2);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.ComboBox#setDataProvider
     */
    public B setDataProvider(DataProvider param1, SerializableFunction param2) {
        delegate.setDataProvider(param1, param2);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.ComboBox#setDataProvider
     */
    public B setDataProvider(CaptionFilter param1, ListDataProvider param2) {
        delegate.setDataProvider(param1, param2);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.ComboBox#setDataProvider
     */
    public B setDataProvider(FetchItemsCallback param1, SerializableToIntFunction param2) {
        delegate.setDataProvider(param1, param2);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.ComboBox#setDataProvider
     */
    public B setDataProvider(ListDataProvider dataProvider) {
        delegate.setDataProvider(dataProvider);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.ComboBox#setTextInputAllowed
     */
    public B setTextInputAllowed(boolean textInputAllowed) {
        delegate.setTextInputAllowed(textInputAllowed);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.ComboBox#setPageLength
     */
    public B setPageLength(int pageLength) {
        delegate.setPageLength(pageLength);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.ComboBox#setEmptySelectionAllowed
     */
    public B setEmptySelectionAllowed(boolean emptySelectionAllowed) {
        delegate.setEmptySelectionAllowed(emptySelectionAllowed);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.ComboBox#setEmptySelectionCaption
     */
    public B setEmptySelectionCaption(String emptySelectionCaption) {
        delegate.setEmptySelectionCaption(emptySelectionCaption);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.ComboBox#setPopupWidth
     */
    public B setPopupWidth(String popupWidth) {
        delegate.setPopupWidth(popupWidth);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.ComboBox#setScrollToSelectedItem
     */
    public B setScrollToSelectedItem(boolean scrollToSelectedItem) {
        delegate.setScrollToSelectedItem(scrollToSelectedItem);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.ComboBox#setItemCaptionGenerator
     */
    public B setItemCaptionGenerator(ItemCaptionGenerator itemCaptionGenerator) {
        delegate.setItemCaptionGenerator(itemCaptionGenerator);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.ComboBox#setStyleGenerator
     */
    public B setStyleGenerator(StyleGenerator styleGenerator) {
        delegate.setStyleGenerator(styleGenerator);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.ComboBox#setItemIconGenerator
     */
    public B setItemIconGenerator(IconGenerator itemIconGenerator) {
        delegate.setItemIconGenerator(itemIconGenerator);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.ComboBox#setNewItemHandler
     */
    public B setNewItemHandler(NewItemHandler newItemHandler) {
        delegate.setNewItemHandler(newItemHandler);
        return self;
    }
    
}
