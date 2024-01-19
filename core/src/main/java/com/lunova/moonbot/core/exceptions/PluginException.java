package com.lunova.moonbot.core.exceptions;

/**
 * Represents an exception specific to plugin operations within the system. This exception extends
 * the standard Java {@link Exception} class and is intended to be thrown when an operation
 * involving a plugin encounters an error. The class constructor allows for chaining exceptions,
 * enabling the developer to track an error back to its original cause.
 *
 * <p>Typical use cases for throwing a PluginException might include, but are not limited to:
 *
 * <ul>
 *   <li>Plugin initialization failures due to invalid configurations or environments.
 *   <li>Runtime errors within plugin code, such as unhandled exceptions or illegal operations.
 *   <li>Issues with plugin dependencies or conflicts between multiple plugins.
 * </ul>
 *
 * <p>The inclusion of an original exception (the cause) is particularly useful for debugging and
 * logging, as it allows developers to understand the sequence of events leading up to the error, as
 * well as the specific issue at the root of the problem.
 *
 * <p>Handling this exception typically involves catching it at a level of the application where
 * appropriate action can be taken, such as logging the error, alerting the user, or attempting to
 * recover or restart the affected plugin.
 *
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @since 12.15.2023
 */
public class PluginException extends Exception {

  /**
   * Constructs a new PluginException with the specified detail message and cause. The detail
   * message is saved for later retrieval by the {@link Throwable#getMessage()} method. The cause is
   * saved for later retrieval by the {@link Throwable#getCause()} method.
   *
   * @param message the detail message, which provides a description of the exception. It is saved
   *     for later retrieval by the {@link Throwable#getMessage()} method.
   * @param e the cause (which is saved for later retrieval by the {@link Throwable#getCause()}
   *     method). A null value is permitted, and indicates that the cause is nonexistent or unknown.
   */
  public PluginException(String message, Exception e) {
    super(message, e);
  }
}
