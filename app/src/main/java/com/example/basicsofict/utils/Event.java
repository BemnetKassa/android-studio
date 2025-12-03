
package com.example.basicsofict.utils;

/**
 * A wrapper for data that is exposed via a LiveData that represents an event.
 * This ensures the event is only handled once.
 */
public class Event<T> {

    private boolean hasBeenHandled = false;
    private final T content; // The actual content of the event

    public Event(T content) {
        this.content = content;
    }

    /**
     * Returns the content and prevents its use again.
     */
    public T getContentIfNotHandled() {
        if (hasBeenHandled) {
            return null;
        } else {
            hasBeenHandled = true;
            return content;
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    public T peekContent() {
        return content;
    }
}
