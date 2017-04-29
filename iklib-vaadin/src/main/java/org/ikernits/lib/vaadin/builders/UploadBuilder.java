package org.ikernits.lib.vaadin.builders;

import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.ChangeListener;
import com.vaadin.ui.Upload.FailedListener;
import com.vaadin.ui.Upload.FinishedListener;
import com.vaadin.ui.Upload.ProgressListener;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.StartedListener;
import com.vaadin.ui.Upload.SucceededListener;

@SuppressWarnings({"deprecation", "unused", "unchecked"})
public class UploadBuilder<T extends Upload, B extends UploadBuilder<T, B>> extends AbstractComponentBuilder<T, B> {

    public UploadBuilder(T delegate) {
        super(delegate);
    }
    
    /**
     * @see com.vaadin.ui.Upload#addStartedListener
     */
    public B addStartedListener(StartedListener startedListener) {
        delegate.addStartedListener(startedListener);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.Upload#addFinishedListener
     */
    public B addFinishedListener(FinishedListener finishedListener) {
        delegate.addFinishedListener(finishedListener);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.Upload#addFailedListener
     */
    public B addFailedListener(FailedListener failedListener) {
        delegate.addFailedListener(failedListener);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.Upload#addSucceededListener
     */
    public B addSucceededListener(SucceededListener succeededListener) {
        delegate.addSucceededListener(succeededListener);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.Upload#addProgressListener
     */
    public B addProgressListener(ProgressListener progressListener) {
        delegate.addProgressListener(progressListener);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.Upload#addChangeListener
     */
    public B addChangeListener(ChangeListener changeListener) {
        delegate.addChangeListener(changeListener);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.Upload#setReceiver
     */
    public B setReceiver(Receiver receiver) {
        delegate.setReceiver(receiver);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.Upload#setButtonCaption
     */
    public B setButtonCaption(String buttonCaption) {
        delegate.setButtonCaption(buttonCaption);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.Upload#addListener
     */
    public B addListener(FailedListener listener) {
        delegate.addListener(listener);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.Upload#addListener
     */
    public B addListener(ProgressListener listener) {
        delegate.addListener(listener);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.Upload#addListener
     */
    public B addListener(SucceededListener listener) {
        delegate.addListener(listener);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.Upload#addListener
     */
    public B addListener(StartedListener listener) {
        delegate.addListener(listener);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.Upload#addListener
     */
    public B addListener(FinishedListener listener) {
        delegate.addListener(listener);
        return self;
    }
    
    /**
     * @see com.vaadin.ui.Upload#setTabIndex
     */
    public B setTabIndex(int tabIndex) {
        delegate.setTabIndex(tabIndex);
        return self;
    }
    
}
