package org.ikernits.lib.vaadin.builders;

import com.vaadin.ui.AbstractDateField;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.event.FieldEvents.FocusListener;
import java.time.temporal.Temporal;

@SuppressWarnings({"deprecation", "unused", "unchecked"})
public class AbstractDateFieldBuilder<T extends AbstractDateField, B extends AbstractDateFieldBuilder<T, B>> extends AbstractFieldBuilder<T, B> {

    public AbstractDateFieldBuilder(T delegate) {
        super(delegate);
    }
    
    /**
     * @see com.vaadin.ui.AbstractDateField#setValue
     */
    public B setValue(Temporal value) {
        delegate.setValue(value);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.AbstractDateField#addFocusListener
     */
    public B addFocusListener(FocusListener focusListener) {
        delegate.addFocusListener(focusListener);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.AbstractDateField#addBlurListener
     */
    public B addBlurListener(BlurListener blurListener) {
        delegate.addBlurListener(blurListener);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.AbstractDateField#setRangeStart
     */
    public B setRangeStart(Temporal rangeStart) {
        delegate.setRangeStart(rangeStart);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.AbstractDateField#setDateOutOfRangeMessage
     */
    public B setDateOutOfRangeMessage(String dateOutOfRangeMessage) {
        delegate.setDateOutOfRangeMessage(dateOutOfRangeMessage);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.AbstractDateField#setResolution
     */
    public B setResolution(Enum resolution) {
        delegate.setResolution(resolution);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.AbstractDateField#setRangeEnd
     */
    public B setRangeEnd(Temporal rangeEnd) {
        delegate.setRangeEnd(rangeEnd);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.AbstractDateField#setDateFormat
     */
    public B setDateFormat(String dateFormat) {
        delegate.setDateFormat(dateFormat);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.AbstractDateField#setLenient
     */
    public B setLenient(boolean lenient) {
        delegate.setLenient(lenient);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.AbstractDateField#setShowISOWeekNumbers
     */
    public B setShowISOWeekNumbers(boolean showISOWeekNumbers) {
        delegate.setShowISOWeekNumbers(showISOWeekNumbers);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.AbstractDateField#setParseErrorMessage
     */
    public B setParseErrorMessage(String parseErrorMessage) {
        delegate.setParseErrorMessage(parseErrorMessage);
        return self;
    }
    
}
